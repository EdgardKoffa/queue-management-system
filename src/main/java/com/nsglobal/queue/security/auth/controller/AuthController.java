package com.nsglobal.queue.security.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.security.auth.AuthService;
import com.nsglobal.queue.security.auth.dto.LoginRequestDto;
import com.nsglobal.queue.security.auth.dto.LoginResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.AUTH)
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid 
            @RequestBody 
            LoginRequestDto dto) {
    	
    	System.out.println("====== LOGIN ====== \n"+dto.getUserName()+" "+dto.getPassword());
       
    	LoginResponseDto response = authService.login(dto);

        return ResponseEntity.ok(response);

    }

    

}
