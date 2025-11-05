package org.example.analyzer.visitors;

import org.example.analyzer.InheritanceHierarchy;
import org.example.model.ClassInfo;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassAnalysisVisitor extends ClassVisitor {
    private final ClassInfo classInfo;
    private final InheritanceHierarchy hierarchy;
    private int fieldCount = 0;

    public ClassAnalysisVisitor(ClassInfo classInfo, InheritanceHierarchy hierarchy) {
        super(Opcodes.ASM9);
        this.classInfo = classInfo;
        this.hierarchy = hierarchy;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        fieldCount++;
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        classInfo.getMethodSignatures().add(name + descriptor);

        if (hierarchy.isMethodOverridden(classInfo.getName(), name, descriptor)) {
            classInfo.setOverriddenMethodsCount(classInfo.getOverriddenMethodsCount() + 1);
        }

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new MethodAnalysisVisitor(mv, classInfo.getAbcMetric());
    }

    @Override
    public void visitEnd() {
        classInfo.setFieldCount(fieldCount);
        super.visitEnd();
    }
}
