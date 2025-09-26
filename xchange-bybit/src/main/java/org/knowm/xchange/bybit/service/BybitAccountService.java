package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitBalances;
import static org.knowm.xchange.bybit.BybitAdapters.convertToBybitSymbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitAccountInfoResponse;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRate;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRates;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

  private final Logger LOG = LoggerFactory.getLogger(BybitAccountService.class);
  private final BybitAccountType accountType;

  public BybitAccountService(
      BybitExchange exchange,
      BybitAccountType accountType,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
    this.accountType = accountType;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> adaptedWallets = getAdaptedWallets();
    return new AccountInfo(adaptedWallets);
  }

  @Override
  public boolean setLeverage(Instrument instrument, int leverage) throws IOException {
    BybitCategory category = BybitAdapters.getCategory(instrument);
    int retCode = setLeverageRaw(category, convertToBybitSymbol(instrument), leverage).getRetCode();
    return retCode == 0 || retCode == 110043;
  }

  /**
   * @param mode 0: Merged Single. 3: Both Sides
   * @throws IOException
   */
  public boolean switchPositionMode(
      BybitCategory category, Instrument instrument, String coin, int mode) throws IOException {
    String symbol = "";
    if (instrument != null) {
      symbol = BybitAdapters.convertToBybitSymbol(instrument);
    }
    int retCode = switchPositionModeRaw(category, symbol, coin, mode).getRetCode();
    return retCode == 0 || retCode == 110025;
  }

  private List<Wallet> getAdaptedWallets() throws IOException {
    switch (accountType) {
      case CONTRACT:
      case UNIFIED:
      case SPOT:
        return getAdaptedBalanceWallets();
      case INVESTMENT:
      case OPTION:
      case FUND:
        return getAdaptedAllCoinsWallets();
      default:
        throw new IllegalStateException("Unexpected value: " + accountType);
    }
  }

  private List<Wallet> getAdaptedAllCoinsWallets() throws IOException {
    BybitResult<BybitAllCoinsBalance> allCoinsBalanceResult = getAllCoinsBalance(accountType);
    BybitAllCoinsBalance allCoinsBalance = allCoinsBalanceResult.getResult();
    List<Wallet> wallets = new ArrayList<>();
    wallets.add(adaptBybitBalances(allCoinsBalance));
    return wallets;
  }

  private List<Wallet> getAdaptedBalanceWallets() throws IOException {
    BybitResult<BybitWalletBalance> walletBalances = getWalletBalances(accountType);
    BybitWalletBalance walletBalancesResult = walletBalances.getResult();
    List<BybitAccountBalance> accounts = walletBalancesResult.getList();
    return accounts.stream()
        .map(bybitAccountBalance -> adaptBybitBalances(bybitAccountBalance.getCoin()))
        .collect(Collectors.toList());
  }

  /**
   * @param category Optional, instrument category ("SPOT" or "LINEAR"). If not specified, return
   *     all instruments trading fees.
   */
  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument(String... category)
      throws IOException {
    Map<Instrument, Fee> result = new HashMap<>();
    if (category != null && category.length > 0 && category[0] != null) {
      String bybitCategory = category[0];
      if (bybitCategory.equals(BybitCategory.OPTION.getValue())
          || bybitCategory.equals(BybitCategory.INVERSE.getValue()))
        throw new IllegalArgumentException("category OPTION and INVERSE not yet implemented");
      result.putAll(getFeeRates(BybitCategory.valueOf(bybitCategory.toUpperCase())));
    } else {
      // not fully supported yet
      //      result.putAll(getFeeRates(BybitCategory.OPTION));
      //      result.putAll(getFeeRates(BybitCategory.INVERSE));
      result.putAll(getFeeRates(BybitCategory.SPOT));
      result.putAll(getFeeRates(BybitCategory.LINEAR));
    }
    return result;
  }

  private Map<Instrument, Fee> getFeeRates(BybitCategory bybitCategory) throws IOException {
    Map<Instrument, Fee> result = new HashMap<>();
    BybitResult<BybitFeeRates> bybitFeeRates = getFeeRatesRaw(bybitCategory, null);
    // seems bug in bybit, req fees for LINEAR category req also returns INVERSE pairs - remove it
    if (bybitCategory == BybitCategory.LINEAR) {
      clearUp(bybitFeeRates.getResult().getList().listIterator());
    }
    bybitFeeRates
        .getResult()
        .getList()
        .forEach(
            bybitFeeRate -> {
              Instrument instrument =
                  BybitAdapters.convertBybitSymbolToInstrument(
                      bybitFeeRate.getSymbol(), bybitCategory);
              result.put(
                  instrument,
                  new Fee(bybitFeeRate.getMakerFeeRate(), bybitFeeRate.getTakerFeeRate()));
            });
    return result;
  }

  private void clearUp(ListIterator<BybitFeeRate> listIterator) {
    while (listIterator.hasNext()) {
      BybitFeeRate feeRate = listIterator.next();
      Pattern p = Pattern.compile("\\d");
      if (feeRate.getSymbol().endsWith("USD")) {
        listIterator.remove();
      } else {
        if (feeRate.getSymbol().contains("USDH")
            || feeRate.getSymbol().contains("USDM")
            || feeRate.getSymbol().contains("USDU")
            || feeRate.getSymbol().contains("USDZ")) {
          Matcher m = p.matcher(feeRate.getSymbol());
          if (m.find()) {
            listIterator.remove();
          }
        }
      }
    }
  }

  /** Query the account information, like margin mode, account mode, etc. */
  public BybitAccountInfoResponse accountInfo() throws IOException {
    return accountInfoRaw().getResult();
  }
}
