package org.example.output;

import org.example.model.AnalysisResult;

import java.io.IOException;

public interface OutputWriter {
    void write(AnalysisResult result) throws IOException;
}
