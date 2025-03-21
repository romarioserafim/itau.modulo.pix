package com.modulo.pix.controllers;

import com.modulo.pix.dto.request.PixKeyRequestDto;
import com.modulo.pix.dto.response.PixKeyResponseCreateDto;
import com.modulo.pix.dto.response.PixKeyResponseDeleteDto;
import com.modulo.pix.exception.custom.ResourceNotFoundException;
import com.modulo.pix.services.PixKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pix")
public class PixKeyController {
    private final PixKeyService service;

    public PixKeyController(PixKeyService service){
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<PixKeyResponseCreateDto> createPixKey(@Validated @RequestBody PixKeyRequestDto pixKeyRequestDto) {
        return ResponseEntity.ok(service.createPixKey(pixKeyRequestDto));
    }

    @DeleteMapping("/inative/{id}")
    public ResponseEntity<PixKeyResponseDeleteDto> inativePix(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.inactivePix(id));
        } catch (RuntimeException ex) {
            throw new ResourceNotFoundException("Id Chave pix invalida");
        }

    }
}
