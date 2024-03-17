package com.indivie.cms.service.impl;

import com.indivie.cms.constant.OutletType;
import com.indivie.cms.constant.UserRole;
import com.indivie.cms.dto.request.AuthRequest;
import com.indivie.cms.dto.response.LoginResponse;
import com.indivie.cms.dto.response.RegisterResponse;
import com.indivie.cms.entity.Outlet;
import com.indivie.cms.entity.Role;
import com.indivie.cms.entity.UserAccount;
import com.indivie.cms.repository.UserAccountRepository;
import com.indivie.cms.service.AuthService;
import com.indivie.cms.service.JwtService;
import com.indivie.cms.service.OutletService;
import com.indivie.cms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final OutletService outletService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${cms.username.superadmin}")
    private String superAdminUsername;
    @Value("${cms.password.superadmin}")
    private String superAdminPassword;

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();
        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .token(token)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest request) throws DataIntegrityViolationException {
        Role role = roleService.getOrSave(UserRole.ROLE_OUTLET);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(role))
                .isEnable(true)
                .build();

        userAccountRepository.saveAndFlush(userAccount);

        Outlet outlet = Outlet.builder()
                .type(OutletType.REGULAR_MERCHANDISER_OUTLET)
                .userAccount(userAccount)
                .build();
        outletService.create(outlet);

        List<String> roles = userAccount.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerMain(AuthRequest request) {
        Role outletRole = roleService.getOrSave(UserRole.ROLE_OUTLET);
        Role mainStorageRole = roleService.getOrSave(UserRole.ROLE_MAIN_STORAGE);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(outletRole, mainStorageRole))
                .isEnable(true)
                .build();

        userAccountRepository.saveAndFlush(userAccount);

        Outlet outlet = Outlet.builder()
                .type(OutletType.MAIN_STORAGE_OUTLET)
                .userAccount(userAccount)
                .build();
        outletService.create(outlet);

        List<String> roles = userAccount.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        Role outletRole = roleService.getOrSave(UserRole.ROLE_OUTLET);
        Role mainStorageRole = roleService.getOrSave(UserRole.ROLE_MAIN_STORAGE);
        Role adminRole = roleService.getOrSave(UserRole.ROLE_ADMIN);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(outletRole, mainStorageRole, adminRole))
                .isEnable(true)
                .build();

        userAccountRepository.saveAndFlush(userAccount);

        Outlet outlet = Outlet.builder()
                .type(OutletType.MAIN_STORAGE_OUTLET)
                .userAccount(userAccount)
                .build();
        outletService.create(outlet);

        List<String> roles = userAccount.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .roles(roles)
                .build();
    }
}
