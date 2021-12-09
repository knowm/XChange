package org.knowm.xchange.raydium;

import lombok.extern.java.Log;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.raydium.dto.FarmListDto;
import org.knowm.xchange.raydium.dto.LpListDto;
import org.knowm.xchange.raydium.dto.TokenListDto;
import org.knowm.xchange.raydium.service.RaydiumAccountService;
import org.knowm.xchange.raydium.service.RaydiumAccountServiceRaw;
import org.knowm.xchange.raydium.service.RaydiumMarketDataService;

import java.io.IOException;

@Log
public class RaydiumExchange extends BaseExchange implements Exchange {

  TokenListDto tokenListDto;
  LpListDto lpListDto;
  FarmListDto farmListDto;

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

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    RaydiumAccountServiceRaw raw = ((RaydiumAccountServiceRaw) this.accountService);

    tokenListDto = raw.getTokenList();
    lpListDto = raw.getLpList();
    farmListDto = raw.getFarmList();
  }

  public TokenListDto getTokenListDto() {
    return tokenListDto;
  }

  public LpListDto getLpListDto() {
    return lpListDto;
  }

  public FarmListDto getFarmListDto() {
    return farmListDto;
  }
}
