package com.indivie.cms.service;

import com.indivie.cms.dto.request.AuthRequest;
import com.indivie.cms.dto.response.LoginResponse;
import com.indivie.cms.dto.response.RegisterResponse;

public interface AuthService {
    LoginResponse login(AuthRequest request);
    RegisterResponse register(AuthRequest request);
    RegisterResponse registerMain(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
}
