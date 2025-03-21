package com.modulo.pix.services;

import com.modulo.pix.dto.request.SearchRequestDto;
import com.modulo.pix.dto.response.SearchResponseDto;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.models.PixKeyModel;
import com.modulo.pix.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {
    @InjectMocks
    private SearchService searchService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchById_NotFound() {
        UUID pixKeyId = UUID.randomUUID();
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            searchService.searchById(pixKeyId);
        });

        assertEquals("Sem Retorno para o Id informado", thrown.getMessage());
    }


    @Test
    void testSearchByFilter_NotFound() {
        SearchRequestDto request = new SearchRequestDto();

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            searchService.searchByFilter(request);
        });

        assertEquals("Sem Retorno para os filtros informados", thrown.getMessage());
    }

    @Test
    void testConvertToDtoList() {
        UUID pixKeyId = UUID.randomUUID();
        AccountModel account = createAccountModel(pixKeyId);

        List<SearchResponseDto> responseList = searchService.convertToDtoList(List.of(account));
        assertFalse(responseList.isEmpty());
        assertEquals("567899", responseList.get(0).getAgencyNumber());
    }

    private AccountModel createAccountModel(UUID pixKeyId) {
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

        account.setPixKeys(List.of(pixKey));
        return account;
    }
}
