/*
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
package com.xeiam.xchange.campbx.service.marketdata.polling;

import org.junit.Test;

import com.xeiam.xchange.campbx.CampBX;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Matija Mazi <br/>
 */
public class CampBXPollingTradeServiceTest {

  @Test
  public void testOrderIdParsing() throws Exception {

    testOrderId(CampBX.OrderType.Buy, "3", "Buy-3");
    testOrderId(CampBX.OrderType.Buy, "asdf", "Buy-asdf");
    testOrderId(CampBX.OrderType.Sell, "3-42-fa-asdf", "Sell-3-42-fa-asdf");
    testOrderId(CampBX.OrderType.Sell, "3:42-fa#asdf", "Sell-3:42-fa#asdf");

  }

  private void testOrderId(CampBX.OrderType type, String id, String compositeId) {

    assertThat(CampBXPollingTradeService.composeOrderId(type, id), is(equalTo(compositeId)));
    CampBXPollingTradeService.ParsedId parsedId = CampBXPollingTradeService.parseOrderId(compositeId);
    assertThat(parsedId.id, is(equalTo(id)));
    assertThat(parsedId.type, is(equalTo(type)));
  }
}
