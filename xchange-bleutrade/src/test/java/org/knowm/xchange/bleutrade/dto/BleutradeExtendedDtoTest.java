package org.knowm.xchange.bleutrade.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.bleutrade.BleutradeException;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;

public class BleutradeExtendedDtoTest extends BleutradeDtoTestSupport {

  protected static final String BALANCE_RETURN_STR =
      "BleutradeBalanceReturn [success=true, message=, "
          + "result=BleutradeBalance [Currency=BTC, Balance=15.49843675, Available=13.98901996, "
          + "Pending=0E-8, CryptoAddress=1NKh9X1z4Q4AbBnEgNiKU177GMyeQUexC9, IsActive=true, "
          + "additionalProperties={foo=1}], "
          + "additionalProperties={ext=extended value}]";

  protected static final String BALANCES_RETURN_STR =
      "BleutradeBalancesReturn [success=true, message=, result=["
          + "BleutradeBalance [Currency=DOGE, Balance=0E-8, Available=0E-8, Pending=0E-8, "
          + "CryptoAddress=DBSwFELQiVrwxFtyHpVHbgVrNJXwb3hoXL, IsActive=true, additionalProperties={foo=1}], "
          + "BleutradeBalance [Currency=BTC, Balance=15.49843675, Available=13.98901996, Pending=0E-8, "
          + "CryptoAddress=1NKh9X1z4Q4AbBnEgNiKU177GMyeQUexC9, IsActive=true, additionalProperties={bar=true}]], "
          + "additionalProperties={ext=extended value}]";

  protected static final String CANCEL_ORDER_RETURN_STR =
      "BleutradeCancelOrderReturn [success=true, message=, result=[12345], additionalProperties={ext=extended value}]";

  protected static final String CURRENCIES_RETURN_STR =
      "BleutradeCurrenciesReturn [success=true, message=, result=["
          + "BleutradeCurrency [Currency=BTC, CurrencyLong=Bitcoin, MinConfirmation=2, TxFee=0.00080000, "
          + "IsActive=true, CoinType=BITCOIN, additionalProperties={MaintenanceMode=false}], "
          + "BleutradeCurrency [Currency=LTC, CurrencyLong=Litecoin, MinConfirmation=4, TxFee=0.02000000, "
          + "IsActive=true, CoinType=BITCOIN, additionalProperties={MaintenanceMode=false}]"
          + "], additionalProperties={ext=extended value}]";

  protected static final String DEPOSIT_ADDRESS_RETURN_STR =
      "BleutradeDepositAddressReturn [success=true, message=, result="
          + "BleutradeDepositAddress [Currency=BTC, Address=1NKh9X1z4Q4AbBnEgNiKU177GMyeQUexC9, additionalProperties={foo=1}], "
          + "additionalProperties={ext=extended value}]";

  protected static final String MARKET_HISTORY_RETURN_STR =
      "BleutradeMarketHistoryReturn [success=true, message=, result=["
          + "BleutradeTrade [TimeStamp=2014-07-29 18:08:00, Quantity=654971.69417461, Price=5.5E-7, "
          + "Total=0.360234432, OrderType=BUY, additionalProperties={foo=1}], "
          + "BleutradeTrade [TimeStamp=2014-07-29 18:12:35, Quantity=120.00000000, Price=0.00006600, "
          + "Total=0.360234432, OrderType=SELL, additionalProperties={bar=true}]], "
          + "additionalProperties={ext=extended value}]";

  protected static final String MARKETS_RETURN_STR =
      "BleutradeMarketsReturn [success=true, message=, result=["
          + "BleutradeMarket [MarketCurrency=DOGE, BaseCurrency=BTC, MarketCurrencyLong=Dogecoin, BaseCurrencyLong=Bitcoin, "
          + "MinTradeSize=0.10000000, MarketName=DOGE_BTC, IsActive=true, additionalProperties={foo=1}], "
          + "BleutradeMarket [MarketCurrency=BLEU, BaseCurrency=BTC, MarketCurrencyLong=Bleutrade Share, BaseCurrencyLong=Bitcoin, "
          + "MinTradeSize=1E-8, MarketName=BLEU_BTC, IsActive=true, additionalProperties={bar=true}]], "
          + "additionalProperties={ext=extended value}]";

  protected static final String OPEN_ORDERS_RETURN_STR =
      "BleutradeOpenOrdersReturn [success=true, message=, result=["
          + "BleutradeOpenOrder [OrderId=65489, Exchange=LTC_BTC, Type=BUY, Quantity=20.00000000, QuantityRemaining=5.00000000, "
          + "QuantityBaseTraded=0.16549400, Price=0.01268311, Status=OPEN, Created=2014-08-03 13:55:20, "
          + "Comments=My optional comment, eg function id #123, additionalProperties={foo=1}], "
          + "BleutradeOpenOrder [OrderId=65724, Exchange=DOGE_BTC, Type=SELL, Quantity=150491.98700000, QuantityRemaining=795.00000000, "
          + "QuantityBaseTraded=0.04349400, Price=5.5E-7, Status=OPEN, Created=2014-07-29 18:45:17, "
          + "Comments=Function #123 Connect #456, additionalProperties={bar=true}]], "
          + "additionalProperties={ext=extended value}]";

