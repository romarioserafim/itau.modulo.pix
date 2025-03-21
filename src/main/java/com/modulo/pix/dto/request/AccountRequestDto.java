package com.modulo.pix.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccountRequestDto {
    @NotNull(message = "tipo_conta é obrigatorio"
    )
    @JsonProperty("tipo_conta")
    AccountType accountType;

    @Pattern(regexp = "^\\d{1,4}$", message = "O numero_agencia deve conter até 4 dígitos e numericos")
    @NotBlank(message = "numero_agencia é obrigatorio")
    @JsonProperty("numero_agencia")
    String agencyNumber;

    @Pattern(regexp = "^\\d{1,8}$", message = "O numero_conta deve conter até 8 dígitos e numericos")
    @NotBlank(message = "numero_conta é Obrigatorio")
    @JsonProperty("numero_conta")
    String accountNumber;

    @Size(max = 30, message = "nome_correntista deve ter no máximo 30 caracteres")
    @NotBlank(message = "nome_correntista é obrigatório"
    )
    @JsonProperty("nome_correntista")
    String accountHolderFirstName;

    @Size(max = 45, message = "sobrenome_correntista deve ter no máximo 45 caracteres")
    @JsonProperty("sobrenome_correntista")
    String accountHolderLastName;


    @NotNull(message = "tipo_cliente é Obrigatório")
    @JsonProperty("tipo_cliente")
    ClientType clientType;

}