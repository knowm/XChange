package com.okcoin.commons.okex.open.api.bean.spot.result;

import java.util.List;

public class MarginAccountDto {
    private String product_id;
    private List<MarginAccountDetailDto> detailDtos;

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(final String product_id) {
        this.product_id = product_id;
    }

    public List<MarginAccountDetailDto> getDetailDtos() {
        return this.detailDtos;
    }

    public void setDetailDtos(final List<MarginAccountDetailDto> detailDtos) {
        this.detailDtos = detailDtos;
    }
}
