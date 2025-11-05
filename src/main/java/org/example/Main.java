package org.example;

import org.example.analyzer.JarAnalyzer;
import org.example.model.AnalysisResult;
import org.example.output.ConsoleOutputWriter;
import org.example.output.JsonOutputWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Использование: java -jar jaranalyzer.jar <путь-к-jar-файлу> <путь-к-json-файлу>");
            System.exit(1);
        }

        Path jarPath = Paths.get(args[0]);
        Path outputPath = Paths.get(args[1]);

        try {
            JarAnalyzer analyzer = new JarAnalyzer();
            AnalysisResult result = analyzer.analyze(jarPath);

            new ConsoleOutputWriter().write(result);
            new JsonOutputWriter(outputPath).write(result);

            System.out.println("\nАнализ успешно завершен. Результаты сохранены в " + outputPath);
        } catch (Exception e) {
            System.err.println("Произошла ошибка во время анализа: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
