package com.modulo.pix.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.models.PixKeyModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchPixKeyList {

    public SearchPixKeyList(PixKeyModel pixKeyModel) {
        this.id = pixKeyModel.getId();
        this.keyType = pixKeyModel.getKeyType();
        this.keyValue = pixKeyModel.getKeyValue();
        this.createdAt = pixKeyModel.getCreatedAt();
        this.disabledAt = pixKeyModel.getDisabledAt();
    }

    @JsonProperty("id")
    UUID id;

    @JsonProperty("tipo_chave")
    KeyType keyType;

    @JsonProperty("valor_chave")
    String keyValue;

    @JsonProperty("data_inclusao_chave")
    LocalDateTime createdAt;

    @JsonProperty("data_inativacao_chave")
    LocalDateTime disabledAt;
}
