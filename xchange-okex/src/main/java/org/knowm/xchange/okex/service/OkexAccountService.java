package org.knowm.xchange.okex.service;

import static org.knowm.xchange.okex.OkexAdapters.adaptInstrument;
import static org.knowm.xchange.okex.OkexAdapters.adaptTradeMode;
import static org.knowm.xchange.okex.OkexAdapters.adaptTradingFee;
import static org.knowm.xchange.okex.dto.OkexInstType.SPOT;
import static org.knowm.xchange.okex.dto.OkexInstType.SWAP;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.OkexException;
import org.knowm.xchange.okex.dto.OkexInstType;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.account.OkexAccountPositionRisk;
import org.knowm.xchange.okex.dto.account.OkexAssetBalance;
import org.knowm.xchange.okex.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.dto.account.OkexWithdrawalResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAccountService extends OkexAccountServiceRaw implements AccountService {

  public OkexAccountService(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public AccountInfo getAccountInfo() throws IOException {
    // null to get assets (with non-zero balance), remaining balance, and available amount in the
    // account.
    OkexResponse<List<OkexWalletBalance>> tradingBalances = getWalletBalances(null);
    OkexResponse<List<OkexAssetBalance>> assetBalances = getAssetBalances(null);
    OkexResponse<List<OkexAccountPositionRisk>> positionRis = getAccountPositionRisk();
    return new AccountInfo(
        OkexAdapters.adaptOkexBalances(tradingBalances.getData()),
        OkexAdapters.adaptOkexAssetBalances(assetBalances.getData()),
        OkexAdapters.adaptOkexAccountPositionRisk(positionRis.getData()));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      String address =
          defaultParams.getAddressTag() != null
              ? defaultParams.getAddress() + ":" + defaultParams.getAddressTag()
              : defaultParams.getAddress();
      OkexResponse<List<OkexWithdrawalResponse>> okexResponse =
          assetWithdrawal(
              defaultParams.getCurrency().getCurrencyCode(),
              defaultParams.getAmount().toPlainString(),
              ON_CHAIN_METHOD,
              address,
              defaultParams.getCommission() != null
                  ? defaultParams.getCommission().toPlainString()
                  : null,
              null,
              null);
      if (!okexResponse.isSuccess()) {
        throw new OkexException(okexResponse.getMsg(), Integer.parseInt(okexResponse.getCode()));
      }

      return okexResponse.getData().get(0).getWithdrawalId();
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  /**
   * @param category Optional, instrument category ("SPOT" or "SWAP"). If not specified, return all
   *     instruments trading fees.
   */
  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument(String... category)
      throws IOException {
    Map<Instrument, Fee> result = new HashMap<>();
    if (category != null && category.length > 0 && category[0] != null) {
      if (OkexInstType.SPOT.name().equals(category[0])) {
        return getTradeFeesSPOT();
      } else if (OkexInstType.SWAP.name().equals(category[0])) {
        return getTradeFeesSWAP();
      }
    } else {
      result.putAll(getTradeFeesSPOT());
      result.putAll(getTradeFeesSWAP());
    }
    return result;
  }

  @Override
  public boolean setLeverage(Instrument instrument, int leverage) throws IOException {
    return setLeverage(
            adaptInstrument(instrument),
            "",
            String.valueOf(leverage),
            adaptTradeMode(instrument, exchange.accountLevel),
            "")
        .isSuccess();
  }

  private Map<Instrument, Fee> getTradeFeesSPOT() throws IOException {
    Map<Instrument, Fee> result = new HashMap<>();
    OkexTradeFee okexTradeFee = getTradeFee(SPOT.name(), null, null, null).getData().get(0);
    for (Instrument instrument : exchange.getExchangeMetaData().getInstruments().keySet()) {
      if (instrument instanceof CurrencyPair) {
        result.put(instrument, adaptTradingFee(okexTradeFee, SPOT, instrument));
      }
    }
    return result;
  }

  private Map<Instrument, Fee> getTradeFeesSWAP() throws IOException {
    Map<Instrument, Fee> result = new HashMap<>();
    OkexTradeFee okexTradeFee = getTradeFee(SWAP.name(), null, null, null).getData().get(0);
    for (Instrument instrument : exchange.getExchangeMetaData().getInstruments().keySet()) {
      if (instrument instanceof FuturesContract && ((FuturesContract) instrument).isPerpetual()) {
        result.put(instrument, adaptTradingFee(okexTradeFee, SWAP, instrument));
      }
    }
    return result;
  }
}
