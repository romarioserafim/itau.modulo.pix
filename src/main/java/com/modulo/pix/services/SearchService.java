package com.modulo.pix.services;

import com.modulo.pix.dto.request.SearchRequestDto;
import com.modulo.pix.dto.response.SearchResponseDto;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final AccountRepository accountRepository;

    public SearchService(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    public SearchResponseDto searchById(UUID id) {
        List<AccountModel> pixForAccount = accountRepository.findAccountByPixKeyId(id);

        if(pixForAccount.isEmpty()){
            throw new  ResourceNotFoundException("Sem Retorno para o Id informado");
        }
        return new SearchResponseDto(pixForAccount.get(0));
    }

    public List<SearchResponseDto> searchByFilter(SearchRequestDto request) {
        String type  = request.getDateFilter() != null ? request.getDateFilter().name() : null;
        List<AccountModel> AccountList =  accountRepository.findAccountsWithPixKeys(
                request.getAccountType(),
                request.getAgencyNumber(),
                request.getAccountNumber(),
                request.getAccountHolderFirstName(),
                request.getAccountHolderLastName(),
                request.getClientType(),
                request.getKeyType(),
                request.getKeyValue(),
                type,
                request.getDtStart(),
                request.getDtEnd()

        ) ;

        if(AccountList.isEmpty()){
            throw new  ResourceNotFoundException("Sem Retorno para os filtros informados");
        }

        return convertToDtoList(AccountList);
    }

    public List<SearchResponseDto> convertToDtoList(List<AccountModel> accounts) {
        return accounts.stream()
                .map(SearchResponseDto::new)
                .collect(Collectors.toList());
    }
}
