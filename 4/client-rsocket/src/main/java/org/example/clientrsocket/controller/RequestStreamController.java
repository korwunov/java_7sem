package org.example.clientrsocket.controller;

import org.example.clientrsocket.model.Good;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/goods")
public class RequestStreamController {

    private final RSocketRequester rSocketRequester;

    @Autowired
    public RequestStreamController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping
    public Publisher<Good> getStream() {
        return rSocketRequester
                .route("getGoods")
                .data(new Good())
                .retrieveFlux(Good.class);
    }
}
