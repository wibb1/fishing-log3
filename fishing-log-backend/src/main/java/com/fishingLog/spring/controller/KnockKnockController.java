package com.fishingLog.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/knockKnock")
public class KnockKnockController {
    @GetMapping
    public String knockKnock() {
        return "Who's there";
    }
}
