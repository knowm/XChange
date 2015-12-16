package com.xeiam.xchange.bleutrade.dto;

import com.xeiam.xchange.bleutrade.BleutradeException;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;
import org.junit.Ignore;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class BleutradeDtoTest extends BleutradeDtoTestSupport {

  @Test
  public void shouldParseBalanceReturn() throws Exception {
    // when
    final BleutradeBalanceReturn response = parse(BleutradeBalanceReturn.class);

    // then
    assertThat(response.toString()).isEqualTo("BleutradeBalanceReturn [success=true, message=, "
        + "result=BleutradeBalance [Currency=BTC, Balance=15.49843675, Available=13.98901996, Pending=0E-8, CryptoAddress=1NKh9X1z4Q4AbBnEgNiKU177GMyeQUexC9, IsActive=true, additionalProperties={}], "
        + "additionalProperties={}]");
  }

  @Test
  public void shouldParseBalancesReturn() throws Exception {
    // when
    final BleutradeBalancesReturn response = parse(BleutradeBalancesReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(
        "BleutradeBalancesReturn [success=true, message=, result=["
        + "BleutradeBalance [Currency=DOGE, Balance=0E-8, Available=0E-8, Pending=0E-8, CryptoAddress=DBSwFELQiVrwxFtyHpVHbgVrNJXwb3hoXL, IsActive=true, additionalProperties={}], "
        + "BleutradeBalance [Currency=BTC, Balance=15.49843675, Available=13.98901996, Pending=0E-8, CryptoAddress=1NKh9X1z4Q4AbBnEgNiKU177GMyeQUexC9, IsActive=true, additionalProperties={}]], "
        + "additionalProperties={}]");
  }

  // current response format is differs from /market/cancel output (see https://bleutrade.com/help/API)
  @Test
  public void shouldParseCancelOrderReturn() throws Exception {
    // when
    final BleutradeCancelOrderReturn response = parse(BleutradeCancelOrderReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(
        "BleutradeCancelOrderReturn [success=true, message=, result=[12345], additionalProperties={}]");
  }

  @Test
  public void shouldParseCurrenciesReturn() throws Exception {
    // when
    final BleutradeCurrenciesReturn response = parse(BleutradeCurrenciesReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(
        "BleutradeCurrenciesReturn [success=true, message=, result=["
        + "BleutradeCurrency [Currency=BTC, CurrencyLong=Bitcoin, MinConfirmation=2, TxFee=0.00080000, IsActive=true, CoinType=BITCOIN, additionalProperties={MaintenanceMode=false}], "
        + "BleutradeCurrency [Currency=LTC, CurrencyLong=Litecoin, MinConfirmation=4, TxFee=0.02000000, IsActive=true, CoinType=BITCOIN, additionalProperties={MaintenanceMode=false}]"
        + "], additionalProperties={}]");
  }

  @Test
  public void shouldParseDepositAddressReturn() throws Exception {
    // when
    final BleutradeDepositAddressReturn response = parse(BleutradeDepositAddressReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(
        "BleutradeDepositAddressReturn [success=true, message=, result="
        + "BleutradeDepositAddress [Currency=BTC, Address=1NKh9X1z4Q4AbBnEgNiKU177GMyeQUexC9, additionalProperties={}], additionalProperties={}]");
  }

  @Test
  public void shouldParseMarketHistoryReturn() throws Exception {
    // when
    final BleutradeMarketHistoryReturn response = parse(BleutradeMarketHistoryReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(
        "BleutradeMarketHistoryReturn [success=true, message=, result=["
        + "BleutradeTrade [TimeStamp=2014-07-29 18:08:00, Quantity=654971.69417461, Price=5.5E-7, Total=0.360234432, OrderType=BUY, additionalProperties={}], "
        + "BleutradeTrade [TimeStamp=2014-07-29 18:12:35, Quantity=120.00000000, Price=0.00006600, Total=0.360234432, OrderType=SELL, additionalProperties={}]], "
        + "additionalProperties={}]");
  }

  @Test
  public void shouldParseMarketsReturn() throws Exception {
    // when
    final BleutradeMarketsReturn response = parse(BleutradeMarketsReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(
        "BleutradeMarketsReturn [success=true, message=, result=["
        + "BleutradeMarket [MarketCurrency=DOGE, BaseCurrency=BTC, MarketCurrencyLong=Dogecoin, BaseCurrencyLong=Bitcoin, MinTradeSize=0.10000000, MarketName=DOGE_BTC, IsActive=true, additionalProperties={}], "
        + "BleutradeMarket [MarketCurrency=BLEU, BaseCurrency=BTC, MarketCurrencyLong=Bleutrade Share, BaseCurrencyLong=Bitcoin, MinTradeSize=1E-8, MarketName=BLEU_BTC, IsActive=true, additionalProperties={}]], "
        + "additionalProperties={}]");
  }

  @Test
  public void shouldParseOpenOrdersReturn() throws Exception {
    // when
    final BleutradeOpenOrdersReturn response = parse(BleutradeOpenOrdersReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(
        "BleutradeOpenOrdersReturn [success=true, message=, result=["
        + "BleutradeOpenOrder [OrderId=65489, Exchange=LTC_BTC, Type=BUY, Quantity=20.00000000, QuantityRemaining=5.00000000, QuantityBaseTraded=0.16549400, Price=0.01268311, Status=OPEN, Created=2014-08-03 13:55:20, Comments=My optional comment, eg function id #123, additionalProperties={}], "
        + "BleutradeOpenOrder [OrderId=65724, Exchange=DOGE_BTC, Type=SELL, Quantity=150491.98700000, QuantityRemaining=795.00000000, QuantityBaseTraded=0.04349400, Price=5.5E-7, Status=OPEN, Created=2014-07-29 18:45:17, Comments=Function #123 Connect #456, additionalProperties={}]], "
        + "additionalProperties={}]");
  }

  @Test
  public void shouldParseOrderBookReturn() throws Exception {
    // when
    final BleutradeOrderBookReturn response = parse(BleutradeOrderBookReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(
        "BleutradeOrderBookReturn [success=true, message=, result=BleutradeOrderBookResult "
        + "[buy=[BleutradeLevel [Quantity=4.99400000, Rate=3.00650900, additionalProperties={}], BleutradeLevel [Quantity=50.00000000, Rate=3.50000000, additionalProperties={}]], "
        + "sell=[BleutradeLevel [Quantity=12.44147454, Rate=5.13540000, additionalProperties={}], BleutradeLevel [Quantity=100.00000000, Rate=6.25500000, additionalProperties={}], "
        + "BleutradeLevel [Quantity=30.00000000, Rate=6.75500001, additionalProperties={}], BleutradeLevel [Quantity=13.49989999, Rate=6.76260099, additionalProperties={}]], "
        + "additionalProperties={}], additionalProperties={}]");
  }

  @Test
  public void shouldParsePlaceOrderReturn() throws Exception {
    // when
    final BleutradePlaceOrderReturn response = parse(BleutradePlaceOrderReturn.class);

    // then
    assertThat(response.toString()).isEqualTo("BleutradePlaceOrderReturn [success=true, message=, result=BleutradeOrderId [orderid=65498, additionalProperties={}], additionalProperties={}]");
  }

  @Test
  public void shouldParseTickerReturn() throws Exception {
    // when
    final BleutradeTickerReturn response = parse(BleutradeTickerReturn.class);

    // then
    assertThat(response.toString()).isEqualTo("BleutradeTickerReturn [success=true, message=, result=["
        + "BleutradeTicker [MarketName=BLEU_BTC, PrevDay=0.00095000, High=0.00105000, Low=0.00086000, Last=0.00101977, Average=0.00103455, Volume=2450.97496015, BaseVolume=2.40781647, "
          + "TimeStamp=2014-07-29 11:19:30, Bid=0.00100000, Ask=0.00101977, IsActive=true, additionalProperties={}], "
        + "BleutradeTicker [MarketName=LTC_BTC, PrevDay=0.00095000, High=0.01333000, Low=0.01167001, Last=0.01333000, Average=0.01235000, Volume=14.46077245, BaseVolume=0.18765956, "
          + "TimeStamp=2014-07-29 11:48:02, Bid=0.01268311, Ask=0.01333000, IsActive=true, additionalProperties={}]], "
        + "additionalProperties={}]");
  }

  @Test
  public void shouldParseCancelOrderReturnAsException() throws Exception {
    // when
    final BleutradeException response = parse("CancelOrderReturn", BleutradeException.class);

    // then
    assertThat(response.getSuccess()).isEqualTo("true");
    assertThat(response.getMessage()).isEmpty();
    assertThat(response.getResult()).hasSize(1);
    assertThat(response.getResult()).contains("12345");
    assertThat(response.getAdditionalProperties()).isEmpty();
  }
}
