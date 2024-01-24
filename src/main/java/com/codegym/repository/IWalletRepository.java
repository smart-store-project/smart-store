package com.codegym.repository;

import com.codegym.model.User;
import com.codegym.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUser(User user);
}
