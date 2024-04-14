package com.april.controller;

import com.april.model.Cards;
import com.april.model.Customer;
import com.april.repository.CardsRepository;
import com.april.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {


    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findByEmail(email);
        if(!CollectionUtils.isEmpty(customers)){
            List<Cards> cards = cardsRepository.findByCustomerId(customers.get(0).getId());
            if (cards != null) {
                return cards;
            } else {
                return null;
            }
        }
        return null;

    }
}
