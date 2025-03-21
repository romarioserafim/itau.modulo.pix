package com.modulo.pix.services;

import com.modulo.pix.dto.request.AccountRequestDto;
import com.modulo.pix.dto.response.AccountResponseDto;
import com.modulo.pix.exception.custom.DataUnprocessableException;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.repositories.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        AccountModel existAccount = accountRepository.findByAccountNumber(accountRequestDto.getAccountNumber());

        if (existAccount != null) {
            throw new DataUnprocessableException("Numero de conta já cadastrada");
        }
        AccountModel account = new AccountModel();
        BeanUtils.copyProperties(accountRequestDto, account);

        return new AccountResponseDto(accountRepository.save(account));
    }

    public AccountResponseDto updateAccount(UUID id, AccountRequestDto accountRequestDto) {
        AccountModel existAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id de conta inválido"));

        existAccount.setAccountNumber(accountRequestDto.getAccountNumber());
        existAccount.setAgencyNumber(accountRequestDto.getAgencyNumber());
        existAccount.setAccountHolderFirstName(accountRequestDto.getAccountHolderFirstName());
        existAccount.setAccountHolderLastName(accountRequestDto.getAccountHolderLastName());
        existAccount.setClientType(accountRequestDto.getClientType());
        existAccount.setAccountType(accountRequestDto.getAccountType());

        return new AccountResponseDto(accountRepository.save(existAccount));

    }
}
