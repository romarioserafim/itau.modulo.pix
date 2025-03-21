package com.modulo.pix.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.models.PixKeyModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixKeyResponseDeleteDto {

    @JsonProperty("id")
    private UUID id;

    public PixKeyResponseDeleteDto(PixKeyModel pixKey) {
        this.id = pixKey.getId();

        this.keyType = pixKey.getKeyType();
        this.keyValue = pixKey.getKeyValue();
        this.accountType = pixKey.getAccount().getAccountType();
        this.agencyNumber = pixKey.getAccount().getAgencyNumber();
        this.accountNumber = pixKey.getAccount().getAccountNumber();
        this.accountHolderFirstName = pixKey.getAccount().getAccountHolderFirstName();
        this.accountHolderLastName = pixKey.getAccount().getAccountHolderLastName();
        this.createdAt = pixKey.getCreatedAt();
        this.disabledAt = pixKey.getDisabledAt();
    }


    @JsonProperty("tipo_chave")
    KeyType keyType;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("tipo_conta")
    AccountType accountType;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("numero_agencia")
    String agencyNumber;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("numero_conta")
    String accountNumber;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("nome_correntista")
    String accountHolderFirstName;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("sobrenome_correntista")
    String accountHolderLastName;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("valor_chave")
    String keyValue;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("data_inclusao_chave")
    LocalDateTime createdAt;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("data_inativacao_chave")
    LocalDateTime disabledAt;
}
