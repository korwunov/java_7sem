package org.example.clientrsocket.controller;

import org.example.clientrsocket.dto.GoodListWrapper;
import org.example.clientrsocket.model.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class ChannelController {

    private final RSocketRequester rSocketRequester;

    @Autowired
    public ChannelController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @PostMapping("/channel")
    public Flux<Good> addGoodsMultiple(@RequestBody GoodListWrapper goodListWrapper){
        List<Good> goodsList = goodListWrapper.getGoods();
        Flux<Good> goods = Flux.fromIterable(goodsList);
        return rSocketRequester
                .route("goodsChannel")
                .data(goods)
                .retrieveFlux(Good.class);

    }
}
