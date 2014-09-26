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
package com.xeiam.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * <p>
 * Data object representing a Trade from Mt Gox
 * </p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 */
public final class HuobiFullTrade {
/*
        "date": 1404718044,
        "price": 3910.03,
        "amount": 0.1,
        "tid": 14391929,
        "type": "sell"
*/

    private final Long date;
    private final BigDecimal price;
    private final BigDecimal amount;
    private final Long tid;
    private final String type;

    public HuobiFullTrade(@JsonProperty("date") Long date,
                          @JsonProperty("price") BigDecimal price,
                          @JsonProperty("amount") BigDecimal amount,
                          @JsonProperty("tid") Long tid,
                          @JsonProperty("type") String type) {
        this.date = date;
        this.price = price;
        this.amount = amount;
        this.tid = tid;
        this.type = type;
    }

    public Long getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getTid() {
        return tid;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "HuobiFullTrade{" +
                "date=" + date +
                ", price=" + price +
                ", amount=" + amount +
                ", tid=" + tid +
                ", type=" + type +
                '}';
    }

}
