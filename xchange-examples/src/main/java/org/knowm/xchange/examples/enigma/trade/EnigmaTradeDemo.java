package org.knowm.xchange.examples.enigma.trade;

import java.io.IOException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.trade.*;
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
    tradeService.cancelOrder("10");
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
