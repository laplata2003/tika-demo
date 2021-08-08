package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.springframework.boot.CommandLineRunner;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class CustomCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String filename = args[1];
        log.info("File to process: {}", filename);
        Path path = Paths.get(filename);
        InputStream inputStream = Files.newInputStream(path);
        Metadata metadata = TikaAnalyzer.extractMetadatatUsingParser(inputStream);

        log.info("Parser: {}", metadata.get("X-Parsed-By"));
        log.info("Author: {}", metadata.get("Author"));

        inputStream.close();
    }

}
