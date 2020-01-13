package com.test.url.shortener.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.test.url.shortener.algorithm.ShortenAlgorithm;
import com.test.url.shortener.validator.BannedWordValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@RestController
public class ShortenerController {
    private LoadingCache<String, String> urlCache;

    private final BannedWordValidator bannedWordValidator;
    private final ShortenAlgorithm shortenAlgorithm;

    public ShortenerController(BannedWordValidator bannedWordValidator, ShortenAlgorithm shortenAlgorithm) {
        this.bannedWordValidator = bannedWordValidator;
        this.shortenAlgorithm = shortenAlgorithm;
    }

    @PostConstruct
    public void postConstruct() {
        urlCache = CacheBuilder
                .newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key)  {
                        return shortenAlgorithm.shorten(key);
                    }
                });
    }

    @PostMapping("/shorten")
    public String shorten(@RequestBody String fullUrl) {

        String shortenUrl;
        do {
            shortenUrl = urlCache.getUnchecked(fullUrl);
        } while (!bannedWordValidator.validate(shortenUrl));

        return shortenUrl;

    }

    @GetMapping("/ping")
    public String ping() {
        return "I'm alive";

    }
}
