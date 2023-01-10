package com.nhg.catalogue.controller;

import com.nhg.catalogue.model.Page;
import com.nhg.catalogue.service.CatalogueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/catalogue")
public class CatalogueController {

    private final CatalogueService catalogueService;

    @GetMapping("/page")
    private Set<Page> getPages() {
        return catalogueService.getAllPages();
    }
}
