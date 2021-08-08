package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
@Slf4j
@Profile("!test")
public class ApplicationStartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args)  {

        var fileNames = Optional.ofNullable(args.getOptionValues("file")).
                orElseThrow(() -> new IllegalArgumentException("File is mandatory"));
        log.info("Files to process: {}", fileNames);

        fileNames.forEach(fileName -> {
            Path inputPath = Paths.get(fileName);
            log.info("----------------------------------------------");
            log.info("Input file: : {}", inputPath);
            Path outputPath = getOutputPath(inputPath);
            log.info("Output file: {}", outputPath);
            try (InputStream inputStream = Files.newInputStream(inputPath);
                 BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
                String content = TikaAnalyzer.extractContentUsingFacade(inputStream);
                writer.write(content);
            } catch (IOException | TikaException e) {
                e.printStackTrace();
            }
        });
    }

    private Path getOutputPath(Path inputPath) {
        String inputPathAsStr = inputPath.toString();
        return Paths.get(String.format("%s_%s.txt",
                        FilenameUtils.removeExtension(inputPathAsStr),
                        FilenameUtils.getExtension(inputPathAsStr)));
    }

}
