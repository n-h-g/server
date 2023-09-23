package com.nhg.moderation.controller;

import com.nhg.moderation.dto.WordFilterResponse;
import com.nhg.moderation.service.WordFilterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/moderation/word_filters")
public class WordFilterController {
    private final WordFilterService wordFilterService;

    @GetMapping()
    public List<WordFilterResponse> GetFilter() {
        return wordFilterService.GetFilter();
    }
}
