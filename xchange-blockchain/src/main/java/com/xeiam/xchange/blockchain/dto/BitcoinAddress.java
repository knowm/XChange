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
package com.xeiam.xchange.blockchain.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.blockchain.BlockchainUtils;

/**
 * NOTE: Divide by 10^8 to get the decimal value of BTC amounts
 * 
 * @author timmolter
 */
public final class BitcoinAddress {

  private final String address;
  private final long finalBalance;
  private final String hash160;
  private final int numTransactions;
  private final long totalReceived;
  private final long totalSent;
  private final List<Txs> txs;

  /**
   * Constructor
   * 
   * @param address
   * @param final_balance
   * @param hash160
   * @param n_tx
   * @param total_received
   * @param total_sent
   * @param txs
   */
  public BitcoinAddress(@JsonProperty("address") String address, @JsonProperty("final_balance") long final_balance, @JsonProperty("hash160") String hash160, @JsonProperty("n_tx") int n_tx,
      @JsonProperty("total_received") long total_received, @JsonProperty("total_sent") long total_sent, @JsonProperty("txs") List<Txs> txs) {

    this.address = address;
    this.finalBalance = final_balance;
    this.hash160 = hash160;
    this.numTransactions = n_tx;
    this.totalReceived = total_received;
    this.totalSent = total_sent;
    this.txs = txs;
  }

  public String getAddress() {

    return this.address;
  }

  public long getFinalBalance() {

    return this.finalBalance;
  }

  public BigDecimal getFinalBalanceDecimal() {

    return BlockchainUtils.getAmount(this.finalBalance);
  }

  public String getHash160() {

    return this.hash160;
  }

  public int getNumTransactions() {

    return this.numTransactions;
  }

  public long getTotalReceived() {

    return this.totalReceived;
  }

  public BigDecimal getTotalReceivedDecimal() {

    return BlockchainUtils.getAmount(this.totalReceived);
  }

  public long getTotalSent() {

    return this.totalSent;
  }

  public BigDecimal getTotalSentDecimal() {

    return BlockchainUtils.getAmount(this.totalSent);
  }

  public List<Txs> getTxs() {

    return this.txs;
  }

  @Override
  public String toString() {

    return "BitcoinAddress [address=" + address + ", finalBalance=" + finalBalance + ", hash160=" + hash160 + ", numTransactions=" + numTransactions + ", totalReceived=" + totalReceived
        + ", totalSent=" + totalSent + "]";
  }

}
