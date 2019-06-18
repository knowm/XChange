package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.marketdata.OkexTokenPairInformation;

public class OkexMarketDataServiceRaw extends OkexBaseService {

  public OkexMarketDataServiceRaw(OkexExchangeV3 exchange) {
    super(exchange);
  }

  public List<OkexTokenPairInformation> getAllTokenPairInformations() throws IOException {
    return okex.getAllTokenPairInformations();
  }

  public OkexTokenPairInformation getTokenPairInformation(String instrumentID) throws IOException {
    OkexTokenPairInformation tokenPairInformation = okex.getTokenPairInformation(instrumentID);
    return tokenPairInformation;
  }
}
