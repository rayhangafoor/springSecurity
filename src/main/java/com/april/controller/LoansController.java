package com.april.controller;

import com.april.model.Customer;
import com.april.model.Loans;
import com.april.repository.CustomerRepository;
import com.april.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.ListComparator;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class LoansController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if (!CollectionUtils.isEmpty(customers)) {
            List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customers.get(0).getId());
            if (loans != null) {
                return loans;
            } else {
                return null;
            }
        }
        return null;

    }
}
