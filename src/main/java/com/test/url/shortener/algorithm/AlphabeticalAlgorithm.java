package com.test.url.shortener.algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AlphabeticalAlgorithm implements ShortenAlgorithm {
    private static final int DIGIT_START = 48;
    private static final int DIGIT_END = 57;

    private static final int UPPERCASE_START = 65;
    private static final int UPPERCASE_END = 90;

    private static final int LOWERCASE_START = 97;
    private static final int LOWERCASE_END = 122;

    private final int arrayLength;
    private final int[] urlCodes;

    public AlphabeticalAlgorithm(@Value("${url.max.length}") int arrayLength) {
        this.arrayLength = arrayLength;
        this.urlCodes = new int[arrayLength];
    }

    public String shorten(String url) {


        while(getCurrentRank() < Integer.MAX_VALUE) {
            final int nextCode = getNextCode();

            if (validBounds(nextCode)) {
                updateUrl(nextCode);
                return urlToString();
            }
        }

        return "";
    }

    private void updateUrl(int nextCode) {
        final int currentRank = getCurrentRank();
        this.urlCodes[currentRank] = nextCode;
    }

    private int getNextCode() {
        final int currentRank = getCurrentRank();

        final int currentCode = this.urlCodes[currentRank];
        return incrementCode(currentCode);
    }

    private int incrementCode(int currentCode) {
        if(currentCode < DIGIT_START) {
            return DIGIT_START;
        }

        if(currentCode < DIGIT_END) {
            return currentCode + 1;
        }

        if(DIGIT_END < currentCode && currentCode < UPPERCASE_START) {
            return UPPERCASE_START;
        }

        if(UPPERCASE_START <= currentCode && currentCode < UPPERCASE_END) {
            return currentCode + 1;
        }

        if(UPPERCASE_END < currentCode && currentCode < LOWERCASE_START) {
            return LOWERCASE_START;
        }

        if(LOWERCASE_START <= currentCode && currentCode <= LOWERCASE_END) {
            return currentCode + 1;
        }

        return Integer.MAX_VALUE;
    }

    private String urlToString() {
        final StringBuilder builder = new StringBuilder();
        Arrays.stream(urlCodes).filter(value -> value > 0).forEach(value -> builder.append((char)value));

        return builder.toString();
    }

    private boolean validBounds(int currentCode) {
        return currentCode < Integer.MAX_VALUE;
    }

    private int getCurrentRank() {
        for (int i = 0; i < arrayLength; i++) {
            if(urlCodes[i] < LOWERCASE_END) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }
}
