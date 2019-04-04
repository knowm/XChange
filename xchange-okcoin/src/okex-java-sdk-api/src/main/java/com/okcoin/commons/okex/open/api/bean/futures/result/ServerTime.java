package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * Time of the server running OKEX's REST API.
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/8 20:58
 */
public class ServerTime {
    private String iso;
    private String epoch;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }
}
