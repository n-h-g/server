package com.nhg.moderation.service;

import com.nhg.moderation.dto.WordFilterResponse;
import com.nhg.moderation.model.WordFilter;
import com.nhg.moderation.repository.WordFilterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class WordFilterService {
    private final WordFilterRepository wordFilterRepository;

    public List<WordFilterResponse> GetFilter() {
        List<WordFilterResponse> returnedList = new ArrayList<>();
        Iterable<WordFilter> dbFilterValues = wordFilterRepository.findAll();

        for (WordFilter dbFilterValue : dbFilterValues) {
            returnedList.add(new WordFilterResponse(dbFilterValue.getWord(), dbFilterValue.getReplacement(), dbFilterValue.getHideMessage()));
        }

        return returnedList;
    }
}
