package org.knowm.xchange.blockchain.dto;

import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.blockchain.BlockchainUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

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
  public BitcoinAddress(@JsonProperty("address") String address, @JsonProperty("final_balance") long final_balance,
      @JsonProperty("hash160") String hash160, @JsonProperty("n_tx") int n_tx, @JsonProperty("total_received") long total_received,
      @JsonProperty("total_sent") long total_sent, @JsonProperty("txs") List<Txs> txs) {

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

    return "BitcoinAddress [address=" + address + ", finalBalance=" + finalBalance + ", hash160=" + hash160 + ", numTransactions=" + numTransactions
        + ", totalReceived=" + totalReceived + ", totalSent=" + totalSent + "]";
  }

}
