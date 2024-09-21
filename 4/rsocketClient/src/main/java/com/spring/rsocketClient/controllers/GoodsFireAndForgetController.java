package com.spring.rsocketClient.controllers;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GoodsFireAndForgetController {
    private final RSocketRequester requester;

    @Autowired
    public GoodsFireAndForgetController(RSocketRequester requester) {
        this.requester = requester;
    }

    @DeleteMapping("/{id}")
    public Publisher<Void> deleteGood(@PathVariable Long id) {
        return requester.route("deleteGood").data(id).send();
    }
}
