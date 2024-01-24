package com.codegym.service.impl;

import com.codegym.model.Role;
import com.codegym.repository.IRoleRepository;
import com.codegym.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleService implements IRoleService {
    private final IRoleRepository iRoleRepository;

    public RoleService(IRoleRepository iRoleRepository) {
        this.iRoleRepository = iRoleRepository;
    }

    @Override
    public Iterable<Role> findAll() {
        return iRoleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return iRoleRepository.findById(id);
    }

    @Override
    public void save(Role role) {
        iRoleRepository.save(role);
    }

    @Override
    public void remove(Long id) {
        iRoleRepository.deleteById(id);
    }

    @Override
    public Role findByName(String name) {
        return iRoleRepository.findByName(name);
    }
}
