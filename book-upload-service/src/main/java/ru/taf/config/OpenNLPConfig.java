package ru.taf.config;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class OpenNLPConfig {

    @Bean(name = "englishDetector")
    public SentenceDetectorME englishSentenceDetector() throws IOException {
        InputStream modelStream = getClass().getClassLoader()
                .getResourceAsStream("opennlp-en-ud-ewt-sentence-1.3-2.5.4.bin");

        if (modelStream == null) {
            throw new IllegalStateException("English model not found. Check if dependency is correctly added.");
        }

        try (modelStream) {
            SentenceModel model = new SentenceModel(modelStream);
            return new SentenceDetectorME(model);
        }
    }

    @Bean(name = "russianDetector")
    public SentenceDetectorME russianSentenceDetector() throws IOException {
        InputStream modelStream = getClass().getClassLoader()
                .getResourceAsStream("opennlp-ru-ud-gsd-sentence-1.3-2.5.4.bin");

        if (modelStream == null) {
            throw new IllegalStateException("Russian model not found. Check if dependency is correctly added.");
        }

        try (modelStream) {
            SentenceModel model = new SentenceModel(modelStream);
            return new SentenceDetectorME(model);
        }
    }
}