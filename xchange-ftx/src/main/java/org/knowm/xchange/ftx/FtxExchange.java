package org.knowm.xchange.ftx;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.ftx.dto.marketdata.FtxMarketsDto;
import org.knowm.xchange.ftx.service.FtxAccountService;
import org.knowm.xchange.ftx.service.FtxBorrowingServiceRaw;
import org.knowm.xchange.ftx.service.FtxLendingServiceRaw;
import org.knowm.xchange.ftx.service.FtxMarketDataService;
import org.knowm.xchange.ftx.service.FtxMarketDataServiceRaw;
import org.knowm.xchange.ftx.service.FtxTradeService;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;

public class FtxExchange extends BaseExchange implements Exchange {

  private FtxLendingServiceRaw lendingService;
  private FtxBorrowingServiceRaw borrowingService;

  @Override
  protected void initServices() {
    this.marketDataService = new FtxMarketDataService(this);
    this.accountService = new FtxAccountService(this);
    this.tradeService = new FtxTradeService(this);
    this.lendingService = new FtxLendingServiceRaw(this);
    this.borrowingService = new FtxBorrowingServiceRaw(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());

    exchangeSpecification.setSslUri("https://ftx.com");
    exchangeSpecification.setHost("ftx.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Ftx");
    exchangeSpecification.setExchangeDescription("Ftx is a spot and derivatives exchange.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    FtxMarketsDto marketsDto =
        ((FtxMarketDataServiceRaw) marketDataService).getFtxMarkets().getResult();

    exchangeMetaData = FtxAdapters.adaptExchangeMetaData(marketsDto);
  }

  @Override
  public MarketDataService getMarketDataService() {
    return this.marketDataService;
  }

  @Override
  public AccountService getAccountService() {
    return this.accountService;
  }

  @Override
  public TradeService getTradeService() {
    return this.tradeService;
  }

  public FtxLendingServiceRaw getLendingService() {
    return lendingService;
  }

  public FtxBorrowingServiceRaw getBorrowingService() {
    return borrowingService;
  }
}
