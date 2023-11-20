package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTransfers;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProAccount;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProFee;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProFundingHistoryParams;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProLedger;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProWallet;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProWalletAddress;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProSendMoneyResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.params.FundingRecordParamAll;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class CoinbaseProAccountService extends CoinbaseProAccountServiceRaw
    implements AccountService {

  private static final String CB_AFTER_HEADER = "Cb-After";
  private static final String CB_BEFORE_HEADER = "Cb-Before";

  public CoinbaseProAccountService(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(CoinbaseProAdapters.adaptWallet(getCoinbaseProAccountInfo()));
  }

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument() throws IOException {
    CoinbaseProFee fees = getCoinbaseProFees();

    Map<Instrument, Fee> tradingFees = new HashMap<>();
    List<Instrument> pairs = exchange.getExchangeInstruments();

    pairs.forEach(pair -> tradingFees.put(pair, new Fee(fees.getMakerRate(), fees.getTakerRate())));
    return tradingFees;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, AddressWithTag address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawCrypto(
              defaultParams.getAddress(),
              defaultParams.getAmount(),
              defaultParams.getCurrency(),
              defaultParams.getAddressTag(),
              defaultParams.getAddressTag() == null)
          .id;
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  public String moveFunds(Currency currency, String address, BigDecimal amount) throws IOException {
    CoinbaseProAccount[] accounts =
        getCoinbaseProAccountInfo();
    String accountId = null;
    for (CoinbaseProAccount account : accounts) {
      if (currency.getCurrencyCode().equals(account.getCurrency())) {
        accountId = account.getId();
      }
    }

    if (accountId == null) {
      throw new ExchangeException(
          "Cannot determine account id for currency " + currency.getCurrencyCode());
    }

    CoinbaseProSendMoneyResponse response = sendMoney(accountId, address, amount, currency);
    if (response.getData() != null) {
      return response.getData().getId();
    }

    return null;
  }

  private CoinbaseProWalletAddress accountAddress(Currency currency)
      throws IOException {
    CoinbaseProWallet[] coinbaseAccounts = getCoinbaseAccounts();
    CoinbaseProWallet depositAccount = null;

    for (CoinbaseProWallet account : coinbaseAccounts) {
      Currency accountCurrency = Currency.getInstance(account.getCurrency());
      if (account.isActive()
          && "wallet".equals(account.getType())
          && accountCurrency.equals(currency)) {
        depositAccount = account;
        break;
      }
    }

    assert depositAccount != null;
    return getCoinbaseAccountAddress(depositAccount.getId());
  }

  @Deprecated
  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return accountAddress(currency).getAddress();
  }

  @Override
  public AddressWithTag requestDepositAddressData(Currency currency, String... args)
      throws IOException {
    CoinbaseProWalletAddress depositAddress = accountAddress(currency);
    return new AddressWithTag(depositAddress.getAddress(), depositAddress.getDestinationTag());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CoinbaseProFundingHistoryParams();
  }

  @Override
  /*
   * Warning - this method makes several API calls. The reason is that the paging functionality
   * isn't implemented properly yet.
   *
   * <p>It honours TradeHistoryParamCurrency for filtering to a single ccy.
   */
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    String fundingRecordType = null;
    if (params instanceof HistoryParamsFundingType
        && ((HistoryParamsFundingType) params).getType() != null) {
      fundingRecordType = ((HistoryParamsFundingType) params).getType().toString().toLowerCase();
    }

    int maxPageSize = 100;

    List<FundingRecord> fundingHistory = new ArrayList<>();

    String createdAt = null; // use to get next page
    while (true) {
      String createdAtFinal = createdAt;
      CoinbaseProTransfers transfers =
          getTransfers(fundingRecordType, null, null, createdAtFinal, maxPageSize);

      fundingHistory.addAll(
          transfers.stream()
              .map(CoinbaseProAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));

      if (transfers.size() < maxPageSize) {
        break;
      }

      createdAt = transfers.getHeader(CB_AFTER_HEADER);
    }

    return fundingHistory;
  }

  @Override
  public AccountInfo getSubAccountInfo(String subAccountId) throws IOException {
    return new AccountInfo(CoinbaseProAdapters.adaptWallet(getCoinbaseProAccountById(subAccountId)));
  }

  @Override
  public List<FundingRecord> getInternalTransferHistory(FundingRecordParamAll params)
      throws IOException {

    String beforeItem = "";
    String afterItem = "";
    int maxPageSize = 100;

    List<FundingRecord> fundingHistory = new ArrayList<>();

    while (true) {
      CoinbaseProTransfers transfers =
          getTransfers(
              null,
              null,
              beforeItem,
              afterItem,
              maxPageSize);

      fundingHistory.addAll(
          transfers.stream()
              .filter(transfer -> transfer.getType().equals(Type.INTERNAL_DEPOSIT) || transfer.getType().equals(Type.INTERNAL_WITHDRAW))
              .map(CoinbaseProAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));

      if (!transfers.isEmpty()) {
        afterItem = transfers.getHeader(CB_AFTER_HEADER);
        beforeItem = transfers.getHeader(CB_BEFORE_HEADER);
      }

      if (transfers.size() < maxPageSize) {
        break;
      }
    }

    return fundingHistory;
  }

  @Override
  public List<FundingRecord> getWithdrawHistory(FundingRecordParamAll params) throws IOException {
    String beforeItem = "";
    String afterItem = "";
    int maxPageSize = 100;

    List<FundingRecord> fundingHistory = new ArrayList<>();

    while (true) {
      CoinbaseProTransfers transfers =
          getTransfers(
              Type.WITHDRAW.name().toLowerCase(),
              null,
              beforeItem,
              afterItem,
              maxPageSize);

      fundingHistory.addAll(
          transfers.stream()
              .map(CoinbaseProAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));

      if (!transfers.isEmpty()) {
        afterItem = transfers.getHeader(CB_AFTER_HEADER);
        beforeItem = transfers.getHeader(CB_BEFORE_HEADER);
      }

      if (transfers.size() < maxPageSize) {
        break;
      }
    }

    return fundingHistory;
  }

  @Override
  public List<FundingRecord> getSubAccountDepositHistory(FundingRecordParamAll params)
      throws IOException {
    String beforeItem = "";
    String afterItem = "";
    int maxPageSize = 100;

    if(params.getSubAccountId() == null || params.getSubAccountId().isEmpty()){
      throw new IllegalArgumentException("You must provide subAccountId for this call.");
    }

    List<FundingRecord> fundingHistory = new ArrayList<>();

    while (true) {
      CoinbaseProTransfers transfers =
          getTransfersByAccountId(
              params.getSubAccountId(),
              beforeItem,
              afterItem,
              maxPageSize,
              Type.DEPOSIT.name().toLowerCase());

      fundingHistory.addAll(
          transfers.stream()
              .map(CoinbaseProAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));

      if (!transfers.isEmpty()) {
        afterItem = transfers.getHeader(CB_AFTER_HEADER);
        beforeItem = transfers.getHeader(CB_BEFORE_HEADER);
      }

      if (transfers.size() < maxPageSize) {
        break;
      }
    }

    return fundingHistory;
  }

  @Override
  public List<FundingRecord> getDepositHistory(FundingRecordParamAll params) throws IOException {
    String beforeItem = "";
    String afterItem = "";
    int maxPageSize = 100;

    List<FundingRecord> fundingHistory = new ArrayList<>();

    while (true) {
      CoinbaseProTransfers transfers =
          getTransfers(
              Type.DEPOSIT.name().toLowerCase(),
              null,
              beforeItem,
              afterItem,
              maxPageSize);

      fundingHistory.addAll(
          transfers.stream()
              .map(CoinbaseProAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));

      if (!transfers.isEmpty()) {
        afterItem = transfers.getHeader(CB_AFTER_HEADER);
        beforeItem = transfers.getHeader(CB_BEFORE_HEADER);
      }

      if (transfers.size() < maxPageSize) {
        break;
      }
    }

    return fundingHistory;
  }

  @Override
  public List<FundingRecord> getWalletTransferHistory(FundingRecordParamAll params)
      throws IOException {
    String beforeItem = "";
    String afterItem = "";
    int maxPageSize = 100;

    if(params.getSubAccountId() == null || params.getSubAccountId().isEmpty()){
      throw new IllegalArgumentException("You must provide subAccountId for this call.");
    }

    List<FundingRecord> fundingHistory = new ArrayList<>();

    while (true) {
      CoinbaseProTransfers transfers =
          getTransfersByAccountId(
              params.getSubAccountId(),
              beforeItem,
              afterItem,
              maxPageSize,
              null);

      fundingHistory.addAll(
          transfers.stream()
              .map(CoinbaseProAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));

      if (!transfers.isEmpty()) {
        afterItem = transfers.getHeader(CB_AFTER_HEADER);
        beforeItem = transfers.getHeader(CB_BEFORE_HEADER);
      }

      if (transfers.size() < maxPageSize) {
        break;
      }
    }

    return fundingHistory;
  }

  @Override
  public List<FundingRecord> getLedger(FundingRecordParamAll params) throws IOException {
    int maxPageSize = 100;

    if(params.getSubAccountId() == null || params.getSubAccountId().isEmpty()) {
      throw new IOException("You must provide subAccountId for this call.");
    }

    List<FundingRecord> ledgerList = new ArrayList<>();

    String createdAt = null;
    while (true) {
      String createdAtFinal = createdAt;

      CoinbaseProLedger ledger =
          getLedger(
              params.getSubAccountId(),
              params.getStartTime(),
              params.getEndTime(),
              null,
              createdAtFinal,
              params.getLimit(),
              null
          );

      ledgerList.addAll(CoinbaseProAdapters.adaptCoinbaseProLedger(ledger));

      if (ledger.size() < maxPageSize) {
        break;
      }

      createdAt = ledger.getHeader(CB_AFTER_HEADER);
    }

    return ledgerList;
  }

  public static class CoinbaseProMoveFundsParams implements WithdrawFundsParams {
    public final Currency currency;
    public final BigDecimal amount;
    public final String address;

    public CoinbaseProMoveFundsParams(Currency currency, BigDecimal amount, String address) {
      this.currency = currency;
      this.amount = amount;
      this.address = address;
    }
  }
}
