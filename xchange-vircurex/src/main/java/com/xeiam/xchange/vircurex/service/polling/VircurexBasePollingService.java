package com.xeiam.xchange.vircurex.service.polling;

import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class VircurexBasePollingService extends BaseExchangeService implements BasePollingService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = new ArrayList<CurrencyPair>();

  static {

    CURRENCY_PAIRS.add(CurrencyPair.LTC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.TRC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.PPC_BTC);
    CURRENCY_PAIRS.add(new CurrencyPair("DGC", Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.NVC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.NMC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.TRC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.DVC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.IXC, Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair("FRC", Currencies.BTC));
    CURRENCY_PAIRS.add(new CurrencyPair(Currencies.FTC, Currencies.LTC));
    CURRENCY_PAIRS.add(CurrencyPair.NMC_BTC);
    CURRENCY_PAIRS.add(CurrencyPair.BTC_USD);
    CURRENCY_PAIRS.add(new CurrencyPair("ANC", Currencies.BTC));
    CURRENCY_PAIRS.add(CurrencyPair.XDC_BTC);
  }

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VircurexBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
