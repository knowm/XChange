package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.CoinbaseAuthenticated;
import com.xeiam.xchange.coinbase.CoinbaseUtils;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;

public class CoinbaseTradeServiceRaw extends CoinbaseBaseService<CoinbaseAuthenticated> {

  protected CoinbaseTradeServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(CoinbaseAuthenticated.class, exchangeSpecification);
  }

  public CoinbaseTransfer buyCoinbaseBTC(final BigDecimal quantity) throws IOException {

    return buyCoinbaseBTC(quantity, false);
  }

  public CoinbaseTransfer buyCoinbaseBTC(final BigDecimal quantity, final boolean agreeBTCAmountVaries) throws IOException {

    final CoinbaseTransfer buyTransfer = coinbase.buy(quantity.toPlainString(), agreeBTCAmountVaries, exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return handleResponse(buyTransfer);
  }

  public CoinbaseTransfer sellCoinbaseBTC(final BigDecimal quantity) throws IOException {

    final CoinbaseTransfer sellTransfer = coinbase.sell(quantity.toPlainString(), exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return handleResponse(sellTransfer);
  }
  
  public CoinbaseTransfers getCoinbaseTransfers() throws IOException {

    return getCoinbaseTransfers(null, null);
  }

  public CoinbaseTransfers getCoinbaseTransfers(final Integer page, final Integer limit) throws IOException {

    final CoinbaseTransfers transfers = coinbase.getTransfers(page, limit, exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return transfers;
  }
}
