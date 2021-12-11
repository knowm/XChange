package org.knowm.xchange.raydium;

import io.github.makarid.solanaj.programs.raydium.RaydiumProgram;
import lombok.extern.java.Log;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.raydium.service.RaydiumAccountService;
import org.knowm.xchange.raydium.service.RaydiumMarketDataService;


@Log
public class RaydiumExchange extends BaseExchange implements Exchange {

  private final RaydiumProgram raydiumProgram = new RaydiumProgram();

  @Override
  protected void initServices() {
    this.marketDataService = new RaydiumMarketDataService(this);
    this.accountService = new RaydiumAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://sdk.raydium.io");
    exchangeSpecification.setHost("raydium.io");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Raydium");
    exchangeSpecification.setExchangeDescription(
        "Raydium is a decentralized cryptocurrency exchange built on Solana.");

    return exchangeSpecification;
  }

  public RaydiumProgram getRaydiumProgram() {
    return raydiumProgram;
  }
}
