package com.xeiam.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 25/06/14
 * Time: 18:17
 */
public class HuobiDetail {

/*    {
        "sells": [{
                "price": "3591.8",
                "level": 0,
                "amount": 0.3142
            }],
        "buys": [{
                "price": "3591.1",
                "level": 0,
                "amount": 4.3502
            }],
        "trades": [{
                "time": "21:54:32",
                "price": 3591.1,
                "amount": 0.4,
                "type": "卖出"
            }],
        "p_new": 3591.1,
        "level": -122.41,
        "amount": 40833,
        "total": 148316786.78807,
        "amp": -3,
        "p_open": 3713.51,
        "p_high": 3720,
        "p_low": 3566.94,
        "p_last": 3713.51,
        "top_sell": [{
                "price": "3591.8",
                "level": 0,
                "amount": 0.3142,
                "accu": 0.3142
            }]
        "top_buy": [{
                "price": "3591.1",
                "level": 0,
                "amount": 4.3502,
                "accu": 4.3502
            }]
    }
*/

    private List<HuobiTrade> trades;


    public HuobiDetail(@JsonProperty("trades") List<HuobiTrade> trades) {
        this.trades = trades;
    }

    public List<HuobiTrade> getTrades() {
        return trades;
    }
}
