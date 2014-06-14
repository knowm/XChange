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
package com.xeiam.xchange.itbit.v1.dto.account;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitAccountInfoReturn {

  private final String id;
  private final String userId;
  private final String name;
  private final ItBitAccountBalance[] balances;

  public ItBitAccountInfoReturn(@JsonProperty("id") String id, @JsonProperty("userId") String userId, @JsonProperty("name") String name, @JsonProperty("balances") ItBitAccountBalance[] balances) {

    this.id = id;
    this.userId = userId;
    this.name = name;
    this.balances = balances;
  }

  public String getId() {

    return id;
  }

  public String getUserId() {

    return userId;
  }

  public String getName() {

    return name;
  }

  public ItBitAccountBalance[] getBalances() {

    return balances;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitAccountInfoReturn [id=");
    builder.append(id);
    builder.append(", userId=");
    builder.append(userId);
    builder.append(", name=");
    builder.append(name);
    builder.append(", balances=");
    builder.append(Arrays.toString(balances));
    builder.append("]");
    return builder.toString();
  }

}
