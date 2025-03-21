package com.modulo.pix.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.models.AccountModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponseDto {
    public SearchResponseDto(AccountModel account) {
            this.id = account.getId();
            this.accountType = account.getAccountType();
            this.agencyNumber = account.getAgencyNumber();
            this.accountNumber = account.getAccountNumber();
            this.accountHolderFirstName = account.getAccountHolderFirstName();
            this.accountHolderLastName = account.getAccountHolderLastName();
            this.createdAt = account.getCreatedAt();

            this.pix = account.getPixKeys().stream()
                    .map(SearchPixKeyList::new)
                    .collect(Collectors.toList());

    }

    @JsonProperty("id_conta")
    UUID id;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("tipo_conta")
    AccountType accountType;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonProperty("numero_agencia")
    String agencyNumber;

    @JsonProperty("pix_keys")
    List<?> pix;

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
    @JsonProperty("data_inclusao")
    LocalDateTime createdAt;


}
