package com.codegym.service.impl;

import com.codegym.model.User;
import com.codegym.model.Wallet;
import com.codegym.repository.IWalletRepository;
import com.codegym.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService implements IWalletService {
    @Autowired
    private IWalletRepository iWalletRepository;

    @Override
    public Wallet findById(Long id) {
        return iWalletRepository.findById(id).orElse(null);
    }

    @Override
    public Wallet findByUser(User user) {
        return iWalletRepository.findByUser(user);
    }

    @Override
    public void save(Wallet wallet) {
        iWalletRepository.save(wallet);
    }
}
