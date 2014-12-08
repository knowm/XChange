package com.xeiam.xchange.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.MarketMetadata;

import java.io.IOException;

public interface MarketMetadataService {
  MarketMetadata getMarketMetadata(CurrencyPair pair) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;
}
