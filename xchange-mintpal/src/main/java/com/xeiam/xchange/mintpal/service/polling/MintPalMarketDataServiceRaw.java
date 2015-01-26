package com.xeiam.xchange.mintpal.service.polling;

import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.mintpal.MintPal;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;

/**
 * @author jamespedwards42
 */
public class MintPalMarketDataServiceRaw extends MintPalBasePollingService<MintPal> {

  /**
   * Constructor
   *
   * @param exchange
   */
  public MintPalMarketDataServiceRaw(Exchange exchange) {

    super(MintPal.class, exchange);
  }

  public List<MintPalTicker> getAllMintPalTickers() {

    return handleRespone(mintPal.getAllTickers());
  }

  public MintPalTicker getMintPalTicker(CurrencyPair currencyPair) {

    return handleRespone(mintPal.getTicker(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }

  public List<MintPalPublicOrders> getMintPalFullOrders(CurrencyPair currencyPair) {

    return handleRespone(mintPal.getFullOrders(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }

  public List<MintPalPublicOrders> getMintPalOrders(CurrencyPair currencyPair, int limit) {

    return handleRespone(mintPal.getOrders(currencyPair.baseSymbol, currencyPair.counterSymbol, limit));
  }

  public List<MintPalPublicOrders> getMintPalSellOrders(CurrencyPair currencyPair, int limit) {

    return handleRespone(mintPal.getSellOrders(currencyPair.baseSymbol, currencyPair.counterSymbol, limit));
  }

  public List<MintPalPublicOrders> getMintPalBuyOrders(CurrencyPair currencyPair, int limit) {

    return handleRespone(mintPal.getBuyOrders(currencyPair.baseSymbol, currencyPair.counterSymbol, limit));
  }

  public List<MintPalPublicTrade> getMintPalTrades(CurrencyPair currencyPair) {

    return handleRespone(mintPal.getTrades(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }
}
