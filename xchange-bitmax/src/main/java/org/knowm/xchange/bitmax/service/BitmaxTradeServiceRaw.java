package org.knowm.xchange.bitmax.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmax.BitmaxException;

import org.knowm.xchange.bitmax.dto.trade.BitmaxCancelOrderRequestPayload;
import org.knowm.xchange.bitmax.dto.trade.BitmaxOpenOrdersResponse;
import org.knowm.xchange.bitmax.dto.trade.BitmaxPlaceOrderRequestPayload;
import org.knowm.xchange.bitmax.dto.trade.BitmaxOrderResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BitmaxTradeServiceRaw extends BitmaxBaseService{

    public BitmaxTradeServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public BitmaxOrderResponse placeBitmaxOrder(BitmaxPlaceOrderRequestPayload payload) throws BitmaxException,IOException {
        try{
            return checkResult(bitmaxWithAccountGroup.placeOrder(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getNonceFactory().createValue(),
                    signatureCreator,
                    "cash",
                    payload
            ));
        }catch (IOException e){
            throw new BitmaxException(e.getMessage());
        }
    }

    public BitmaxOrderResponse cancelBitmaxOrder(BitmaxCancelOrderRequestPayload payload) throws BitmaxException,IOException {
        try{
      return checkResult(
          bitmaxWithAccountGroup.cancelOrder(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              "cash",
              payload.getOrderId(),
              payload.getSymbol(),
              payload.getTime()));
        }catch (IOException e){
            throw new BitmaxException(e.getMessage());
        }
    }

    public BitmaxOrderResponse cancelAllBitmaxOrdersBySymbol(String symbol) throws BitmaxException,IOException {
        try{
            return checkResult(bitmaxWithAccountGroup.cancelAllOrders(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getNonceFactory().createValue(),
                    signatureCreator,
                    "cash",
                    symbol
            ));
        }catch (IOException e){
            throw new BitmaxException(e.getMessage());
        }
    }

    public List<BitmaxOpenOrdersResponse> getBitmaxOpenOrders(String symbol) throws BitmaxException,IOException {
        try{
            return checkResult(bitmaxWithAccountGroup.getOpenOrders(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getNonceFactory().createValue(),
                    signatureCreator,
                    "cash",
                    symbol
            ));
        }catch (IOException e){
            throw new BitmaxException(e.getMessage());
        }
    }

    public BitmaxOpenOrdersResponse getBitmaxOrderById(String orderId) throws BitmaxException,IOException {
        try{
            return checkResult(bitmaxWithAccountGroup.getOrderById(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getNonceFactory().createValue(),
                    signatureCreator,
                    "cash",
                    orderId
            ));
        }catch (IOException e){
            throw new BitmaxException(e.getMessage());
        }
    }

    public List<BitmaxOpenOrdersResponse> getBitmaxUserTrades(String symbol) throws BitmaxException,IOException {
        try{
            return checkResult(bitmaxWithAccountGroup.getOrdersHistory(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getNonceFactory().createValue(),
                    signatureCreator,
                    "cash",
                    50,
                    symbol,
                    true
            ));
        }catch (IOException e){
            throw new BitmaxException(e.getMessage());
        }
    }
}
