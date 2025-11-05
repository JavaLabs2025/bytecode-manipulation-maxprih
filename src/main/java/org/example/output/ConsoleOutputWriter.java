package org.example.output;

import org.example.model.AbcMetric;
import org.example.model.AnalysisResult;

public class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void write(AnalysisResult result) {
        System.out.println("Результаты анализа:");
        System.out.println("========================================");
        System.out.printf("Максимальная глубина наследования: %d%n", result.getMaxInheritanceDepth());
        System.out.printf("Средняя глубина наследования: %.2f%n", result.getAverageInheritanceDepth());
        System.out.printf("Среднее количество полей в классе: %.2f%n", result.getAverageFieldsPerClass());
        System.out.printf("Среднее количество переопределенных методов: %.2f%n", result.getAverageOverriddenMethods());
        
        System.out.println("\nМетрика ABC (всего по проекту):");
        AbcMetric abc = result.getAbcMetric();
        System.out.printf("  - Assignments (A): %d%n", abc.getAssignments());
        System.out.printf("  - Branches    (B): %d%n", abc.getBranches());
        System.out.printf("  - Conditions  (C): %d%n", abc.getConditions());
        System.out.printf("  - Metric         : %s%n", abc.getMetric());
        System.out.println("========================================");
    }
}
