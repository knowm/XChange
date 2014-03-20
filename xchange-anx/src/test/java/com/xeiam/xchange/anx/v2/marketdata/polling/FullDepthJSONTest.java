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
package com.xeiam.xchange.anx.v2.marketdata.polling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepth;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * Test ANXFullDepth JSON parsing
 */
public class FullDepthJSONTest {

    @Test
    public void testUnmarshal() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = FullDepthJSONTest.class.getResourceAsStream("/v2/marketdata/example-fulldepth-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        ANXDepth anxFullDepth = mapper.readValue(is, ANXDepth.class);

        // Verify that the example data was unmarshalled correctly
//        assertThat(anxFullDepth.getAsks().get(0).getAmountInt()).isEqualTo(727610000L);

        Assert.assertEquals(new Long(1392693096241000L), anxFullDepth.getMicroTime());
        Assert.assertEquals(3, anxFullDepth.getAsks().size());
        Assert.assertEquals(new BigDecimal("3260.40000"), anxFullDepth.getAsks().get(0).getPrice());
        Assert.assertEquals(326040000L, anxFullDepth.getAsks().get(0).getPriceInt());
        Assert.assertEquals(new BigDecimal("16.00000000"), anxFullDepth.getAsks().get(0).getAmount());
        Assert.assertEquals(1600000000L, anxFullDepth.getAsks().get(0).getAmountInt());

        Assert.assertEquals(4, anxFullDepth.getBids().size());
        Assert.assertEquals(new BigDecimal("2000.00000"), anxFullDepth.getBids().get(0).getPrice());
        Assert.assertEquals(200000000L, anxFullDepth.getBids().get(0).getPriceInt());
        Assert.assertEquals(new BigDecimal("3.00000000"), anxFullDepth.getBids().get(0).getAmount());
        Assert.assertEquals(300000000L, anxFullDepth.getBids().get(0).getAmountInt());

    }
}
