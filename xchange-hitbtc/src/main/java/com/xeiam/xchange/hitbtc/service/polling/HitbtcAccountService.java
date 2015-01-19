package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTradeServiceHelper;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.service.polling.PollingAccountService;

import static com.xeiam.xchange.utils.TradeServiceHelperConfigurer.CFG;

public class HitbtcAccountService extends HitbtcAccountServiceRaw implements PollingAccountService {

  private BigDecimal tradingFee;

  public HitbtcAccountService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    HitbtcBalance[] accountInfoRaw = getAccountInfoRaw();

    return HitbtcAdapters.adaptAccountInfo(accountInfoRaw, tradingFee);
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  public void setTradingFeeFromTradeHelpers(Map<CurrencyPair, HitbtcTradeServiceHelper> metadata){
    boolean makerFee = CFG.getBoolProperty(HITBTC_ORDER_FEE_POLICY_MAKER);
    Properties config = CFG.getProperties();

    String currencyPair = config.getProperty(HITBTC_ORDER_FEE_LISTING_DEFAULT);
    if (currencyPair == null)
      currencyPair = config.getProperty(XCHANGE_ORDER_FEE_LISTING_DEFAULT, CurrencyPair.BTC_USD.toString());

    CurrencyPair pair = CurrencyPair.fromString(currencyPair);

    HitbtcTradeServiceHelper listingHelper = metadata.get(pair);
    tradingFee= makerFee ? listingHelper.getProvideLiquidityRate() : listingHelper.getTakeLiquidityRate();
  }
}
