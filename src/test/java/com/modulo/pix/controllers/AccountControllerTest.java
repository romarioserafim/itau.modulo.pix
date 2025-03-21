package com.modulo.pix.controllers;

import com.modulo.pix.dto.request.AccountRequestDto;
import com.modulo.pix.dto.response.AccountResponseDto;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.repositories.AccountRepository;
import com.modulo.pix.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private UUID accountId;
    private AccountRequestDto requestDto;
    private AccountResponseDto responseDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountId = UUID.randomUUID();
        requestDto = new AccountRequestDto();
        AccountModel mockAccount = new AccountModel();
        responseDto = new AccountResponseDto(mockAccount);


    }

    @Test
    void shouldCreateAccountSuccessfully() {

        AccountRequestDto requestDto = new AccountRequestDto();
        requestDto.setAccountHolderFirstName("joao");
        requestDto.setAccountHolderLastName("batista");
        requestDto.setAccountNumber("4790");
        requestDto.setClientType(ClientType.PF);
        requestDto.setAgencyNumber("567899");
        requestDto.setAccountType(AccountType.CORRENTE);

        AccountModel accountModel = new AccountModel();
        accountModel.setId(UUID.randomUUID());
        accountModel.setAccountHolderFirstName("joao");
        accountModel.setAccountHolderLastName("batista");
        accountModel.setAccountNumber("4790");
        accountModel.setClientType(ClientType.PF);
        accountModel.setAgencyNumber("567899");
        accountModel.setAccountType(AccountType.CORRENTE);
        accountModel.setCreatedAt(LocalDateTime.now());

        AccountResponseDto mockResponse = new AccountResponseDto(accountModel);

        when(accountService.createAccount(requestDto)).thenReturn(mockResponse);

        ResponseEntity<?> response = accountController.createAccount(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(accountService, times(1)).createAccount(requestDto);
    }

    @Test
    void shouldUpdateAccountSuccessfully() {

        when(accountService.updateAccount(any(UUID.class), any(AccountRequestDto.class)))
                .thenReturn(responseDto);

        ResponseEntity<?> response = accountController.updateAccount(accountId, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }
}
