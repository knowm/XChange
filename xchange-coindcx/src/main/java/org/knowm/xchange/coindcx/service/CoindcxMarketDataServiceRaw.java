package org.knowm.xchange.coindcx.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindcx.dto.CoindcxOrderBook;
import org.knowm.xchange.coindcx.dto.CoindcxTickersResponse;
import org.knowm.xchange.coindcx.dto.CoindcxTrade;
import org.knowm.xchange.coindcx.dto.CoindcxTrades;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

@Slf4j
public class CoindcxMarketDataServiceRaw extends CoindcxBaseService {

  protected CoindcxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoindcxOrderBook getCoindcxOrderBook(CurrencyPair currencyPair, Object... args)
      throws IOException {
    return coindcx.getOrderBook(getSymbol(currencyPair, args));
  }

  public List<CoindcxTrade> getCoindcxTrades(CurrencyPair currencyPair, Object... args)
      throws IOException {
    return coindcx.getTrade(getSymbol(currencyPair, args));
  }

  public List<CoindcxTickersResponse> getCoindcxTicker() {
    return coindcx.getTicker();
  }

  /**
   * @param currencyPair
   * @param args
   * @return this is used to specify the exchange for the given market. Valid values for ecode
   *     include: B: Binance I: CoinDCX HB: HitBTC H: Huobi BM: BitMEX
   */
  private String getSymbol(CurrencyPair currencyPair, Object... args) {

    if (args[0] == null) {
      throw new ExchangeException("agrs[0] must be any of this B, I, HB, H, BM");
    } else {
      if (("B").equals(args[0].toString())) {
        return "B-"
            + currencyPair.base.getCurrencyCode().toUpperCase()
            + "_"
            + currencyPair.counter.getCurrencyCode();
      } else if (("I").equals(args[0].toString())) {
        return "I-"
            + currencyPair.base.getCurrencyCode().toUpperCase()
            + "_"
            + currencyPair.counter.getCurrencyCode();
      } else if (("HB").equals(args[0].toString())) {
        return "HB-"
            + currencyPair.base.getCurrencyCode().toUpperCase()
            + "_"
            + currencyPair.counter.getCurrencyCode();
      } else if (("H").equals(args[0].toString())) {
        return "H-"
            + currencyPair.base.getCurrencyCode().toUpperCase()
            + "_"
            + currencyPair.counter.getCurrencyCode();
      } else if (("BM").equals(args[0].toString())) {
        return "BM-"
            + currencyPair.base.getCurrencyCode().toUpperCase()
            + "_"
            + currencyPair.counter.getCurrencyCode();
      } else {
        throw new ExchangeException("agrs[0] must be any of this B, I, HB, H, BM");
      }
    }
  }

  public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
    String str =
        "[{p:10838.22,q:0.060051,s:BTCUSDT,T:1600361972204,m:true},{p:10838.23,q:0.004991,s:BTCUSDT,T:1600361971927,m:false}]";
    //		String json = new ObjectMapper().writeValueAsString(str);
    CoindcxTrades coindcxTrades = new ObjectMapper().readValue(str, CoindcxTrades.class);
    System.out.println(coindcxTrades);
  }
}
