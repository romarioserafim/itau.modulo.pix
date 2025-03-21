package com.modulo.pix.controllers;


import com.modulo.pix.dto.request.SearchRequestDto;
import com.modulo.pix.dto.response.SearchResponseDto;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.services.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/key-id/{id}")
    public ResponseEntity<SearchResponseDto> searchById(@PathVariable UUID id) {
        try {
            SearchResponseDto dataset = searchService.searchById(id);
            return ResponseEntity.ok(dataset);
        } catch (RuntimeException ex) {
            throw new ResourceNotFoundException("Sem Retorno para o Id informado");
        }
    }

    @PostMapping("/filter")
    public  ResponseEntity<List<SearchResponseDto>> searchByFilters(@Validated @RequestBody SearchRequestDto account) {
        List<SearchResponseDto> dataset = searchService.searchByFilter(account);
        return ResponseEntity.ok(dataset);
    }

}
