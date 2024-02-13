package com.nhg.messenger.adapter.out.rest;

import com.nhg.messenger.application.dto.WordFilterResponse;
import com.nhg.messenger.application.port.WordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class WordFilterRestAdapter implements WordFilter {

    private final RestTemplate restTemplate;


    @Override
    public String filterText(String text) {
        WordFilterResponse[] wordFilters = restTemplate.getForObject(
                "http://MODERATION/api/v1/moderation/word_filters",
                WordFilterResponse[].class
        );

        if (wordFilters != null && Arrays.stream(wordFilters).map(WordFilterResponse::word).toList().contains(text)) {
            String finalText = text;
            WordFilterResponse filtered = Arrays.stream(wordFilters).filter(wf -> wf.word().equalsIgnoreCase(finalText)).findFirst().get();

            if (filtered.hideMessage()) {
                return "";
            } else {
                text = filtered.replacement();
            }
        }

        return text;
    }
}
