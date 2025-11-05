package org.example.analyzer;

import org.example.analyzer.visitors.ClassAnalysisVisitor;
import org.example.model.AbcMetric;
import org.example.model.AnalysisResult;
import org.example.model.ClassInfo;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.jar.JarFile;

public class JarAnalyzer {

    public AnalysisResult analyze(Path jarPath) throws IOException {
        try (JarFile jarFile = new JarFile(jarPath.toFile())) {
            InheritanceHierarchy hierarchy = new InheritanceHierarchy(jarFile);
            Collection<ClassInfo> classesToAnalyze = hierarchy.getClassMap().values();

            jarFile.stream()
                    .filter(entry -> entry.getName().endsWith(".class"))
                    .forEach(entry -> {
                        String className = entry.getName().replace(".class", "");
                        ClassInfo classInfo = hierarchy.getClassMap().get(className);
                        if (classInfo != null) {
                            try (InputStream inputStream = jarFile.getInputStream(entry)) {
                                ClassReader classReader = new ClassReader(inputStream);
                                classReader.accept(new ClassAnalysisVisitor(classInfo, hierarchy), 0);
                            } catch (IOException e) {
                                System.err.println("Ошибка при анализе класса: " + className);
                            }
                        }
                    });

            return calculateFinalMetrics(classesToAnalyze, hierarchy);
        }
    }

    private AnalysisResult calculateFinalMetrics(Collection<ClassInfo> analyzedClasses, InheritanceHierarchy hierarchy) {
        AnalysisResult result = new AnalysisResult();
        if (analyzedClasses.isEmpty()) {
            result.setAbcMetric(new AbcMetric());
            return result;
        }

        for (ClassInfo ci : analyzedClasses) {
            ci.setInheritanceDepth(hierarchy.getInheritanceDepth(ci.getName()));
        }

        int totalClasses = analyzedClasses.size();
        int maxDepth = analyzedClasses.stream().mapToInt(ClassInfo::getInheritanceDepth).max().orElse(0);
        double avgDepth = analyzedClasses.stream().mapToInt(ClassInfo::getInheritanceDepth).average().orElse(0.0);

        long totalFields = analyzedClasses.stream().mapToLong(ClassInfo::getFieldCount).sum();
        double avgFields = (double) totalFields / totalClasses;

        long totalOverriddenMethods = analyzedClasses.stream().mapToLong(ClassInfo::getOverriddenMethodsCount).sum();
        double avgOverriddenMethods = (double) totalOverriddenMethods / totalClasses;

        AbcMetric totalAbc = new AbcMetric();
        analyzedClasses.forEach(ci -> totalAbc.add(ci.getAbcMetric()));

        result.setMaxInheritanceDepth(maxDepth);
        result.setAverageInheritanceDepth(avgDepth);
        result.setAverageFieldsPerClass(avgFields);
        result.setAverageOverriddenMethods(avgOverriddenMethods);
        result.setAbcMetric(totalAbc);

        return result;
    }
}
