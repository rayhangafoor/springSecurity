package com.april.controller;

import com.april.model.AccountTransactions;
import com.april.model.Customer;
import com.april.repository.AccountTransactionsRepository;
import com.april.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {
    @Autowired
    private AccountTransactionsRepository accountTransactionsRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (!CollectionUtils.isEmpty(customers)) {
            List<AccountTransactions> accountTransactions =
                    accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(
                    customers.get(0).getId());
            if (accountTransactions != null) {
                return accountTransactions;
            } else {
                return null;
            }

        }
        return null;
    }
}

