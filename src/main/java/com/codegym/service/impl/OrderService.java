package com.codegym.service.impl;


import com.codegym.exception.UnavailableBalanceException;
import com.codegym.model.Cart;
import com.codegym.model.CartItem;
import com.codegym.model.Order;
import com.codegym.model.OrderStatus;
import com.codegym.model.User;
import com.codegym.model.Wallet;
import com.codegym.model.dto.UserDto;
import com.codegym.repository.IOrderRepository;
import com.codegym.service.IOrderService;
import com.codegym.service.IUserService;
import com.codegym.service.IUserService2;
import com.codegym.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
    private IWalletService walletService;

    @Autowired
    private IUserService2 userService2;



    @Override
    public Iterable<Order> findAll() {
        return iOrderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return iOrderRepository.findById(id);
    }

    @Override
    public void save(Order order) {
        iOrderRepository.save(order);

    }

    @Override
    public void remove(Long id) {
        iOrderRepository.deleteById(id);
    }



    @Override
    public Iterable<Order> findAllByUserAndOrderStatus(User user, OrderStatus orderStatus) {
        if (orderStatus.getName().equalsIgnoreCase("all")) {
            return iOrderRepository.findAllByUser(user);
        }
        return iOrderRepository.findByUserAndOrderStatus(user, orderStatus);
    }

    @Override
    public Iterable<Order> findAllByUserAndOrderStatus_v2(UserDto userDto, OrderStatus orderStatus) {
        return null;
    }

    @Override
    public void addOrder(Order order, UserDto userDto) throws UnavailableBalanceException {
        if (order.getPaymentMethod().getName().equalsIgnoreCase("wallet")) {
            User user = userService2.convertUserDtoToUser(userDto).get();
            Wallet wallet = walletService.findByUser(user);
            double newBalance = walletService.findByUser(user).getBalance() - order.getCartPay().getTotalPrice();
            if (newBalance >= 0) {
                order.setDate(Timestamp.from(Instant.now()));
                iOrderRepository.save(order);
                wallet.setBalance(newBalance);
                userDto.getWallet().setBalance(newBalance);
                walletService.save(wallet);
            } else {
                throw new UnavailableBalanceException();
            }
        }
    }
}
