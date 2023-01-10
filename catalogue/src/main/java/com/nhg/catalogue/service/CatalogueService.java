package com.nhg.catalogue.service;

import com.nhg.catalogue.model.Item;
import com.nhg.catalogue.model.Page;
import com.nhg.catalogue.repository.ItemRepository;
import com.nhg.catalogue.repository.PageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Service
@AllArgsConstructor
public class CatalogueService {

    private final PageRepository pageRepository;
    private final ItemRepository itemRepository;

    public Set<Page> getAllPages() {
        Iterator<Page> iterator = pageRepository.findAll().iterator();

        SortedSet<Page> pages = new TreeSet<>();
        iterator.forEachRemaining(pages::add);

        return pages;
    }

    public List<Item> getItemsForPage(int pageId) {
        return itemRepository.findByPageId(pageId);
    }

    public Item getItem(int id) {
        return itemRepository.findById(id).orElse(null);
    }
}
