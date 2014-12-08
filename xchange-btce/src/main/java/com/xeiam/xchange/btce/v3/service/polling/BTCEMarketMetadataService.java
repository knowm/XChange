package com.xeiam.xchange.btce.v3.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEPairInfo;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.MarketMetadata;
import com.xeiam.xchange.service.polling.MarketMetadataService;

import java.io.IOException;

import static com.xeiam.xchange.utils.ConfigurationManager.CFG_MGR;

public class BTCEMarketMetadataService extends BTCEMarketDataServiceRaw implements MarketMetadataService {

  public BTCEMarketMetadataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public MarketMetadata getMarketMetadata(CurrencyPair pair) throws ExchangeException, IOException {

    String symbol = BTCEAdapters.adaptCurrencyPair(pair);
    BTCEPairInfo pairInfo = getBTCEInfo().getPairs().get(symbol);
    if (pairInfo == null) {
      throw new IllegalArgumentException("Unknown pair " + pair);
    }

    int amountScale = CFG_MGR.getIntProperty(KEY_ORDER_SIZE_SCALE_DEFAULT);
    return BTCEAdapters.createMarketMetadata(pairInfo, amountScale);
  }

}
