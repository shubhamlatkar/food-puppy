package com.foodgrid.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/")
    public ResponseEntity<String> getDefault() {
        return new ResponseEntity<>("Order Service ", HttpStatus.OK);
    }
}
