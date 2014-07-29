package com.xeiam.xchange.mintpal.service.polling;

import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.mintpal.MintPal;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;

/**
 * @author jamespedwards42
 */
public class MintPalMarketDataServiceRaw extends MintPalBasePollingService<MintPal> {

  public MintPalMarketDataServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(MintPal.class, exchangeSpecification);
  }
  
  public List<MintPalTicker> getAllMintPalTickers() {
    
    return handleRespone(mintPal.getAllTickers());
  }
  
  public MintPalTicker getMintPalTicker(final CurrencyPair currencyPair) {
    
    return handleRespone(mintPal.getTicker(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }
  
  public List<MintPalPublicOrders> getMintPalOrders(final CurrencyPair currencyPair) {
    
    return handleRespone(mintPal.getOrders(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }
  
  public List<MintPalPublicTrade> getMintPalTrades(final CurrencyPair currencyPair) {
    
    return handleRespone(mintPal.getTrades(currencyPair.baseSymbol, currencyPair.counterSymbol));
  }
}
