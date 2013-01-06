/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.campbx.api.model;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi <br/>
 *         Sample json: { "Asks":[ [ 99.99, 0.10000000 ], [ 20, 5.00000000 ], [ 18, 10.00000000 ], [ 16.75, 1.38850000 ], [ 15, 41.00000000 ], [ 14, 45.00000000 ], [ 13.99, 24.06613854 ], [ 13.96,
 *         5.00000000 ], [ 13.9, 25.00000000 ], [ 13.89, 23.50000000 ], [ 13.88, 3.30000000 ], [ 13.75, 8.18000000 ], [ 13.74, 14.00000000 ], [ 13.73, 3.99950000 ], [ 13.7, 6.35000000 ], [ 13.69,
 *         23.50000000 ], [ 13.65, 8.21755000 ], [ 13.62, 41.00000000 ], [ 13.6, 110.00000000 ], [ 13.59, 26.48000000 ], [ 13.58, 22.00000000 ], [ 13.49, 23.50000000 ], [ 13.48, 0.37000000 ], [ 13.46,
 *         20.00000000 ], [ 13.45, 3.70000000 ], [ 13.3, 99.12914128 ] ], "Bids":[ [ 13.17, 38.39000000 ], [ 13.16, 10.00000000 ], [ 13.14, 9.40000000 ], [ 13.12, 10.60000000 ], [ 13.11, 4.00020916 ],
 *         [ 13.1, 20.00000000 ], [ 13.08, 2.02555668 ], [ 13.07, 57.50388357 ], [ 13.06, 24.82365366 ], [ 13.01, 21.00000000 ], [ 13, 39.02172664 ], [ 12.99, 15.31224148 ], [ 12.81, 15.00000000 ], [
 *         12.8, 10.00000000 ], [ 12.6, 10.00000000 ], [ 12.5, 5.44631818 ], [ 12.25, 0.40000000 ], [ 12.2, 12.17791000 ], [ 12.01, 20.60000000 ], [ 12, 92.85750703 ], [ 11.72, 0.99453007 ], [ 11.5,
 *         11.60000000 ], [ 11, 14.15278919 ], [ 10.4, 7.30000000 ], [ 10.3, 6.55000000 ], [ 9.01, 19.45000000 ], [ 9, 552.54433946 ], [ 7.21, 18.20000000 ], [ 7.2, 14.69141941 ], [ 5.5, 0.40000000 ],
 *         [ 4, 5.00000000 ], [ 3.57, 0.10000000 ], [ 2.5, 1.00000000 ], [ 0.9, 0.40000000 ] ] }
 */
public class OrderBook {

  @JsonProperty("Bids")
  private List<List<BigDecimal>> bids;
  @JsonProperty("Asks")
  private List<List<BigDecimal>> asks;

  /** (price, amount) */
  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  /** (price, amount) */
  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  @Override
  public String toString() {

    return String.format("OrderBook{bids=%s, asks=%s}", bids, asks);
  }
}