  protected static final String ORDER_BOOK_RETURN_STR =
      "BleutradeOrderBookReturn [success=true, message=, result=BleutradeOrderBookResult "
          + "[buy=["
          + "BleutradeLevel [Quantity=4.99400000, Rate=3.00650900, additionalProperties={ext=1}], "
          + "BleutradeLevel [Quantity=50.00000000, Rate=3.50000000, additionalProperties={ext=2}]"
          + "], "
          + "sell=["
          + "BleutradeLevel [Quantity=12.44147454, Rate=5.13540000, additionalProperties={ext=3}], "
          + "BleutradeLevel [Quantity=100.00000000, Rate=6.25500000, additionalProperties={ext=4}], "
          + "BleutradeLevel [Quantity=30.00000000, Rate=6.75500001, additionalProperties={ext=5}], "
          + "BleutradeLevel [Quantity=13.49989999, Rate=6.76260099, additionalProperties={ext=6}]], "
          + "additionalProperties={foo=1}], "
          + "additionalProperties={ext=extended value}]";

  protected static final String PLACE_ORDER_RETURN_STR =
      "BleutradePlaceOrderReturn [success=true, message=, "
          + "result=BleutradeOrderId [orderid=65498, additionalProperties={foo=1}], "
          + "additionalProperties={ext=extended value}]";

  protected static final String TICKER_RETURN_STR =
      "BleutradeTickerReturn [success=true, message=, result=["
          + "BleutradeTicker [MarketName=BLEU_BTC, PrevDay=0.00095000, High=0.00105000, Low=0.00086000, "
          + "Last=0.00101977, Average=0.00103455, Volume=2450.97496015, BaseVolume=2.40781647, "
          + "TimeStamp=2014-07-29 11:19:30, Bid=0.00100000, Ask=0.00101977, IsActive=true, additionalProperties={foo=1}], "
          + "BleutradeTicker [MarketName=LTC_BTC, PrevDay=0.00095000, High=0.01333000, Low=0.01167001, "
          + "Last=0.01333000, Average=0.01235000, Volume=14.46077245, BaseVolume=0.18765956, "
          + "TimeStamp=2014-07-29 11:48:02, Bid=0.01268311, Ask=0.01333000, IsActive=true, additionalProperties={bar=true}]], "
          + "additionalProperties={ext=extended value}]";

  @Test
  public void shouldParseBalanceReturn() throws IOException {
    // when
    final BleutradeBalanceReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedBalanceReturn",
            BleutradeBalanceReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(BALANCE_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedBalancesReturn() throws IOException {
    // when
    final BleutradeBalancesReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedBalancesReturn",
            BleutradeBalancesReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(BALANCES_RETURN_STR);
  }

  // current response format is differs from /market/cancel output (see
  // https://bleutrade.com/help/API)
  @Test
  public void shouldParseExtendedCancelOrderReturn() throws IOException {
    // when
    final BleutradeCancelOrderReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedCancelOrderReturn",
            BleutradeCancelOrderReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(CANCEL_ORDER_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedCurrenciesReturn() throws IOException {
    // when
    final BleutradeCurrenciesReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedCurrenciesReturn",
            BleutradeCurrenciesReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(CURRENCIES_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedDepositAddressReturn() throws IOException {
    // when
    final BleutradeDepositAddressReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedDepositAddressReturn",
            BleutradeDepositAddressReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(DEPOSIT_ADDRESS_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedMarketHistoryReturn() throws IOException {
    // when
    final BleutradeMarketHistoryReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedMarketHistoryReturn",
            BleutradeMarketHistoryReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(MARKET_HISTORY_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedMarketsReturn() throws IOException {
    // when
    final BleutradeMarketsReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedMarketsReturn",
            BleutradeMarketsReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(MARKETS_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedOpenOrdersReturn() throws IOException {
    // when
    final BleutradeOpenOrdersReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedOpenOrdersReturn",
            BleutradeOpenOrdersReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(OPEN_ORDERS_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedOrderBookReturn() throws IOException {
    // when
    final BleutradeOrderBookReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedOrderBookReturn",
            BleutradeOrderBookReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(ORDER_BOOK_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedPlaceOrderReturn() throws IOException {
    // when
    final BleutradePlaceOrderReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedPlaceOrderReturn",
            BleutradePlaceOrderReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(PLACE_ORDER_RETURN_STR);
  }

  @Test
  public void shouldParseExtendedTickerReturn() throws IOException {
    // when
    final BleutradeTickerReturn response =
        parse(
            "org/knowm/xchange/bleutrade/dto/" + "ExtendedTickerReturn",
            BleutradeTickerReturn.class);

    // then
    assertThat(response.toString()).isEqualTo(TICKER_RETURN_STR);
  }

  @Test
  @Ignore("BleutradeException is not parsed from json with additional properties")
  public void shouldParseExtendedCancelOrderReturnAsException() throws IOException {
    // when
    final BleutradeException response =
        parse("org/knowm/xchange/bleutrade/dto/" + "CancelOrderReturn", BleutradeException.class);

    // then
    assertThat(response.getSuccess()).isEqualTo("true");
    assertThat(response.getMessage()).isEmpty();
    assertThat(response.getResult()).hasSize(1);
    assertThat(response.getResult()).contains("12345");
    assertThat(response.getAdditionalProperties()).hasSize(1);
    assertThat(response.getAdditionalProperties().get("ext")).isEqualTo("extended value");
  }
}
