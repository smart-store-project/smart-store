package com.codegym.service;

import com.codegym.model.Role;
import com.codegym.service.IGenerateService;

public interface IRoleService extends IGenerateService<Role> {
    Role findByName(String name);
}
