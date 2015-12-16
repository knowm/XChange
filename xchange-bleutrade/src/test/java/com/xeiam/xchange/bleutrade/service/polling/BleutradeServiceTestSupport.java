package com.xeiam.xchange.bleutrade.service.polling;

import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalance;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddress;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeLevel;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBook;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTrade;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrder;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOrderId;

import java.math.BigDecimal;
import java.util.List;

public class BleutradeServiceTestSupport {

  protected BleutradeBalance createBalance(BigDecimal available, String currency, BigDecimal balance, BigDecimal pending, Boolean isActive) {
    BleutradeBalance bleutradeBalance = new BleutradeBalance();
    bleutradeBalance.setAvailable(available);
    bleutradeBalance.setCurrency(currency);
    bleutradeBalance.setBalance(balance);
    bleutradeBalance.setPending(pending);
    bleutradeBalance.setIsActive(isActive);

    return bleutradeBalance;
  }

  protected BleutradeDepositAddress createDepositAddress(String currency, String address) {
    BleutradeDepositAddress depositAddress = new BleutradeDepositAddress();
    depositAddress.setCurrency(currency);
    depositAddress.setAddress(address);

    return depositAddress;
  }

  protected BleutradeTicker createBleutradeTicker(String marketName, BigDecimal prevDay, BigDecimal high, BigDecimal low, BigDecimal last,
      BigDecimal average, BigDecimal volume, BigDecimal baseVolume, String timestamp, BigDecimal bid, BigDecimal ask, Boolean isActive) {

    BleutradeTicker ticker = new BleutradeTicker();
    ticker.setMarketName(marketName);
    ticker.setPrevDay(prevDay);
    ticker.setHigh(high);
    ticker.setLow(low);
    ticker.setLast(last);
    ticker.setAverage(average);
    ticker.setVolume(volume);
    ticker.setBaseVolume(baseVolume);
    ticker.setTimeStamp(timestamp);
    ticker.setBid(bid);
    ticker.setAsk(ask);
    ticker.setIsActive(isActive);

    return ticker;
  }

  protected BleutradeOrderBook createBleutradeOrderBook(List<BleutradeLevel> buy, List<BleutradeLevel>  sell) {
    BleutradeOrderBook bleutradeOrderBook = new BleutradeOrderBook();
    bleutradeOrderBook.setBuy(buy);
    bleutradeOrderBook.setSell(sell);

    return bleutradeOrderBook;
  }

  protected BleutradeLevel createBleutradeLevel(BigDecimal quantity, BigDecimal rate) {
    BleutradeLevel level = new BleutradeLevel();
    level.setQuantity(quantity);
    level.setRate(rate);

    return level;
  }

  protected BleutradeTrade createBleutradeTrade(String timestamp, BigDecimal quantity, BigDecimal price, BigDecimal total, String orderType) {
    BleutradeTrade bleutradeTrade = new BleutradeTrade();
    bleutradeTrade.setTimeStamp(timestamp);
    bleutradeTrade.setQuantity(quantity);
    bleutradeTrade.setPrice(price);
    bleutradeTrade.setTotal(total);
    bleutradeTrade.setOrderType(orderType);

    return bleutradeTrade;
  }

  protected BleutradeCurrency createBleutradeCurrency(String currency, String currencyLong, Integer minConfirmation,
      BigDecimal txFee, Boolean isActive, String coinType) {

    BleutradeCurrency bleutradeCurrency = new BleutradeCurrency();
    bleutradeCurrency.setCurrency(currency);
    bleutradeCurrency.setCurrencyLong(currencyLong);
    bleutradeCurrency.setMinConfirmation(minConfirmation);
    bleutradeCurrency.setTxFee(txFee);
    bleutradeCurrency.setIsActive(isActive);
    bleutradeCurrency.setCoinType(coinType);

    return bleutradeCurrency;
  }

  protected BleutradeMarket createBleutradeMarket(String marketCurrency, String baseCurrency, String marketCurrencyLong,
      String baseCurrencyLong, BigDecimal minTradeSize, String marketName, Boolean isActive) {
    BleutradeMarket bleutradeMarket = new BleutradeMarket();
    bleutradeMarket.setMarketCurrency(marketCurrency);
    bleutradeMarket.setBaseCurrency(baseCurrency);
    bleutradeMarket.setMarketCurrencyLong(marketCurrencyLong);
    bleutradeMarket.setBaseCurrencyLong(baseCurrencyLong);
    bleutradeMarket.setMinTradeSize(minTradeSize);
    bleutradeMarket.setMarketName(marketName);
    bleutradeMarket.setIsActive(isActive);

    return bleutradeMarket;
  }

  protected BleutradeOpenOrder createBleutradeOpenOrder(String orderId, String exchange, String type,
      BigDecimal quantity, BigDecimal quantityRemaining, String quantityBaseTraded, BigDecimal price,
      String status, String created, String comments) {
    BleutradeOpenOrder bleutradeOpenOrder = new BleutradeOpenOrder();
    bleutradeOpenOrder.setOrderId(orderId);
    bleutradeOpenOrder.setExchange(exchange);
    bleutradeOpenOrder.setType(type);
    bleutradeOpenOrder.setQuantity(quantity);
    bleutradeOpenOrder.setQuantityRemaining(quantityRemaining);
    bleutradeOpenOrder.setQuantityBaseTraded(quantityBaseTraded);
    bleutradeOpenOrder.setPrice(price);
    bleutradeOpenOrder.setStatus(status);
    bleutradeOpenOrder.setCreated(created);
    bleutradeOpenOrder.setComments(comments);

    return bleutradeOpenOrder;
  }

  protected BleutradeOrderId createBleutradeOrderId(String orderId) {
    BleutradeOrderId bleutradeOrderId = new BleutradeOrderId();
    bleutradeOrderId.setOrderid(orderId);

    return bleutradeOrderId;
  }
}
