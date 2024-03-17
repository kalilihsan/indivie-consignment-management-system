package com.indivie.cms.controller;

import com.indivie.cms.constant.ApiUrl;
import com.indivie.cms.dto.request.AuthRequest;
import com.indivie.cms.dto.response.CommonResponse;
import com.indivie.cms.dto.response.LoginResponse;
import com.indivie.cms.dto.response.RegisterResponse;
import com.indivie.cms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.AUTH_API)
public class AuthController {
    private final AuthService authService;
    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> registerOutlet(@RequestBody AuthRequest request) {
        RegisterResponse register = authService.register(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully saved data")
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/register/main-storage",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> registerMainStorageOutlet(@RequestBody AuthRequest request) {
        RegisterResponse register = authService.registerMain(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully saved data")
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> login(@RequestBody AuthRequest request) {
        LoginResponse loginResponse = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .data(loginResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
