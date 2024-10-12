package com.spring.rsocket.controllers;

import com.spring.rsocket.entities.Good;
import com.spring.rsocket.repositories.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Controller
public class GoodsController {
    private final GoodsRepository repository;

    @Autowired
    public GoodsController(GoodsRepository repository) {
        this.repository = repository;
    }

    @MessageMapping("getGood")
    public Mono<Good> getGood(Long id) {
        return Mono.justOrEmpty(repository.findById(id));
    }

    @MessageMapping("addGood")
    public Mono<Good> addGood(Good g) {
        return Mono.justOrEmpty(repository.save(g));
    }

    @MessageMapping("getGoods")
    public Flux<Good> getGoods() {
        return Flux.fromIterable(repository.findAll());
    }

    @MessageMapping("deleteGood")
    public Mono<Void> deleteGood(Long id) {
        repository.deleteById(id);
        return Mono.empty();
    }

    @MessageMapping("goodsChannel")
    public Flux<Good> goodChannel(Flux<Good> goods){
        return goods.flatMap(good -> Mono.fromCallable(() ->
                        repository.save(good)))
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }

}
