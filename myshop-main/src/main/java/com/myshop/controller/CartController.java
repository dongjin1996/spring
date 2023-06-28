package com.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
    @GetMapping(value = "/cart")
    public String orderHist(){
        return "cart/cartList";
    }
}
