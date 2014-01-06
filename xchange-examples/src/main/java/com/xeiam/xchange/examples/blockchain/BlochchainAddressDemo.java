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
package com.xeiam.xchange.examples.blockchain;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.blockchain.Blockchain;
import com.xeiam.xchange.blockchain.BlockchainExchange;
import com.xeiam.xchange.blockchain.dto.BitcoinAddress;
import com.xeiam.xchange.blockchain.dto.BitcoinAddresses;

/**
 * @author timmolter
 */
public class BlochchainAddressDemo {

  public static void main(String[] args) throws IOException {

    Exchange blockchainExchangexchange = ExchangeFactory.INSTANCE.createExchange(BlockchainExchange.class.getName());
    Blockchain blockchain = RestProxyFactory.createProxy(Blockchain.class, blockchainExchangexchange.getExchangeSpecification().getPlainTextUri());

    BitcoinAddress bitcoinAddress = blockchain.getBitcoinAddress("17dQktcAmU4urXz7tGk2sbuiCqykm3WLs6");
    System.out.println(bitcoinAddress.toString());

    BitcoinAddresses bitcoinAddresses = blockchain.getBitcoinAddresses("17dQktcAmU4urXz7tGk2sbuiCqykm3WLs6|15MvtM8e3bzepmZ5vTe8cHvrEZg6eDzw2w");
    for (BitcoinAddress bitcoinAddress2 : bitcoinAddresses.getBitcoinAddresses()) {
      System.out.println(bitcoinAddress2.toString());
    }
  }

}
