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
public final class HuobiTrade {
/*
    "time": "21:54:32",
    "price": 3591.1,
    "amount": 0.4,
    "type": "卖出"
*/

    private final String time;
    private final BigDecimal price;
    private final BigDecimal amount;
    private final String type;

    public HuobiTrade(@JsonProperty("time") String time,
                      @JsonProperty("price") BigDecimal price,
                      @JsonProperty("amount") BigDecimal amount,
                      @JsonProperty("type") String type) {
        this.time = time;
        this.price = price;
        this.amount = amount;
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "HuobiTrade{" +
                "time=" + time +
                ", price=" + price +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }

}
