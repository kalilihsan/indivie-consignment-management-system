package com.indivie.cms.service;

import com.indivie.cms.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount getByUserId(String id);
    UserAccount getByContext();
}
