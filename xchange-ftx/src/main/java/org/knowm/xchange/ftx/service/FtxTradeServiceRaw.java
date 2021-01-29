package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ftx.FtxException;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.trade.CancelAllFtxOrdersParams;
import org.knowm.xchange.ftx.dto.trade.FtxOrderDto;
import org.knowm.xchange.ftx.dto.trade.FtxOrderRequestPayload;
import org.knowm.xchange.ftx.dto.trade.FtxPositionDto;

public class FtxTradeServiceRaw extends FtxBaseService {

  public FtxTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public FtxResponse<FtxOrderDto> placeNewFtxOrder(
      String subAccount, FtxOrderRequestPayload payload) throws FtxException, IOException {
    try {
      return ftx.placeOrder(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subAccount,
          payload);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public boolean cancelFtxOrder(String orderId) throws FtxException, IOException {
    try {
      return ftx.cancelOrder(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              null,
              orderId)
          .isSuccess();
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public boolean cancelAllFtxOrders(CancelAllFtxOrdersParams payLoad)
      throws FtxException, IOException {
    try {
      return ftx.cancelAllOrders(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              null,
              payLoad)
          .isSuccess();
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxOrderDto>> getFtxOpenOrders(String market)
      throws FtxException, IOException {
    try {
      return ftx.openOrders(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null,
          market);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxOrderDto>> getFtxOrderHistory(String market)
      throws FtxException, IOException {
    try {
      return ftx.orderHistory(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null,
          market);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxOrderDto>> getFtxAllOpenOrders() throws FtxException, IOException {
    try {
      return ftx.openOrdersWithoutMarket(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxOrderDto> getFtxOrderStatus(String orderId)
      throws FtxException, IOException {
    try {
      return ftx.getOrderStatus(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null,
          orderId);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxPositionDto>> getFtxPositions() throws FtxException, IOException {
    try {
      return ftx.getFtxPositions(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }
}
