package com.spring.rsocketClient.controllers;

import com.spring.rsocketClient.dto.Good;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/goods")
public class GoodsRequestStreamController {
    private final RSocketRequester requester;

    @Autowired
    public GoodsRequestStreamController(RSocketRequester requester) {
        this.requester = requester;
    }

    @GetMapping
    public Publisher<Good> getGoods() {
        return requester.route("getGoods").retrieveFlux(Good.class);
    }
}
