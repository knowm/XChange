package com.okcoin.commons.okex.open.api.bean.swap.result;

import java.util.List;

public class ApiAccountsVO {
    private List<ApiAccountVO> info;

    public List<ApiAccountVO> getInfo() {
        return info;
    }

    public void setInfo(List<ApiAccountVO> info) {
        this.info = info;
    }

    public ApiAccountsVO() {
    }

    public ApiAccountsVO(List<ApiAccountVO> info) {
        this.info = info;
    }
}
