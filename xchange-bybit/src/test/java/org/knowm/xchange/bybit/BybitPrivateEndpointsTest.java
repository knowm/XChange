package org.knowm.xchange.bybit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

public class BybitPrivateEndpointsTest {

  Exchange exchange;

  Instrument instrument = new CurrencyPair("BTC/USDT");

  @Before
  public void setUp(){
    Properties properties = new Properties();

    try {
      properties.load(this.getClass().getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ExchangeSpecification spec = new BybitExchange().getDefaultExchangeSpecification();

    spec.setApiKey(properties.getProperty("apikey"));
    spec.setSecretKey(properties.getProperty("secret"));
    spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);

    exchange = ExchangeFactory.INSTANCE.createExchange(BybitExchange.class);
    exchange.applySpecification(spec);

  }

  @Test
  public void testTradeHistory() throws IOException {

    TradeHistoryParamsAll params = new TradeHistoryParamsAll();

    params.setInstrument(instrument);

    exchange
        .getTradeService()
        .getTradeHistory(params)
        .getUserTrades()
        .forEach(
            userTrade -> {
              assertThat(userTrade.getId()).isNotNull();
              assertThat(userTrade.getOrderId()).isNotNull();
              assertThat(userTrade.getOrderUserReference()).isNotNull();
              assertThat(userTrade.getOriginalAmount()).isGreaterThan(BigDecimal.ZERO);
              assertThat(userTrade.getPrice()).isGreaterThan(BigDecimal.ZERO);
              assertThat(userTrade.getFeeAmount()).isGreaterThan(BigDecimal.ZERO);
              assertThat(userTrade.getType()).isNotNull();
              assertThat(userTrade.getFeeCurrency()).isNotNull();
              assertThat(userTrade.getTimestamp()).isNotNull();
            });
  }

  @Test
  public void testAccountInfo() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();

    assertThat(accountInfo.getWallets().size()).isGreaterThan(1);
    accountInfo
        .getWallets()
        .forEach(
            (s, wallet) -> {
              assertThat(BybitAccountType.valueOf(s)).isInstanceOf(BybitAccountType.class);
              assertThat(BybitAccountType.valueOf(wallet.getId())).isInstanceOf(BybitAccountType.class);
              assertThat(wallet.getFeatures()).isNotNull();

              wallet.getBalances().forEach((currency, balance) -> {
                assertThat(currency).isNotNull();
                assertThat(balance.getAvailable()).isNotNull();
                assertThat(balance.getTotal()).isNotNull();
                assertThat(balance.getFrozen()).isNotNull();
                assertThat(balance.getBorrowed()).isNotNull();
            });
        });
    }
}
