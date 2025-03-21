package com.modulo.pix.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modulo.pix.models.PixKeyModel;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixKeyResponseCreateDto {

    @JsonProperty("id")
    private UUID id;

    public PixKeyResponseCreateDto(PixKeyModel pixKey) {
        this.id = pixKey.getId();
    }
}
