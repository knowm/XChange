package com.okcoin.commons.okex.open.api.bean.swap.param;

import java.util.LinkedList;
import java.util.List;

public class PpCancelOrderVO {
    private List<String> ids = new LinkedList<>();

    public PpCancelOrderVO() {
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public PpCancelOrderVO(List<String> ids) {
        this.ids = ids;
    }
}
