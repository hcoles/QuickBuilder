package org.pitest.quickbuilder.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Type;
import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Generator;
import org.pitest.quickbuilder.Maybe;
import org.pitest.quickbuilder.QuickBuilderError;
import org.pitest.quickbuilder.SequenceBuilder;

import com.googlecode.gentyref.GenericTypeReflector;

public class TypeScanner<T, B extends Builder<T>> {

  private static final String   USER_PROPERTY_PREFIX = "_";

  private final static QBLoader cl                   = new QBLoader(
                                                         TypeScanner.class
                                                             .getClassLoader());

  private final Class<B>        builder;
  private final Generator<B, T> g;

  public TypeScanner(final Class<B> builder, final Generator<B, T> g) {
    this.builder = builder;
    this.g = g;
  }

  @SuppressWarnings("unchecked")
  public B builder() {

    checkSuppliedInterface();

    final String proxiedName = this.builder.getName().replace(".", "/");
    final String builderName = proxiedName + "__quickbuilder__";

    try {
      final Class<B> builderClass = findOrMakeBuilder(proxiedName, builderName);
      final Constructor<B> c = builderClass
          .getDeclaredConstructor(Generator.class);
      final B b = c.newInstance(this.pickGenerator());
      return b;
    } catch (final QuickBuilderError e) {
      throw e;
    } catch (final Exception e) {
      throw new QuickBuilderError("Unexpected error", e);
    }

  }

  private void checkSuppliedInterface() {
    if (!this.builder.isInterface()) {
      throw new QuickBuilderError("Cannot create a builder from "
          + this.builder.getName() + " becuase it is not an interface.");
    }

    if (!Modifier.isPublic(this.builder.getModifiers())) {
      throw new QuickBuilderError("Cannot implement the interface "
          + this.builder.getName() + " because it is not public");
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

    disableSettersForUserHandledProperties(ps, userProperties, builtType);

    final BuilderBuilder bb = new BuilderBuilder(builderName, proxiedName,
        builtTypeName, ps);

    return (Class<B>) cl.createClass(bb.build(), builderName.replace('/', '.'));
  }

  private void disableSettersForUserHandledProperties(final List<Property> ps,
      final Set<Property> userProperties, final Class<T> builtType) {
    for (final Property each : ps) {
      if (userProperties.contains(each)) {
        each.disableSetter();
      } else {
        if (!each.isHasSetter()) {
          throw new QuickBuilderError(
              "Can't create builder from "
                  + this.builder.getName()
                  + " : "
                  + "No setter found for "
                  + each.name()
                  + " of type "
                  + each.type()
                  + " on the built class "
                  + builtType.getName()
                  + ".\nCheck name and type or declare an underscore method and generator to handle it yourself.");
        }
      }
    }

  }

  private Generator<B, T> pickGenerator() throws SecurityException,
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
        final String name = extractName(prefix, m);
        final String owner = m.getDeclaringClass().getName().replace(".", "/");
        final org.objectweb.asm.Type type = findPropertyType(m);
        final org.objectweb.asm.Type declared = Type
            .getType(findDeclaredType(m));

        ps.add(new Property(name, owner, prefix, type, declared,
            findBridgeMethodReturnTypeIfAny(m), findSetter(builtType, name,
                type)));
      }
    }
    return ps;
  }

  private Type findBridgeMethodReturnTypeIfAny(final Method m) {
    final java.lang.reflect.Type rType = m.getGenericReturnType();
    if (rType instanceof TypeVariable) {
      return Type.getType(m.getReturnType());
    }
    return null;
  }

  private String extractPrefix(final String name) {
    return StringUtils.parseCamelCase(name)[0];
  }

  private boolean isPropertyMethod(final Method m) {
    final String name = m.getName();
    return (!name.startsWith(USER_PROPERTY_PREFIX)
        && !m.getDeclaringClass().equals(Iterable.class)
        && !m.getDeclaringClass().equals(Builder.class)
        && !m.getDeclaringClass().equals(SequenceBuilder.class) && (StringUtils
        .parseCamelCase(name).length > 1));
  }

  private void checkWithMethod(final Method m) {
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
    final Class<?> paramType = findDeclaredType(m);
    Class<?> t = paramType;
    if (t.equals(Builder.class)) {
      final java.lang.reflect.ParameterizedType ty = (ParameterizedType) m
          .getGenericParameterTypes()[0];
      t = findPropertyTypeFromGenericInterface(ty, m);
    } else if (Builder.class.isAssignableFrom(t)) {
      t = findPropertyTypeFromConcreteBuilder(m, paramType);
    }

    return Type.getType(t);
  }

  private Class<?> findPropertyTypeFromConcreteBuilder(final Method m,
      final Class<?> paramType) {
    Class<?> t;
    t = (Class<?>) GenericTypeReflector.getTypeParameter(paramType,
        Builder.class.getTypeParameters()[0]);
    if (t == null) {
      throw new QuickBuilderError("Could not determine property type for "
          + m.getName());
    }
    return t;
  }

  private Class<?> findPropertyTypeFromGenericInterface(
      final ParameterizedType ty, final Method m) {
    final java.lang.reflect.Type typeArgument = ty.getActualTypeArguments()[0];
    if (typeArgument instanceof Class<?>) {
      return (Class<?>) typeArgument;
    } else {
      throw new QuickBuilderError("Unable to determine property type for  "
          + m.getName() + " as wildcards not currently supported");
    }
  }

  private Class<?> findDeclaredType(final Method m) {
    final Class<?> t = m.getParameterTypes()[0];
    return t;
  }

  private Set<Property> findUserHandledProperties(final Class<T> builtType) {
    final Set<Property> ps = new HashSet<Property>();
    for (final Method m : this.builder.getMethods()) {
      if (m.getName().startsWith(USER_PROPERTY_PREFIX)) {
        checkUnderScoreMethod(m);
        final String n = extractName(findPrefix(m.getName()), m);
        final org.objectweb.asm.Type type = findType(m);
        ps.add(new Property(n, null, findPrefix(m.getName()), type, type, null,
            findSetter(builtType, n, type)));
      }
    }
    return ps;
  }

  private String findPrefix(final String name) {
    if (name.startsWith("__")) {
      return "__";
    }
    return USER_PROPERTY_PREFIX;
  }

  private Type findType(final Method m) {
    if (m.getReturnType().equals(Maybe.class)) {
      final java.lang.reflect.ParameterizedType ty = (ParameterizedType) m
          .getGenericReturnType();
      return org.objectweb.asm.Type
          .getType(findPropertyTypeFromGenericInterface(ty, m));
    }
    return org.objectweb.asm.Type.getReturnType(m);
  }

  private void checkUnderScoreMethod(final Method m) {
    if (m.getParameterTypes().length != 0) {
      throw new QuickBuilderError(m.getName() + " should not have parameters");
    }
  }

  private Setter findSetter(final Class<?> builtType, final String name,
      final org.objectweb.asm.Type type) {
    for (final Method m : builtType.getMethods()) {
      if (m.getName().equals("set" + name)
          && m.getReturnType().equals(Void.TYPE)
          && (m.getParameterTypes().length == 1)
          && (Type.getArgumentTypes(m)[0].equals(type))
          ) {
        return new Setter("set" + name, type);
      }
    }

    return null;
  }

  private String extractName(final String prefix, final Method m) {
    return m.getName().substring(prefix.length(), m.getName().length());
  }

}
