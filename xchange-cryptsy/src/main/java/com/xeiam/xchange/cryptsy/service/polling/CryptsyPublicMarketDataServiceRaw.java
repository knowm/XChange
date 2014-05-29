package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.Cryptsy;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;

public class CryptsyPublicMarketDataServiceRaw extends CryptsyBasePollingService<Cryptsy> {

  public CryptsyPublicMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Cryptsy.class, exchangeSpecification);
  }

  public Map<String, CryptsyPublicMarketData> getAllCryptsyMarketData() throws IOException, ExchangeException {

    return checkResult(cryptsy.getAllMarketData()).getReturnValue().get("markets");
  }

  public Map<String, CryptsyPublicMarketData> getCryptsyMarketData(int marketId) throws IOException, ExchangeException {

    return checkResult(cryptsy.getMarketData(marketId)).getReturnValue().get("markets");
  }

  public Map<String, CryptsyPublicOrderbook> getAllCryptsyOrderBooks() throws IOException, ExchangeException {

    return checkResult(cryptsy.getAllOrderbookData()).getReturnValue();
  }

  public Map<String, CryptsyPublicOrderbook> getCryptsyOrderBook(int marketId) throws IOException, ExchangeException {

    return checkResult(cryptsy.getOrderbookData(marketId)).getReturnValue();
  }
}
