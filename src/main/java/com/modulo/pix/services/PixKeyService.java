package com.modulo.pix.services;

import com.modulo.pix.dto.request.PixKeyRequestDto;
import com.modulo.pix.dto.response.PixKeyResponseCreateDto;
import com.modulo.pix.dto.response.PixKeyResponseDeleteDto;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.exception.custom.DataUnprocessableException;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.models.PixKeyModel;
import com.modulo.pix.repositories.AccountRepository;
import com.modulo.pix.repositories.PixKeyRepository;
import com.modulo.pix.utils.Helpers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PixKeyService {
    private final PixKeyRepository pixKeyRepository;
    private final AccountRepository accountRepository;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+\\d{1,2}\\d{2}\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{1,77}$)[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,7}$");
    private static final Pattern ALEATORIO_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,36}$");

    public PixKeyService(PixKeyRepository pixKeyRepository, AccountRepository accountRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountRepository = accountRepository;
    }

    public PixKeyResponseCreateDto createPixKey(PixKeyRequestDto pixKeyRequestDto) {
        validatePixKey(pixKeyRequestDto);
        PixKeyModel pixKeyModel = pixKeyRepository.findByKeyValueAndStatus(pixKeyRequestDto.getKeyValue(), 1);
        Optional<AccountModel> accountModel = accountRepository.findById(pixKeyRequestDto.getAccount_id());
        validatePixKeyWithAccount(accountModel);

        if (pixKeyModel != null && pixKeyModel.getStatus() == 1) {
            throw new DataUnprocessableException("Chave pix ja cadastrada");
        }

        PixKeyModel pixKey = new PixKeyModel();
        BeanUtils.copyProperties(pixKeyRequestDto, pixKey);
        pixKey.setStatus(1);
        pixKey.setAccount(accountModel.get());

        return new PixKeyResponseCreateDto(this.pixKeyRepository.save(pixKey));

    }
    public PixKeyResponseDeleteDto inactivePix(UUID id) {

        PixKeyModel pixKeyModel = pixKeyRepository.findById(id).orElse(null);
        if (pixKeyModel == null ) {
            throw new ResourceNotFoundException("Id Chave pix invalida");
        }
        if (pixKeyModel.getStatus() == 0) {
            throw new DataUnprocessableException("Chave pix ja esta desativada");
        }

        pixKeyModel.setStatus(0);
        pixKeyModel.setDisabledAt(LocalDateTime.now());


        return new PixKeyResponseDeleteDto(this.pixKeyRepository.save(pixKeyModel));

    }

    private void validatePixKey(PixKeyRequestDto dto) {
        String value = dto.getKeyValue();
        boolean isValid;
        switch (dto.getKeyType()) {
            case CELULAR:
                isValid = PHONE_PATTERN.matcher(value).matches();
                break;
            case EMAIL:
                isValid = EMAIL_PATTERN.matcher(value).matches();
                break;
            case CPF:
                isValid = Helpers.validCpf(value);
                break;
            case CNPJ:
                isValid = Helpers.validCnpj(value);
                break;
            case ALEATORIO:
                isValid = ALEATORIO_PATTERN.matcher(value).matches();
                break;
            default:
                throw new DataUnprocessableException("Tipo de chave PIX inválido");
        }

        if (!isValid) {
            throw new DataUnprocessableException("Chave PIX inválida para o tipo selecionado");
        }
    }

    private void validatePixKeyWithAccount(Optional<AccountModel> accountModel) {
        if (accountModel.isEmpty()) {
            throw new DataUnprocessableException("Conta não encontrada para validação de chave PIX");
        }

        List<PixKeyModel> pixKeys = accountModel.get().getPixKeys().stream()
                .filter(e -> e.getStatus() == 1)
                .collect(Collectors.toList());

        accountModel.ifPresent(account -> {
            if (account.getClientType() == ClientType.PF && pixKeys.size() > 4) {
                throw new DataUnprocessableException("Limite de chave pix atingido");
            }
            if (account.getClientType() == ClientType.PJ && pixKeys.size() > 19) {
                throw new DataUnprocessableException("Limite de chave pix atingido");
            }
        });
    }
}
