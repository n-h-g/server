package com.nhg.messenger.application.dto;

public record WordFilterResponse(
        String word,
        String replacement,
        Boolean hideMessage
) {

}