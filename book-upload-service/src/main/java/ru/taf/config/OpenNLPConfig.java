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
        InputStream modelStream = new ClassPathResource("models/en-sent.bin").getInputStream();
        SentenceModel model = new SentenceModel(modelStream);
        return new SentenceDetectorME(model);
    }

    @Bean(name = "russianDetector")
    public SentenceDetectorME russianSentenceDetector() throws IOException {
        InputStream modelStream = new ClassPathResource("models/ru-sent.bin").getInputStream();
        SentenceModel model = new SentenceModel(modelStream);
        return new SentenceDetectorME(model);
    }
}