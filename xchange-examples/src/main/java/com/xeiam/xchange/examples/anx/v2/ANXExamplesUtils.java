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
package com.xeiam.xchange.examples.anx.v2;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.v2.ANXExchange;

/**
 * @author timmolter
 */
public class ANXExamplesUtils {

  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(ANXExchange.class);

    // xchange.anx@gmail.com
    exSpec.setApiKey("2adfc5c6-76b4-4293-9ebd-5ca07e946b6b");
    exSpec.setSecretKey("jBYn0728AmK/TPsraGj9M/c9UHIR/h/V8a0k8QXcyRUOlZ3YOMvL2c6x7GBw8k/JXMQ+4Sf4AOADFI3n9xWxwA==");

    String protocol = "https";
    String host = "anxpro.com";
    int port = 443;
    exSpec.setSslUri(protocol + "://" + host + ":" + port);
    exSpec.setHost(host);
    exSpec.setPort(port);

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
