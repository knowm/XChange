package org.knowm.xchange.test.coinsuper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsuper.CoinsuperExchange;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.trade.CoinsuperOrder;
import org.knowm.xchange.coinsuper.service.CoinsuperTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;

public class TradeServiceIntegrationTransactions {
  public static void main(String[] args) {
    try {
      getOrderListRaw();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getOrderList() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    MarketDataService marketDataService = coinsuper.getMarketDataService();

    try {
      Ticker coinsuperTicker = marketDataService.getTicker(CurrencyPair.ETC_BTC, 1);
      System.out.println("======get tickers======");

      System.out.println(coinsuperTicker);
      System.out.println("getVolume=" + coinsuperTicker.getVolume());
      System.out.println("getTimestamp=" + coinsuperTicker.getTimestamp());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getOrderListRaw() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    // TradeService tradeService = coinsuper.getTradeService();
    TradeService tradeService = coinsuper.getTradeService();
    try {
      // raw((CoinsuperTradeServiceRaw) tradeService);
      // rawCreateOrder((CoinsuperTradeServiceRaw) tradeService);
      // rawCancelOrder((CoinsuperTradeServiceRaw) tradeService);
      rawCreateBuyOrder((CoinsuperTradeServiceRaw) tradeService);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void raw(CoinsuperTradeServiceRaw tradeService) throws IOException {

    // Object data = tradeService.orderOpenList();

    System.out.println("======rawCreateOrder======");
    // System.out.println(data);
    // System.out.println(coinsuperResponse.getData().getResult().get(0));

    //	    coinsuperResponse.getData().getResult().forEach(coinsuperTicker -> {
    //
    //	    	System.out.println("result.getVolume() = "+coinsuperTicker.getVolume());
    //		});

  }

  private static void rawCreateBuyOrder(CoinsuperTradeServiceRaw tradeService) throws IOException {
    Map<String, String> data = new HashMap<String, String>();
    data.put("orderType", "LMT");
    data.put("symbol", "XRP/BTC");
    data.put("priceLimit", "0.00008174");
    data.put("amount", "0");
    data.put("quantity", "30513299.8408");

    CoinsuperResponse<CoinsuperOrder> buy = tradeService.createOrder(data);
    // Object sell = tradeService.createSellOrder();

    System.out.println("======rawCreateOrder======");
    System.out.println(buy.getData().getResult());
    System.out.println(buy.getData().getResult().getOrderNo());
    // System.out.println(sell);

  }

  //	private static void rawCreateSellOrder(CoinsuperTradeServiceRaw tradeService) throws
  // IOException {
  //        Map<String,String> data = new HashMap<String, String>();
  //        data.put("orderType", "LMT");
  //        data.put("symbol", "XRP/BTC");
  //        data.put("priceLimit", "0.00008174");
  //        data.put("amount", "0");
  //        data.put("quantity", "30513299.8408");
  //
  //
  //		Object sell = tradeService.createSellOrder(data);
  //
  //		System.out.println("======sell======");
  //		System.out.println(sell);
  //		//System.out.println(sell);
  //	}

  private static void rawCancelOrder(CoinsuperTradeServiceRaw tradeService) throws IOException {
    Map<String, String> data = new HashMap<String, String>();
    data.put("orderNo", "1608392464514043905");

    // Object result = tradeService.cancelOrder(data);

    System.out.println("======sell======");
    // System.out.println(result);
    // System.out.println(sell);
  }
}
