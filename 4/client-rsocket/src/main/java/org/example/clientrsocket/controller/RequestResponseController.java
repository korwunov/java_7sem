package org.example.clientrsocket.controller;

import org.example.clientrsocket.model.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/goods")
public class RequestResponseController {

    private final RSocketRequester rSocketRequester;

    @Autowired
    public RequestResponseController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping("/{id}")
    public Mono<Good> getGood(@PathVariable Long id) {
        return rSocketRequester
                .route("getGood")
                .data(id)
                .retrieveMono(Good.class);
    }

    @PostMapping
    public Mono<Good> addGood(@RequestBody Good good) {
        return rSocketRequester
                .route("addGood")
                .data(good)
                .retrieveMono(Good.class);
    }
}
