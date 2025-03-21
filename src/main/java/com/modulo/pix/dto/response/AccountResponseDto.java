package com.modulo.pix.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.models.AccountModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AccountResponseDto {

    public AccountResponseDto(AccountModel accountModel) {
        this.setId(accountModel.getId());
        this.setAccountType(accountModel.getAccountType());
        this.setAgencyNumber(accountModel.getAgencyNumber());
        this.setAccountNumber(accountModel.getAccountNumber());
        this.setAccountHolderFirstName(accountModel.getAccountHolderFirstName());
        this.setAccountHolderLastName(accountModel.getAccountHolderLastName());
        this.setClientType(accountModel.getClientType());
        this.setCreatedAt(accountModel.getCreatedAt());
        this.setUpdatedAt(accountModel.getUpdatedAt());

    }
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("tipo_conta")
    private AccountType accountType;

    @JsonProperty("numero_agencia")
    private String agencyNumber;

    @JsonProperty("numero_conta")
    private String accountNumber;

    @JsonProperty("nome_correntista")
    private String accountHolderFirstName;

    @JsonProperty("sobrenome_correntista")
    private String accountHolderLastName;

    @JsonProperty("data_hora_inclusao")
    private LocalDateTime createdAt;

    @JsonProperty("data_hora_ultima_atualizacao")
    private LocalDateTime updatedAt;

    @JsonProperty("tipo_cliente")
    private ClientType clientType;
}
