package com.spring.rsocketClient.controllers;

import com.spring.rsocketClient.dto.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/api/goods")
public class GoodsRequestResponseController {
    private final RSocketRequester requester;

    @Autowired
    public GoodsRequestResponseController(RSocketRequester requester) {
        this.requester = requester;
    }

    @GetMapping("/{id}")
    public Mono<Good> getGoodById(@PathVariable Long id) {
        return requester.route("getGood").data(id).retrieveMono(Good.class);
    }

    @PostMapping
    public Mono<Good> addGood(@RequestBody Good g) {
        return requester.route("addGood").data(g).retrieveMono(Good.class);
    }
}
