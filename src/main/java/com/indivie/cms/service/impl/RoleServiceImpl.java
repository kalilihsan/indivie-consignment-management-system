package com.indivie.cms.service.impl;

import com.indivie.cms.constant.UserRole;
import com.indivie.cms.entity.Role;
import com.indivie.cms.repository.RoleRepository;
import com.indivie.cms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role.name())
                .orElseGet(() -> roleRepository.saveAndFlush(Role.builder()
                        .role(role)
                        .build())
                );
    }
}
