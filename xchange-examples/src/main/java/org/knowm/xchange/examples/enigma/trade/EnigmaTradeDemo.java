package org.knowm.xchange.examples.enigma.trade;

import java.io.IOException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecuteQuoteRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecutedQuote;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrderRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaOrderSubmission;
import org.knowm.xchange.enigma.dto.trade.EnigmaQuote;
import org.knowm.xchange.enigma.dto.trade.EnigmaQuoteRequest;
import org.knowm.xchange.enigma.model.Infrastructure;
import org.knowm.xchange.enigma.model.Side;
import org.knowm.xchange.enigma.service.EnigmaTradeServiceRaw;
import org.knowm.xchange.examples.enigma.EnigmaDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

@Slf4j
public class EnigmaTradeDemo {

  public static void main(String[] args) throws IOException {
    Exchange enigma = EnigmaDemoUtils.createExchange();
    TradeService tradeService = enigma.getTradeService();

    generic(tradeService);
    raw((EnigmaTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {
    boolean cancelOrder = tradeService.cancelOrder("10");
    log.info(String.valueOf(cancelOrder));

    OpenOrders openOrders = tradeService.getOpenOrders();
    log.info(openOrders.toString());

    String placeMarketOrder =
        tradeService.placeMarketOrder(
            new MarketOrder(Order.OrderType.ASK, BigDecimal.valueOf(0.002), CurrencyPair.BTC_USD));
    log.info(placeMarketOrder);

    String placeLimitOrder =
        tradeService.placeLimitOrder(
            new LimitOrder(
                Order.OrderType.ASK,
                BigDecimal.valueOf(0.002),
                CurrencyPair.BTC_USD,
                null,
                null,
                BigDecimal.ONE));
    log.info(placeLimitOrder);
  }

  private static void raw(EnigmaTradeServiceRaw tradeService) throws IOException {

    EnigmaNewOrderRequest enigmaNewOrderRequest =
        new EnigmaNewOrderRequest(
            2,
            Side.SELL.getValue(),
            new BigDecimal("0.002"),
            null,
            Infrastructure.DEVELOPMENT.getValue());
    EnigmaOrderSubmission orderSubmission = tradeService.submitOrder(enigmaNewOrderRequest);
    log.info("Order submission: {}", orderSubmission.toString());

    EnigmaQuoteRequest enigmaQuoteRequest =
        new EnigmaQuoteRequest(
            2,
            Side.SELL.getValue(),
            new BigDecimal("0.002"),
            Infrastructure.DEVELOPMENT.getValue());
    EnigmaQuote enigmaQuote = tradeService.askForQuote(enigmaQuoteRequest);
    log.info("Ask for Request for quote: {}", enigmaQuote.toString());

    EnigmaExecuteQuoteRequest enigmaExecuteQuoteRequest =
        new EnigmaExecuteQuoteRequest(
            enigmaQuote.getProductId(),
            Side.SELL.getValue(),
            enigmaQuote.getQuantity(),
            enigmaQuote.getPrice(),
            enigmaQuote.getRfqClientId(),
            Infrastructure.DEVELOPMENT.getValue());
    EnigmaExecutedQuote enigmaExecutedQuote =
        tradeService.executeQuoteRequest(enigmaExecuteQuoteRequest);
    log.info("Execute for Request for quote: {}", enigmaExecutedQuote.toString());
  }
}
