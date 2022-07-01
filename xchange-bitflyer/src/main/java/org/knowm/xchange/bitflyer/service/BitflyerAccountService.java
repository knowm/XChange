package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.BitflyerAdapters;
import org.knowm.xchange.bitflyer.BitflyerUtils;
import org.knowm.xchange.bitflyer.dto.BitflyerException;
import org.knowm.xchange.bitflyer.dto.account.BitflyerAddress;
import org.knowm.xchange.bitflyer.dto.account.BitflyerCoinHistory;
import org.knowm.xchange.bitflyer.dto.account.BitflyerDepositOrWithdrawal;
import org.knowm.xchange.bitflyer.dto.trade.results.BitflyerTradingCommission;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitflyerAccountService extends BitflyerAccountServiceRaw implements AccountService {
  private static final Logger LOG = LoggerFactory.getLogger(BitflyerAccountService.class);

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
    Map<CurrencyPair, Fee> tradingFees = new HashMap<>();
    List<CurrencyPair> pairs = exchange.getExchangeSymbols();

    pairs.forEach(
        pair -> {
          try {
            BitflyerTradingCommission commission =
                getTradingCommission(BitflyerUtils.bitflyerProductCode(pair));

            tradingFees.put(pair, BitflyerAdapters.adaptTradingCommission(commission));
          } catch (IOException | BitflyerException | ExchangeException e) {
            LOG.trace("Exception fetching trade commission for {}", pair, e);
          }
        });

    return tradingFees;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(BitflyerAdapters.adaptAccountInfo(getBitflyerBalances()));
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    List<BitflyerAddress> addresses = getAddresses();
    for (BitflyerAddress address : addresses) {
      if (address.getCurrencyCode().equals(currency.getCurrencyCode())) return address.getAddress();
    }

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BitflyerTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams param) throws IOException {
    BitflyerTradeHistoryParams historyParms =
        (BitflyerTradeHistoryParams)
            (param instanceof BitflyerTradeHistoryParams ? createFundingHistoryParams() : param);
    List<BitflyerCoinHistory> coinsIn = getCoinIns();
    List<BitflyerCoinHistory> coinsOut = getCoinOuts();
    List<BitflyerDepositOrWithdrawal> cashDeposits = getCashDeposits();
    List<BitflyerDepositOrWithdrawal> withdrawals = getWithdrawals();

    List<FundingRecord> retVal = new ArrayList<>();
    List<FundingRecord> some;
    some = BitflyerAdapters.adaptFundingRecordsFromCoinHistory(coinsIn, FundingRecord.Type.DEPOSIT);
    cullNotWanted(some, historyParms);
    retVal.addAll(some);

    some =
        BitflyerAdapters.adaptFundingRecordsFromCoinHistory(
            coinsOut, FundingRecord.Type.WITHDRAWAL);
    cullNotWanted(some, historyParms);
    retVal.addAll(some);

    some =
        BitflyerAdapters.adaptFundingRecordsFromDepositHistory(
            cashDeposits, FundingRecord.Type.DEPOSIT);
    cullNotWanted(some, historyParms);
    retVal.addAll(some);

    some =
        BitflyerAdapters.adaptFundingRecordsFromDepositHistory(
            withdrawals, FundingRecord.Type.WITHDRAWAL);
    cullNotWanted(some, historyParms);
    retVal.addAll(some);

    // interleave the records based on time, newest first
    Collections.sort(
        retVal,
        (FundingRecord r1, FundingRecord r2) -> {
          return r2.getDate().compareTo(r1.getDate());
        });

    return retVal;
  }

  private void cullNotWanted(List<FundingRecord> some, BitflyerTradeHistoryParams param) {
    if (param != null && param.getCurrencies() != null) {
      Iterator<FundingRecord> iter = some.iterator();
      while (iter.hasNext()) {
        FundingRecord record = iter.next();
        if (!isIn(record.getCurrency(), param.getCurrencies())) iter.remove();
      }
    }
  }

  private boolean isIn(Currency currency, Currency[] currencies) {
    for (Currency cur : currencies) if (cur.equals(currency)) return true;

    return false;
  }
}
