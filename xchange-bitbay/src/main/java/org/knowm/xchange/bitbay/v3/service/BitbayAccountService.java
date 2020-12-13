package org.knowm.xchange.bitbay.v3.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.bitbay.v3.BitbayExchange;
import org.knowm.xchange.bitbay.v3.dto.BitbayBalanceHistoryEntry;
import org.knowm.xchange.bitbay.v3.dto.BitbayBalanceHistoryResponse;
import org.knowm.xchange.bitbay.v3.dto.BitbayBalances;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayBalancesHistoryQuery;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.utils.DateUtils;

public class BitbayAccountService extends BitbayAccountServiceRaw implements AccountService {
  public BitbayAccountService(BitbayExchange bitbayExchange) {
    super(bitbayExchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> wallets = new ArrayList<>();

    for (BitbayBalances.BitbayBalance balance : balances()) {
      Wallet wallet =
          Wallet.Builder.from(
                  Arrays.asList(
                      new Balance(
                          Currency.getInstance(balance.getCurrency()),
                          balance.getTotalFunds(),
                          balance.getAvailableFunds(),
                          balance.getLockedFunds())))
              .id(balance.getId())
              .build();
      wallets.add(wallet);
    }

    return new AccountInfo(wallets);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    Integer limit = 200;
    Long offset = 0L;

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    BitbayBalancesHistoryQuery query = new BitbayBalancesHistoryQuery();

    List<String> types = new ArrayList<>();
    types.add("WITHDRAWAL_SUBTRACT_FUNDS");
    types.add("ADD_FUNDS");
    query.setTypes(types);
    query.setLimit(String.valueOf(limit));
    query.setOffset(String.valueOf(offset));

    final BitbayBalanceHistoryResponse bitbayBalanceHistoryResponse = balanceHistory(query);

    List<FundingRecord> fundingRecords = new ArrayList<>();

    for (BitbayBalanceHistoryEntry item : bitbayBalanceHistoryResponse.getItems()) {
      fundingRecords.add(adaptFundingRecord(item));
    }

    return fundingRecords;
  }

  private static FundingRecord adaptFundingRecord(BitbayBalanceHistoryEntry item) {
    FundingRecord.Type type =
        item.getType().equalsIgnoreCase("WITHDRAWAL_SUBTRACT_FUNDS")
            ? FundingRecord.Type.WITHDRAWAL
            : FundingRecord.Type.DEPOSIT;

    return new FundingRecord.Builder()
        .setType(type)
        .setBlockchainTransactionHash(null) // not available in the API yet
        .setAddress(null) // not available in the API yet
        .setAmount(item.getValue().abs())
        .setCurrency(Currency.getInstance(item.getBalance().getCurrency()))
        .setDate(DateUtils.fromMillisUtc(item.getTime()))
        .setInternalId(item.getHistoryId().toString())
        .setFee(null) // not available in the API yet
        .setStatus(FundingRecord.Status.COMPLETE)
        .setBalance(item.getFundsAfter().getTotal())
        .build();
  }
}
