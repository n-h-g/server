package com.nhg.catalogue.controller;

import com.nhg.catalogue.model.Item;
import com.nhg.catalogue.model.Page;
import com.nhg.catalogue.service.CatalogueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/catalogue")
public class CatalogueController {

    private final CatalogueService catalogueService;

    @GetMapping("/pages")
    private Set<Page> getPages() {
        return catalogueService.getAllPages();
    }

    @GetMapping("/pages/{id}/items")
    private List<Item> getItemsByPage(@PathVariable Integer id) {
        return catalogueService.getItemsForPage(id);
    }

    @GetMapping("/items/{id}")
    private Item getItem(@PathVariable Integer id) {
        return catalogueService.getItem(id);
    }
}
