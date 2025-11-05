package org.example.analyzer.visitors;

import org.example.model.AbcMetric;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodAnalysisVisitor extends MethodVisitor {
    private final AbcMetric abcMetric;

    public MethodAnalysisVisitor(MethodVisitor methodVisitor, AbcMetric abcMetric) {
        super(Opcodes.ASM9, methodVisitor);
        this.abcMetric = abcMetric;
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        // A - Assignment
        if (opcode >= Opcodes.ISTORE && opcode <= Opcodes.ASTORE) {
            abcMetric.setAssignments(abcMetric.getAssignments() + 1);
        }
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        // B - Branch
        abcMetric.setBranches(abcMetric.getBranches() + 1);
        if ((opcode >= Opcodes.IFEQ && opcode <= Opcodes.IF_ACMPNE) || opcode == Opcodes.IFNONNULL || opcode == Opcodes.IFNULL) {
            // C - Condition
            abcMetric.setConditions(abcMetric.getConditions() + 1);
        }
        super.visitJumpInsn(opcode, label);
    }
}
