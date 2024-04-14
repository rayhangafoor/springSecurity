package com.april.controller;

import com.april.model.Accounts;
import com.april.model.Customer;
import com.april.repository.AccountsRepository;
import com.april.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        List<Customer> customers =customerRepository.findByEmail(email);
        if(!CollectionUtils.isEmpty(customers)) {
            return accountsRepository.findByCustomerId(customers.get(0).getId());
        }
        return null;
    }
}
