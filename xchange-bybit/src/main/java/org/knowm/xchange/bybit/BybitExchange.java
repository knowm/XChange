package org.knowm.xchange.bybit;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.bybit.service.BybitMarketDataService;
import org.knowm.xchange.bybit.service.BybitTradeService;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.ExchangeException;

public class BybitExchange extends BaseExchange {

  @Override
  protected void initServices() {
    marketDataService = new BybitMarketDataService(this);
    tradeService = new BybitTradeService(this);
    accountService =
        new BybitAccountService(
            this, ((BybitExchangeSpecification) getExchangeSpecification()).getAccountType());
  }
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    BybitExchangeSpecification exchangeSpecification =
        new BybitExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bybit.com");
    exchangeSpecification.setHost("bybit.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bybit");
    exchangeSpecification.setExchangeDescription("BYBIT");
    exchangeSpecification.setAccountType(BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, false);
    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    if(useSandbox(exchangeSpecification)){
      exchangeSpecification.setSslUri("https://api-testnet.bybit.com");
    }
    super.applySpecification(exchangeSpecification);
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    // initialize currency pairs & currencies
    exchangeMetaData = new ExchangeMetaData(
        marketDataService.getInstruments(),
        marketDataService.getCurrencies(),
        null,
        null,
        true);
  }

  protected boolean useSandbox(ExchangeSpecification exchangeSpecification){
      return Boolean.TRUE.equals(
          exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX));
  }
}
