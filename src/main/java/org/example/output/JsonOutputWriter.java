package org.example.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.model.AnalysisResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonOutputWriter implements OutputWriter {
    private final Path outputPath;
    private final ObjectMapper objectMapper;

    public JsonOutputWriter(Path outputPath) {
        this.outputPath = outputPath;
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void write(AnalysisResult result) throws IOException {
        objectMapper.writeValue(Files.newBufferedWriter(outputPath), result);
    }
}
