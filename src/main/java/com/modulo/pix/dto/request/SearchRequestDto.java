package com.modulo.pix.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.enums.DataFilter;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.validation.ValidateDate;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class SearchRequestDto {

    @ValidateDate(message = "A data_inicio deve estar no formato yyyy-MM-dd'T'HH:mm:ss e ser válida")
    @JsonProperty("data_inicio")
    private LocalDateTime dtStart;

    @ValidateDate(message = "A data_fim deve estar no formato yyyy-MM-dd'T'HH:mm:ss e ser válida")
    @JsonProperty("data_fim")
    private LocalDateTime dtEnd;

    @JsonProperty("tipo_filtro_data")
    private DataFilter dateFilter;

    @JsonProperty("tipo_conta")
    AccountType accountType;

    @JsonProperty("numero_agencia")
    String agencyNumber;

    @JsonProperty("numero_conta")
    String accountNumber;

    @JsonProperty("nome_correntista")
    String accountHolderFirstName;

    @JsonProperty("sobrenome_correntista")
    String accountHolderLastName;

    @JsonProperty("tipo_cliente")
    ClientType clientType;

    @JsonProperty("tipo_chave")
    KeyType keyType;

    @JsonProperty("valor_chave")
    private String keyValue;

}
