package com.modulo.pix.services;

import com.modulo.pix.dto.request.PixKeyRequestDto;
import com.modulo.pix.dto.response.PixKeyResponseCreateDto;
import com.modulo.pix.dto.response.PixKeyResponseDeleteDto;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.exception.custom.DataUnprocessableException;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.models.PixKeyModel;
import com.modulo.pix.repositories.AccountRepository;
import com.modulo.pix.repositories.PixKeyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PixKeyServiceTest {

    @Mock
    private PixKeyRepository pixKeyRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private PixKeyService pixKeyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreatePixKeySuccessfully() {
        PixKeyRequestDto requestDto = new PixKeyRequestDto();
        requestDto.setKeyValue("+5511994853030");
        requestDto.setKeyType(KeyType.CELULAR);
        requestDto.setAccount_id(UUID.randomUUID());

        AccountModel accountModel = new AccountModel();
        accountModel.setId(requestDto.getAccount_id());
        accountModel.setClientType(ClientType.PF);

        when(pixKeyRepository.findByKeyValueAndStatus(requestDto.getKeyValue(), 1)).thenReturn(null);
        when(accountRepository.findById(requestDto.getAccount_id())).thenReturn(Optional.of(accountModel));

        PixKeyModel savedPixKey = new PixKeyModel();
        savedPixKey.setId(UUID.randomUUID());
        savedPixKey.setKeyValue(requestDto.getKeyValue());
        savedPixKey.setAccount(accountModel);
        savedPixKey.setStatus(1);

        when(pixKeyRepository.save(any(PixKeyModel.class))).thenReturn(savedPixKey);
        PixKeyResponseCreateDto response = pixKeyService.createPixKey(requestDto);

        assertNotNull(response);
        assertEquals(savedPixKey.getId(), response.getId());
        verify(pixKeyRepository, times(1)).save(any(PixKeyModel.class));
    }

@Test
void shouldThrowExceptionWhenPixKeyAlreadyExists() {

    PixKeyRequestDto requestDto = new PixKeyRequestDto();
    requestDto.setKeyValue("+5511994853030");
    requestDto.setKeyType(KeyType.CELULAR);
    requestDto.setAccount_id(UUID.randomUUID());

    PixKeyModel existingPixKey = new PixKeyModel();
    existingPixKey.setKeyValue(requestDto.getKeyValue());
    existingPixKey.setStatus(1);

    when(pixKeyRepository.findByKeyValueAndStatus(requestDto.getKeyValue(), 1)).thenReturn(existingPixKey);
    when(accountRepository.findById(requestDto.getAccount_id())).thenReturn(Optional.empty());

    assertThrows(DataUnprocessableException.class, () -> pixKeyService.createPixKey(requestDto));
}
    @Test
    void shouldInactivePixKeySuccessfully() {

        UUID pixKeyId = UUID.randomUUID();
        PixKeyModel pixKeyModel = new PixKeyModel();
        pixKeyModel.setId(pixKeyId);
        pixKeyModel.setStatus(1);
        AccountModel accountModel = new AccountModel();
        accountModel.setId(UUID.randomUUID());
        accountModel.setAccountHolderFirstName("joao");
        accountModel.setAccountHolderLastName("batista");
        accountModel.setAccountNumber("4790");
        accountModel.setClientType(ClientType.PF);
        accountModel.setAgencyNumber("567899");
        accountModel.setAccountType(AccountType.CORRENTE);
        accountModel.setCreatedAt(LocalDateTime.now());
        pixKeyModel.setAccount(accountModel);

        when(pixKeyRepository.findById(pixKeyId)).thenReturn(Optional.of(pixKeyModel));
        when(pixKeyRepository.save(any(PixKeyModel.class))).thenReturn(pixKeyModel);

        PixKeyResponseDeleteDto response = pixKeyService.inactivePix(pixKeyId);

        assertNotNull(response);
        assertEquals(0, pixKeyModel.getStatus());
        verify(pixKeyRepository, times(1)).save(pixKeyModel);
    }

    @Test
    void shouldThrowExceptionWhenPixKeyNotFound() {
        UUID pixKeyId = UUID.randomUUID();
        when(pixKeyRepository.findById(pixKeyId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pixKeyService.inactivePix(pixKeyId));
    }
}
