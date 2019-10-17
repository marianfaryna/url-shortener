package com.test.url.shortener.validator;

import org.springframework.stereotype.Component;

@Component
public class BannedWordValidator {
    public boolean validate(String url) {
        return true;
    }
}
