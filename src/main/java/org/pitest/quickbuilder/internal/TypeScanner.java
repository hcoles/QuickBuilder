package org.pitest.quickbuilder.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Type;
import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Generator;
import org.pitest.quickbuilder.QB;
import org.pitest.quickbuilder.QuickBuilderError;

import com.googlecode.gentyref.GenericTypeReflector;

public class TypeScanner<T, B extends Builder<T>> {

  private static final String   USER_PROPERTY_PREFIX = "_";

  private final static QBLoader cl                   = new QBLoader(
                                                         QB.class
                                                             .getClassLoader());

  private final Class<B>        builder;
  private final Generator<T, B> g;

  public TypeScanner(final Class<B> builder, final Generator<T, B> g) {
    this.builder = builder;
    this.g = g;
  }

  @SuppressWarnings("unchecked")
  public B builder() {

    final String proxiedName = this.builder.getName().replace(".", "/");
    final String builderName = proxiedName + "__quickbuilder__";

    try {
      final Class<B> builderClass = findOrMakeBuilder(proxiedName, builderName);
      final B b = builderClass.newInstance();
      ((_InternalQuickBuilder<T>) b).__internal(pickGenerator());
      return b;
    } catch (final QuickBuilderError e) {
      throw e;
    } catch (final Exception e) {
      throw new QuickBuilderError(e);
    }

  }

  @SuppressWarnings("unchecked")
  private Class<B> findOrMakeBuilder(final String proxiedName,
      final String builderName) throws Exception {
    final Class<B> existingBuilder = (Class<B>) cl.findClass(builderName
        .replace("/", "."));
    if (existingBuilder != null) {
      return existingBuilder;
    }
    return makeBuilderClass(proxiedName, builderName);
  }

  @SuppressWarnings("unchecked")
  private Class<B> makeBuilderClass(final String proxiedName,
      final String builderName) throws Exception {
    final Class<T> builtType = findBuiltType();
    final String builtTypeName = builtType.getName().replace(".", "/");
    final List<Property> ps = findDeclaredProperties(builtType);
    final Set<Property> userProperties = findUserHandledProperties(builtType);

    checkProperties(ps, userProperties);

    final BuilderBuilder bb = new BuilderBuilder(builderName, proxiedName,
        builtTypeName, ps);

    return (Class<B>) cl.createClass(bb.build(), builderName.replace('/', '.'));
  }

  private void checkProperties(final List<Property> ps,
      final Set<Property> userProperties) {
    for (final Property each : ps) {
      if (userProperties.contains(each)) {
        each.disableSetter();
      } else {
        if (!each.isHasSetter()) {
          throw new QuickBuilderError(
              "No setter found for "
                  + each.name()
                  + " of type "
                  + each.type()
                  + ".\nCheck name or declare an underscore method and generator to handle it yourself.");
        }
      }
    }

  }

  private Generator<T, B> pickGenerator() throws SecurityException,
      NoSuchMethodException {
    if (this.g != null) {
      return this.g;
    }

    final Class<T> builtType = findBuiltType();
    Exception ex = null;
    try {
      if (Modifier.isPublic(builtType.getConstructor().getModifiers())) {
        return null;
      }
    } catch (final Exception e) {
      ex = e;
    }

    throw new QuickBuilderError(
        "Cannot create builder classes for type "
            + builtType
            + "unaided. \nClass must have an accessible no args constructor or provide a generator to construct.",
        ex);

  }

  @SuppressWarnings("unchecked")
  private Class<T> findBuiltType() throws SecurityException,
      NoSuchMethodException {
    final Method build = this.builder.getMethod("build");
    return (Class<T>) GenericTypeReflector.getExactReturnType(build,
        this.builder);
  }

  private List<Property> findDeclaredProperties(final Class<?> builtType) {
    final List<Property> ps = new ArrayList<Property>();
    for (final Method m : this.builder.getMethods()) {
      if (isPropertyMethod(m)) {
        checkWithMethod(m);
        final String prefix = extractPrefix(m.getName());
        final String n = extractName(prefix, m);
        final org.objectweb.asm.Type type = findPropertyType(m);
        final org.objectweb.asm.Type declared = Type
            .getType(findDeclaredType(m));
        ps.add(new Property(n, prefix, type, declared, findSetter(builtType, n, type)));
      }
    }
    return ps;
  }

  private String extractPrefix(String name) {
    return StringUtils.parseCamelCase(name)[0];
  }

  private boolean isPropertyMethod(final Method m) {
    String name = m.getName();
    return (!name.startsWith(USER_PROPERTY_PREFIX)
        && !m.getDeclaringClass().equals(Builder.class) && StringUtils
        .parseCamelCase(name).length > 1);
  }

  private void checkWithMethod(Method m) {
    if (!m.getReturnType().isAssignableFrom(this.builder)) {
      throw new QuickBuilderError(m.getName()
          + " should declare return type as " + this.builder.getName());
    }

    if (m.getParameterTypes().length != 1) {
      throw new QuickBuilderError(m.getName()
          + " should take exactly one parameter.");
    }

  }

  private org.objectweb.asm.Type findPropertyType(final Method m) {
    Class<?> paramType = findDeclaredType(m);
    Class<?> t = paramType;
    if (Builder.class.isAssignableFrom(t)) {
      t = (Class<?>) GenericTypeReflector.getTypeParameter(paramType,
          Builder.class.getTypeParameters()[0]);
      if (t == null) {
        throw new QuickBuilderError(
            "Could not determine property type. Did you declare a raw Builder<YourType> in your interface?");
      }
    }

    return Type.getType(t);
  }

  private Class<?> findDeclaredType(final Method m) {
    Class<?> t = m.getParameterTypes()[0];
    return t;
  }

  private Set<Property> findUserHandledProperties(final Class<T> builtType) {
    final Set<Property> ps = new HashSet<Property>();
    for (final Method m : this.builder.getMethods()) {
      if (m.getName().startsWith(USER_PROPERTY_PREFIX)) {
        checkUnderScoreMethod(m);
        final String n = extractName(USER_PROPERTY_PREFIX, m);
        final org.objectweb.asm.Type type = org.objectweb.asm.Type
            .getReturnType(m);
        ps.add(new Property(n, USER_PROPERTY_PREFIX, type, type, findSetter(builtType, n, type)));
      }
    }
    return ps;
  }

  private void checkUnderScoreMethod(Method m) {
    if (m.getParameterTypes().length != 0) {
      throw new QuickBuilderError(m.getName() + " should not have parameters");
    }
  }

  private Setter findSetter(final Class<?> builtType, final String name,
      final org.objectweb.asm.Type type) {
    for (final Method m : builtType.getMethods()) {
      if (m.getName().equals("set" + name)
          && m.getReturnType().equals(Void.TYPE)
          && (m.getParameterTypes().length == 1)) {
        return new Setter("set" + name, type);
      }
    }

    return null;
  }

  private String extractName(final String prefix, final Method m) {
    return m.getName().substring(prefix.length(), m.getName().length());
  }

}
