package com.test.url.shortener.algorithm;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AlphabeticalAlgorithm implements ShortenAlgorithm{
    public String shorten(String url) {
        return UUID.randomUUID().toString();
    }
}
