package com.test.url.shortener.validator;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BannedWordValidator {
    private Set<String> bannedWords;

    @PostConstruct
    public void init() {
        try {
            //TODO check for file availability
            this.bannedWords = Files
                    .lines(Paths.get(getClass().getClassLoader().getResource("fileTest.txt").toURI()))
                    .collect(Collectors.toSet());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public boolean validate(final String url) {
        final List<String> occurred = this.bannedWords.stream().filter(url::contains).collect(Collectors.toList());
        return occurred.isEmpty();
    }
}
