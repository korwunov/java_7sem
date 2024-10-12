package org.example.clientrsocket.dto;

import org.example.clientrsocket.model.Good;

import java.util.List;

public class GoodListWrapper {
    private List<Good> goods;

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }
}
