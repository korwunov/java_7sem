package com.spring.rsocketClient.controllers;

import com.spring.rsocketClient.dto.*;
import com.spring.rsocketClient.dto.GoodListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class GoodsChannelController {
    private final RSocketRequester requester;

    @Autowired
    public GoodsChannelController(RSocketRequester requester) {
        this.requester = requester;
    }

    @PostMapping("/exp")
    public Flux<Good> addGoodsMultiple(@RequestBody GoodListWrapper goodListWrapper) {
        List<Good> goodsList = goodListWrapper.getGoods();
        Flux<Good> goods = Flux.fromIterable(goodsList);
        return requester.route("goodsChannel").data(goods).retrieveFlux(Good.class);
    }
}
