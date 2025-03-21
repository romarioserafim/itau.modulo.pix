package com.modulo.pix.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modulo.pix.enums.KeyType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class PixKeyRequestDto {

    @NotNull(message = "O id_conta é obrigatorio"
    )
    @JsonProperty("id_conta")
    UUID account_id;

    @NotNull(message = "O tipo_chave é obrigatorio"
    )
    @JsonProperty("tipo_chave")
    KeyType keyType;

    @NotNull(message = "Valor da chave não pode ser nulo")
    @JsonProperty("valor_chave")
    private String keyValue;

}
