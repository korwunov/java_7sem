package org.example.clientrsocket.controller;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/goods")
public class FireForgetController {

    private final RSocketRequester rSocketRequester;


    @Autowired
    public FireForgetController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @DeleteMapping("/{id}")
    public Publisher<Void> deleteGood(@PathVariable Long id) {
        return rSocketRequester
                .route("deleteGood")
                .data(id)
                .send();

    }
}
