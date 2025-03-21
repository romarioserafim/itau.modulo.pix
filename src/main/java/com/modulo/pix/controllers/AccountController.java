package com.modulo.pix.controllers;

import com.modulo.pix.dto.request.AccountRequestDto;
import com.modulo.pix.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Validated @RequestBody AccountRequestDto account) {
        return ResponseEntity.ok(service.createAccount(account));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable UUID id, @Validated @RequestBody AccountRequestDto accountRequest) {
        return ResponseEntity.ok(service.updateAccount(id, accountRequest));

    }


}
