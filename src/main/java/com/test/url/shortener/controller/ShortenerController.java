package com.test.url.shortener.controller;

import com.test.url.shortener.algorithm.ShortenAlgorithm;
import com.test.url.shortener.validator.BannedWordValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenerController {

    private final BannedWordValidator bannedWordValidator;
    private final ShortenAlgorithm shortenAlgorithm;

    public ShortenerController(BannedWordValidator bannedWordValidator, ShortenAlgorithm shortenAlgorithm) {
        this.bannedWordValidator = bannedWordValidator;
        this.shortenAlgorithm = shortenAlgorithm;
    }

    @PostMapping("/shorten")
    public String shorten(@RequestBody String fullUrl) {

        String shortenUrl;
        do {
            shortenUrl = shortenAlgorithm.shorten(fullUrl);
        } while (!bannedWordValidator.validate(shortenUrl));

        return shortenUrl;

    }

    @GetMapping("/ping")
    public String ping() {


        return "I'm alive";

    }
}
