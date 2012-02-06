/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.mtgox.marketdata.MtGoxTicker;

public class JacksonTest {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(MtGoxPublicHttpMarketDataDemo.class);

  /**
   * @param args Not required
   */
  public static void main(String[] args) {

    // String mtgoxjson = "{\"result\":\"success\"} ";
    // String mtgoxjson = "{\"result\":\"success\",\"return\":\"high\" ";
    String mtgoxjson = "{\"result\":\"success\",\"return\":{\"high\":{\"value\":\"5.98700\",\"value_int\":\"598700\",\"display\":\"$5.98700\",\"currency\":\"USD\"},\"low\":{\"value\":\"5.70829\",\"value_int\":\"570829\",\"display\":\"$5.70829\",\"currency\":\"USD\"},\"avg\":{\"value\":\"5.86021\",\"value_int\":\"586021\",\"display\":\"$5.86021\",\"currency\":\"USD\"},\"vwap\":{\"value\":\"5.83167\",\"value_int\":\"583167\",\"display\":\"$5.83167\",\"currency\":\"USD\"},\"vol\":{\"value\":\"33094.29177057\",\"value_int\":\"3309429177057\",\"display\":\"33,094.29177057\u00a0BTC\",\"currency\":\"BTC\"},\"last_local\":{\"value\":\"5.79000\",\"value_int\":\"579000\",\"display\":\"$5.79000\",\"currency\":\"USD\"},\"last\":{\"value\":\"5.79000\",\"value_int\":\"579000\",\"display\":\"$5.79000\",\"currency\":\"USD\"},\"last_orig\":{\"value\":\"5.79000\",\"value_int\":\"579000\",\"display\":\"$5.79000\",\"currency\":\"USD\"},\"last_all\":{\"value\":\"5.79000\",\"value_int\":\"579000\",\"display\":\"$5.79000\",\"currency\":\"USD\"},\"buy\":{\"value\":\"5.77397\",\"value_int\":\"577397\",\"display\":\"$5.77397\",\"currency\":\"USD\"},\"sell\":{\"value\":\"5.78999\",\"value_int\":\"578999\",\"display\":\"$5.78999\",\"currency\":\"USD\"}}} ";
    // String mtgoxjson =
    // "{\"result\":\"success\",\"return\":{\"high\":{\"value\":\"5.98700\",\"value_int\":\"598700\",\"display\":\"$5.98700\",\"currency\":\"USD\"},\"low\":{\"value\":\"5.70829\",\"value_int\":\"570829\",\"display\":\"$5.70829\",\"currency\":\"USD\"},\"avg\":{\"value\":\"5.86021\",\"value_int\":\"586021\",\"display\":\"$5.86021\",\"currency\":\"USD\"},\"vwap\":{\"value\":\"5.83167\",\"value_int\":\"583167\",\"display\":\"$5.83167\",\"currency\":\"USD\"},\"vol\":{\"value\":\"33094.29177057\",\"value_int\":\"3309429177057\",\"display\":\"33,094.29177057\u00a0BTC\",\"currency\":\"BTC\"},\"last_local\":{\"value\":\"5.79000\",\"value_int\":\"579000\",\"display\":\"$5.79000\",\"currency\":\"USD\"},\"last\":{\"value\":\"5.79000\",\"value_int\":\"579000\",\"display\":\"$5.79000\",\"currency\":\"USD\"},\"last_orig\":{\"value\":\"5.79000\",\"value_int\":\"579000\",\"display\":\"$5.79000\",\"currency\":\"USD\"},\"last_all\":{\"value\":\"5.79000\",\"value_int\":\"579000\",\"display\":\"$5.79000\",\"currency\":\"USD\"},\"buy\":{\"value\":\"5.77397\",\"value_int\":\"577397\",\"display\":\"$5.77397\",\"currency\":\"USD\"},\"sell\":{\"value\":\"5.78999\",\"value_int\":\"578999\",\"display\":\"$5.78999\",\"currency\":\"USD\"}}} ";

    // parse JSON
    ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    try {
      MtGoxTicker mtGoxTicker = mapper.readValue(mtgoxjson, MtGoxTicker.class);
      log.debug(mtGoxTicker.getResult());
    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
