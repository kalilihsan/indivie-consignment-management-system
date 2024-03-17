package com.indivie.cms.service;

import com.indivie.cms.constant.UserRole;
import com.indivie.cms.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
