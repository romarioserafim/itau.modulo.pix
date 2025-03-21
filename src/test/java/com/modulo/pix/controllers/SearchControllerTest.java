package com.modulo.pix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modulo.pix.dto.request.SearchRequestDto;
import com.modulo.pix.dto.response.SearchResponseDto;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.exception.GlobalExceptionHandler;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.models.PixKeyModel;
import com.modulo.pix.services.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SearchControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchController searchController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(searchController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

         objectMapper = new ObjectMapper();

    }

    @Test
    void testSearchById_Success() throws Exception {
        UUID pixKeyId = UUID.randomUUID();

        AccountModel account = new AccountModel();
        account.setId(UUID.randomUUID());
        account.setAccountHolderFirstName("João");
        account.setAccountHolderLastName("Batista");
        account.setAccountNumber("4790");
        account.setClientType(ClientType.PF);
        account.setAgencyNumber("567899");
        account.setAccountType(AccountType.CORRENTE);
        account.setCreatedAt(LocalDateTime.now());

        PixKeyModel pixKey = new PixKeyModel();
        pixKey.setKeyType(KeyType.CELULAR);
        pixKey.setKeyValue("+5511994853030");
        pixKey.setId(pixKeyId);
        pixKey.setCreatedAt(LocalDateTime.now());

        account.setPixKeys(Arrays.asList(pixKey));

        SearchResponseDto response = new SearchResponseDto(account);

        when(searchService.searchById(pixKeyId)).thenReturn(response);

        mockMvc.perform(get("/search/key-id/{id}", pixKeyId))
                .andExpect(status().isOk()).equals(response);
    }

    @Test
    void testSearchById_NotFound() throws Exception {
        UUID pixKeyId = UUID.randomUUID();

        when(searchService.searchById(pixKeyId)).thenThrow(new ResourceNotFoundException("Sem Retorno para o Id informado"));
        mockMvc.perform(get("/search/key-id/{id}", pixKeyId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Sem Retorno para o Id informado"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testSearchByFilters_Success() throws Exception {

        UUID pixKeyId = UUID.randomUUID();

        AccountModel account = new AccountModel();
        account.setId(UUID.randomUUID());
        account.setAccountHolderFirstName("João");
        account.setAccountHolderLastName("Batista");
        account.setAccountNumber("4790");
        account.setClientType(ClientType.PF);
        account.setAgencyNumber("567899");
        account.setAccountType(AccountType.CORRENTE);
        account.setCreatedAt(LocalDateTime.now());

        PixKeyModel pixKey = new PixKeyModel();
        pixKey.setKeyType(KeyType.CELULAR);
        pixKey.setKeyValue("+5511994853030");
        pixKey.setId(pixKeyId);
        pixKey.setCreatedAt(LocalDateTime.now());

        account.setPixKeys(Arrays.asList(pixKey));

        SearchRequestDto searchRequest = new SearchRequestDto(); // Preencha os filtros conforme necessário

        SearchResponseDto response1 = new SearchResponseDto(account); // Simulação de resposta 1
        SearchResponseDto response2 = new SearchResponseDto(account); // Simulação de resposta 2

        when(searchService.searchByFilter(any(SearchRequestDto.class))).thenReturn(Arrays.asList(response1, response2));


        mockMvc.perform(post("/search/filtrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest))) // Converte o DTO para JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    void testSearchByFilters_NoResults() throws Exception {
        SearchRequestDto searchRequest = new SearchRequestDto();

        when(searchService.searchByFilter(any(SearchRequestDto.class))).thenReturn(List.of());

        mockMvc.perform(post("/search/filtrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
