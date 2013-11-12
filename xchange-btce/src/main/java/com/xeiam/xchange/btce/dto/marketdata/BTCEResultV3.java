/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.btce.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox
 * Since: 11/12/13 11:00 PM
 *
 * Data object representing multi-currency market data from BTCE API v.3
 */
public class BTCEResultV3 <V> {

    private final Map<String, V> resultV3;

    /**
     * Constructor
     *
     * @param resultV3
     */
    public BTCEResultV3(@JsonProperty Map<String, V> resultV3) {

        this.resultV3 = resultV3;
    }

    public Map<String, V> getResultV3() {

        return resultV3;
    }

    public V getResultV2(String pair) {

        V result = null;
        if (resultV3.containsKey(pair)) {
            result = resultV3.get(pair);
        }
        return result;
    }

    @Override
    public String toString() {

        return "BTCEResultV3 [resultV3=" + resultV3.toString() + "]";
    }

}
