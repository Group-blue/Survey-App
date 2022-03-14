package com.bilgeadam.controller;

import com.bilgeadam.config.security.user.LoginUserService;
import com.bilgeadam.dto.request.LoginUserRequestDto;
import com.bilgeadam.dto.response.LoginUserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    private final LoginUserService loginUserService;

    public AuthController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(@RequestBody LoginUserRequestDto dto){
        return ResponseEntity.ok(loginUserService.login(dto));
    }
}
