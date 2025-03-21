package com.modulo.pix.controllers;

import com.modulo.pix.dto.request.PixKeyRequestDto;
import com.modulo.pix.dto.response.PixKeyResponseCreateDto;
import com.modulo.pix.dto.response.PixKeyResponseDeleteDto;
import com.modulo.pix.enums.AccountType;
import com.modulo.pix.enums.ClientType;
import com.modulo.pix.enums.KeyType;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.models.AccountModel;
import com.modulo.pix.models.PixKeyModel;
import com.modulo.pix.services.PixKeyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class PixKeyControllerTest {

    private MockMvc mockMvc;
    private PixKeyModel pixKeyModel;
    private AccountModel accountModel;
    @Mock
    private PixKeyService pixKeyService;

    @InjectMocks
    private PixKeyController pixKeyController;

    @BeforeEach
    void setUp() {
        pixKeyModel = new PixKeyModel();
        accountModel = new AccountModel();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pixKeyController).build();
    }

    @Test
    void testCreatePixKey() throws Exception {
        PixKeyRequestDto pixKeyRequestDto = new PixKeyRequestDto();
        pixKeyRequestDto.setAccount_id(UUID.randomUUID());
        pixKeyRequestDto.setKeyType(KeyType.CELULAR);
        pixKeyRequestDto.setKeyValue("+5511994853030");
        pixKeyModel.setId(UUID.randomUUID());

        PixKeyResponseCreateDto response = new PixKeyResponseCreateDto(pixKeyModel);


        when(pixKeyService.createPixKey(pixKeyRequestDto)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/pix/create")
                        .contentType("application/json")
                        .content("{\"id_conta\":\"" + pixKeyRequestDto.getAccount_id() + "\", \"tipo_chave\":\"CELULAR\", \"valor_chave\":\"+5511994853030\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void testInativePix() throws Exception {
        UUID pixKeyId = UUID.randomUUID();

        pixKeyModel.setId(pixKeyId);
        pixKeyModel.setKeyType(KeyType.CELULAR);
        pixKeyModel.setKeyValue("123456789");
        accountModel.setId( UUID.randomUUID());
        accountModel.setClientType(ClientType.PJ);
        accountModel.setAccountType(AccountType.CORRENTE);
        accountModel.setAgencyNumber("001");
        accountModel.setAccountNumber("12345");
        accountModel.setAccountHolderFirstName("John");
        accountModel.setAccountHolderLastName("Doe");
        accountModel.setCreatedAt(LocalDateTime.now());
        pixKeyModel.setAccount(accountModel);
        pixKeyModel.setCreatedAt(LocalDateTime.now());
        PixKeyResponseDeleteDto response = new PixKeyResponseDeleteDto(pixKeyModel);

        when(pixKeyService.inactivePix(pixKeyId)).thenReturn(response);

        // Faz a requisição DELETE
        mockMvc.perform(MockMvcRequestBuilders.delete("/pix/inative/{id}", pixKeyId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(pixKeyId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo_chave").value("CELULAR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor_chave").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo_conta").value("CORRENTE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numero_agencia").value("001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numero_conta").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome_correntista").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sobrenome_correntista").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data_inclusao_chave").exists());
    }

    @Test
    void testCreatePixKey_BadRequest() throws Exception {

        PixKeyRequestDto pixKeyRequestDto = new PixKeyRequestDto();
        pixKeyRequestDto.setAccount_id(UUID.randomUUID());
        pixKeyRequestDto.setKeyType(null);
        pixKeyRequestDto.setKeyValue("");

        mockMvc.perform(MockMvcRequestBuilders.post("/pix/create")
                        .contentType("application/json")
                        .content("{\"id_conta\":\"" + pixKeyRequestDto.getAccount_id() + "\", \"tipo_chave\": null, \"valor_chave\":\"\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testInativePix_NotFound() throws Exception {

        UUID pixKeyId = UUID.randomUUID();

        when(pixKeyService.inactivePix(pixKeyId)).thenThrow(new ResourceNotFoundException("Id Chave pix invalida"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/pix/inative/{id}", pixKeyId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
