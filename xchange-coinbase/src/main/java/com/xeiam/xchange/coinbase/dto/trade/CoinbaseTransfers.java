package com.xeiam.xchange.coinbase.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinbase.dto.CoinbasePagedResult;

/**
 * @author jamespedwards42
 */
public class CoinbaseTransfers extends CoinbasePagedResult {

  private final List<CoinbaseTransfer> transfers;

  private CoinbaseTransfers(@JsonProperty("transfers") final List<CoinbaseTransfer> transfers, @JsonProperty("total_count") final int totalCount, @JsonProperty("num_pages") final int numPages,
      @JsonProperty("current_page") final int currentPage) {

    super(totalCount, numPages, currentPage);
    this.transfers = transfers;
  }

  public List<CoinbaseTransfer> getTransfers() {

    return transfers;
  }

  @Override
  public String toString() {

    return "CoinbaseTransfers [transfers=" + transfers + "]";
  }
}
