package com.indivie.cms.service;

import com.indivie.cms.dto.response.JwtClaims;
import com.indivie.cms.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
