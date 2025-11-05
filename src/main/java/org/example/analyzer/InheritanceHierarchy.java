package org.example.analyzer;

import org.example.model.ClassInfo;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarFile;

public class InheritanceHierarchy {
    private final Map<String, ClassInfo> classMap = new HashMap<>();
    private final Map<String, Integer> depthCache = new HashMap<>();

    public InheritanceHierarchy(JarFile jarFile) {
        jarFile.stream()
                .filter(entry -> entry.getName().endsWith(".class"))
                .forEach(entry -> {
                    try (InputStream inputStream = jarFile.getInputStream(entry)) {
                        ClassReader classReader = new ClassReader(inputStream);
                        ClassNode classNode = new ClassNode(Opcodes.ASM9);
                        classReader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_CODE | ClassReader.SKIP_FRAMES);

                        classMap.put(classNode.name, new ClassInfo(classNode.name, classNode.superName));
                    } catch (IOException e) {
                        System.err.println("Не удалось прочитать класс: " + entry.getName());
                    }
                });
    }

    public int getInheritanceDepth(String className) {
        if (className == null || className.equals("java/lang/Object")) {
            return 0;
        }
        if (depthCache.containsKey(className)) {
            return depthCache.get(className);
        }

        ClassInfo classInfo = classMap.get(className);
        int depth = (classInfo != null) ? 1 + getInheritanceDepth(classInfo.getSuperName()) : 0;

        depthCache.put(className, depth);
        return depth;
    }

    public boolean isMethodOverridden(String ownerClass, String methodName, String methodDesc) {
        String currentSuper = Optional.ofNullable(classMap.get(ownerClass)).map(ClassInfo::getSuperName).orElse(null);

        while (currentSuper != null) {
            ClassInfo superClassInfo = classMap.get(currentSuper);
            if (superClassInfo != null && superClassInfo.getMethodSignatures().contains(methodName + methodDesc)) {
                return true;
            }
            currentSuper = Optional.ofNullable(superClassInfo).map(ClassInfo::getSuperName).orElse(null);
        }
        return false;
    }

    public Map<String, ClassInfo> getClassMap() {
        return classMap;
    }
}
