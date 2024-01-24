package com.codegym.service;

import com.codegym.model.User;
import com.codegym.model.Wallet;

public interface IWalletService {
    Wallet findById(Long id);

    Wallet findByUser(User user);
    void save(Wallet wallet);
}
