package org.knowm.xchange.bitflyer.service;

import static org.knowm.xchange.bitflyer.dto.trade.BitflyerParentOrderConditionType.LIMIT;
import static org.knowm.xchange.bitflyer.dto.trade.BitflyerParentOrderConditionType.MARKET;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.BitflyerException;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerChildOrder;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerChildOrderType;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerExecution;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerParentOrder;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerPosition;
import org.knowm.xchange.bitflyer.dto.trade.results.BitflyerChildOrderAcceptance;
import org.knowm.xchange.bitflyer.dto.trade.results.BitflyerParentOrderAcceptance;
import org.knowm.xchange.bitflyer.dto.trade.results.BitflyerQueryChildOrderResult;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

public class BitflyerTradeServiceRaw extends BitflyerBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BitflyerExecution> getExecutions() throws IOException {
    try {
      return bitflyer.getExecutions();
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerExecution> getExecutions(String productCode) throws IOException {
    try {
      return bitflyer.getExecutions(productCode);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerPosition> getPositions() throws IOException {
    try {
      // Currently supports only "FX_BTC_JPY".
      return bitflyer.getPositions(
          apiKey, exchange.getNonceFactory(), signatureCreator, "FX_BTC_JPY");
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerPosition> getPositions(String productCode) throws IOException {
    try {
      return bitflyer.getPositions(
          apiKey, exchange.getNonceFactory(), signatureCreator, productCode);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public List<BitflyerQueryChildOrderResult> getChildOrders(
      String productCode, String childOrderState) throws IOException {
    try {
      return bitflyer.getChildOrders(
          apiKey, exchange.getNonceFactory(), signatureCreator, productCode, childOrderState);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerChildOrderAcceptance sendChildOrder(MarketOrder marketOrder) throws IOException {
    BitflyerChildOrder.BitflyerChildOrderBuilder orderBuilder =
        BitflyerChildOrder.getOrderBuilder()
            .withProductCode(marketOrder.getCurrencyPair())
            .withChildOrderType(BitflyerChildOrderType.MARKET)
            .withSide(marketOrder.getType())
            .withSize(marketOrder.getOriginalAmount());

    try {
      return bitflyer.sendChildOrder(
          apiKey, exchange.getNonceFactory(), signatureCreator, orderBuilder.buildOrder());
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerChildOrderAcceptance sendChildOrder(LimitOrder limitOrder) throws IOException {
    BitflyerChildOrder.BitflyerChildOrderBuilder orderBuilder =
        BitflyerChildOrder.getOrderBuilder()
            .withProductCode(limitOrder.getCurrencyPair())
            .withChildOrderType(BitflyerChildOrderType.LIMIT)
            .withSide(limitOrder.getType())
            .withPrice(limitOrder.getLimitPrice())
            .withSize(limitOrder.getOriginalAmount());

    try {
      return bitflyer.sendChildOrder(
          apiKey, exchange.getNonceFactory(), signatureCreator, orderBuilder.buildOrder());
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerParentOrderAcceptance sendParentOrder(MarketOrder marketOrder) throws IOException {
    BitflyerParentOrder.BitflyerParentOrderBuilder orderBuilder =
        BitflyerParentOrder.getOrderBuilder()
            .withParameter(
                marketOrder.getCurrencyPair(),
                MARKET,
                marketOrder.getType(),
                null,
                null,
                marketOrder.getOriginalAmount(),
                null);

    try {
      return bitflyer.sendParentOrder(
          apiKey, exchange.getNonceFactory(), signatureCreator, orderBuilder.buildOrder());
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerParentOrderAcceptance sendParentOrder(LimitOrder limitOrder) throws IOException {
    BitflyerParentOrder.BitflyerParentOrderBuilder orderBuilder =
        BitflyerParentOrder.getOrderBuilder()
            .withParameter(
                limitOrder.getCurrencyPair(),
                LIMIT,
                limitOrder.getType(),
                limitOrder.getLimitPrice(),
                null,
                limitOrder.getOriginalAmount(),
                null);

    try {
      return bitflyer.sendParentOrder(
          apiKey, exchange.getNonceFactory(), signatureCreator, orderBuilder.buildOrder());
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }
}
