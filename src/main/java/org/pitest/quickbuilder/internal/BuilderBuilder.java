package org.pitest.quickbuilder.internal;

import static org.objectweb.asm.Opcodes.ACC_BRIDGE;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IFNULL;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class BuilderBuilder {

  private static final String GENERATOR_FIELD = "___generator";
  private static final String   BUILDER_INTERFACE   = "org/pitest/quickbuilder/Builder";
  private static final String  INTERNAL_INTERFACE  = "org/pitest/quickbuilder/internal/_InternalQuickBuilder";
  private static final String  GENERATOR_INTERFACE = "org/pitest/quickbuilder/Generator";

  private final String         builderName;
  private final String         proxiedName;
  private final String         built;
  private final List<Property> ps;

  BuilderBuilder(final String builderName, final String proxiedName,
      final String built, final List<Property> ps) {
    this.builderName = builderName;
    this.proxiedName = proxiedName;
    this.built = built;
    this.ps = ps;
  }

  public byte[] build() throws Exception {

    final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    cw.visit(Opcodes.V1_5, ACC_PUBLIC + ACC_SUPER, this.builderName,
        "Ljava/lang/Object;L" + BUILDER_INTERFACE + "<L" + this.built + ";>;"
            + "L" + this.proxiedName + ";", "java/lang/Object", new String[] {
            BUILDER_INTERFACE, this.proxiedName, INTERNAL_INTERFACE });

    createFields(cw);

    createInitMethod(cw);
    createCopyConstructor(cw);

    for (final Property each : this.ps) {
      createWithMethod(cw, each);
      if (!each.isBuilder()) {
        createAccessor(cw, each);
      }
    }

    createGeneratorMethod(cw);
    createBuildMethod(cw, this.ps);
    createBridgeMethod(cw);

    cw.visitEnd();

    final byte[] bs = cw.toByteArray();

    return bs;

  }

  private void createCopyConstructor(final ClassWriter cw) {
    final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(L"
        + this.builderName + ";)V", null, null);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V",
        false);

    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitFieldInsn(GETFIELD, this.builderName, GENERATOR_FIELD, "L"
        + GENERATOR_INTERFACE + ";");
    mv.visitFieldInsn(PUTFIELD, this.builderName, GENERATOR_FIELD, "L"
        + GENERATOR_INTERFACE + ";");

    for (final Property each : this.uniqueProperties()) {
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 1);
      mv.visitFieldInsn(GETFIELD, this.builderName, each.name(), each.type());
      mv.visitFieldInsn(PUTFIELD, this.builderName, each.name(), each.type());
    }

    mv.visitInsn(RETURN);
    mv.visitMaxs(1, 1);
    mv.visitEnd();
  }

  private void createAccessor(final ClassWriter cw, final Property each) {
    final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "_" + each.name(), "()"
        + each.type(), null, null);
    mv.visitCode();

    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, this.builderName, each.name(), each.type());
    mv.visitInsn(each.returnOp());
    mv.visitMaxs(1, 1);
    mv.visitEnd();

  }

  private void createInitMethod(final ClassWriter cw) {
    final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null,
        null);
    mv.visitCode();

    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V",
        false);
    mv.visitInsn(RETURN);
    mv.visitMaxs(1, 1);
    mv.visitEnd();
  }

  private void createGeneratorMethod(final ClassWriter cw) {
    final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "__internal", "(L"
        + GENERATOR_INTERFACE + ";)V", "(L" + GENERATOR_INTERFACE + "<L"
        + this.built + ";+L" + BUILDER_INTERFACE + "<L" + this.built
        + ";>;>;)V", null);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitFieldInsn(PUTFIELD, this.builderName, GENERATOR_FIELD, "L"
        + GENERATOR_INTERFACE + ";");
    mv.visitInsn(RETURN);
    mv.visitMaxs(2, 2);
    mv.visitEnd();
  }

  private void createFields(final ClassWriter cw) {
    final FieldVisitor fv1 = cw.visitField(ACC_PRIVATE, GENERATOR_FIELD, "L"
        + GENERATOR_INTERFACE + ";", "L" + GENERATOR_INTERFACE + "<L"
        + this.built + ";L" + this.builderName + ";>;", null);
    fv1.visitEnd();

    final Set<Property> uniquePs = uniqueProperties();
    for (final Property each : uniquePs) {
      final FieldVisitor fv = cw.visitField(Opcodes.ACC_PRIVATE, each.name(),
          each.type(), null, null);
      fv.visitEnd();
    }
  }

  private Set<Property> uniqueProperties() {
    final Set<Property> uniquePs = new HashSet<Property>(this.ps);
    return uniquePs;
  }

  private void createWithMethod(final ClassWriter cw, final Property prop) {
    final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, prop.withMethodName(),
        "(" + prop.declaredType() + ")L" + this.proxiedName + ";", null, null);
    mv.visitCode();

    mv.visitVarInsn(ALOAD, 0);

    mv.visitVarInsn(prop.loadIns(), 1);

    if (prop.isBuilder()) {
      mv.visitMethodInsn(INVOKEINTERFACE, prop.declaredTypeName(), "build",
          "()Ljava/lang/Object;", true);
      mv.visitTypeInsn(CHECKCAST, prop.typeName());
    }

    mv.visitFieldInsn(Opcodes.PUTFIELD, this.builderName, prop.name(),
        prop.type());

    mv.visitTypeInsn(NEW, this.builderName);
    mv.visitInsn(DUP);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, this.builderName, "<init>", "(L"
        + this.builderName + ";)V", false);
    mv.visitInsn(ARETURN);

    mv.visitMaxs(1, 1);
    mv.visitEnd();

  }

  private void createBuildMethod(final ClassWriter cw, final List<Property> ps) {
    MethodVisitor mv;
    mv = cw.visitMethod(ACC_PUBLIC, "build", "()L" + this.built + ";", null,
        null);
    mv.visitCode();

    // handle generator case
    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, this.builderName, GENERATOR_FIELD, "L"
        + GENERATOR_INTERFACE + ";");
    final Label defaultConsCall = new Label();
    mv.visitJumpInsn(IFNULL, defaultConsCall);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitFieldInsn(GETFIELD, this.builderName, GENERATOR_FIELD, "L"
        + GENERATOR_INTERFACE + ";");
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKEINTERFACE, GENERATOR_INTERFACE, "generate", "(L"
        + BUILDER_INTERFACE + ";)Ljava/lang/Object;", true);
    mv.visitTypeInsn(CHECKCAST, this.built);
    mv.visitVarInsn(ASTORE, 1);
    final Label setProps = new Label();
    mv.visitJumpInsn(GOTO, setProps);

    mv.visitLabel(defaultConsCall);
    mv.visitTypeInsn(Opcodes.NEW, this.built);
    mv.visitInsn(Opcodes.DUP);
    mv.visitMethodInsn(INVOKESPECIAL, this.built, "<init>", "()V", false);
    mv.visitVarInsn(ASTORE, 1);

    mv.visitLabel(setProps);
    for (final Property p : ps) {
      if (p.isHasSetter()) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, this.builderName, p.name(), p.type());
        mv.visitMethodInsn(INVOKEVIRTUAL, this.built, p.setter().name(), p
            .setter().desc(), false);
      }
    }

    mv.visitVarInsn(ALOAD, 1);
    mv.visitInsn(ARETURN);
    mv.visitMaxs(1, 1); // irrelevent
    mv.visitEnd();
  }

  private void createBridgeMethod(final ClassWriter cw) {
    MethodVisitor mv;
    mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "build",
        "()Ljava/lang/Object;", null, null);
    mv.visitCode();
    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKEVIRTUAL, this.builderName, "build", "()L"
        + this.built + ";", false);
    mv.visitInsn(ARETURN);
    mv.visitMaxs(1, 1);
    mv.visitEnd();
  }

}
