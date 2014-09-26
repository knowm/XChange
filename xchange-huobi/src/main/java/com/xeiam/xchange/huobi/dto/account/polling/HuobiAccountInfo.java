/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.huobi.dto.account.polling;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.huobi.dto.HuobiResponce;

/**
 * Data object representing Account Info from Mt Gox
 */
public final class HuobiAccountInfo extends HuobiResponce {

//    total 	Total
//    net_asset 	Net
//    available_cny_display 	Avail CNY
//    available_btc_display 	Avail BTC
//    frozen_cny_display 	Frozen CNY
//    frozen_btc_display 	Frozen BTC
//    loan_cny_display 	Amount
//    loan_btc_display 	Amount


    private BigDecimal total;//
    private BigDecimal net_asset;
    private BigDecimal available_cny_display;
    private BigDecimal available_btc_display;
    private BigDecimal frozen_cny_display;
    private BigDecimal frozen_btc_display;
    private BigDecimal loan_cny_display;
    private BigDecimal loan_btc_display;

    public HuobiAccountInfo(@JsonProperty("code") Integer code,
                            @JsonProperty("msg") String msg,
                            @JsonProperty("total") BigDecimal total,
                            @JsonProperty("net_asset") BigDecimal net_asset,
                            @JsonProperty("available_cny_display") BigDecimal available_cny_display,
                            @JsonProperty("available_btc_display") BigDecimal available_btc_display,
                            @JsonProperty("frozen_cny_display") BigDecimal frozen_cny_display,
                            @JsonProperty("frozen_btc_display") BigDecimal frozen_btc_display,
                            @JsonProperty("loan_cny_display") BigDecimal loan_cny_display,
                            @JsonProperty("loan_btc_display") BigDecimal loan_btc_display) {
        super(code, msg);

        this.total = total;
        this.net_asset = net_asset;
        this.available_cny_display = available_cny_display;
        this.available_btc_display = available_btc_display;
        this.frozen_cny_display = frozen_cny_display;
        this.frozen_btc_display = frozen_btc_display;
        this.loan_cny_display = loan_cny_display;
        this.loan_btc_display = loan_btc_display;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getNet_asset() {
        return net_asset;
    }

    public BigDecimal getAvailable_cny_display() {
        return available_cny_display;
    }

    public BigDecimal getAvailable_btc_display() {
        return available_btc_display;
    }

    public BigDecimal getFrozen_cny_display() {
        return frozen_cny_display;
    }

    public BigDecimal getFrozen_btc_display() {
        return frozen_btc_display;
    }

    public BigDecimal getLoan_cny_display() {
        return loan_cny_display;
    }

    public BigDecimal getLoan_btc_display() {
        return loan_btc_display;
    }
}
