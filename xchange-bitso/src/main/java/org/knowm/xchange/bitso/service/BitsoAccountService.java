package org.knowm.xchange.bitso.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAdapters;
import org.knowm.xchange.bitso.BitsoFundingAdapters;
import org.knowm.xchange.bitso.dto.funding.BitsoFunding;
import org.knowm.xchange.bitso.dto.funding.BitsoWithdrawal;
import org.knowm.xchange.bitso.dto.funding.BitsoWithdrawalMethod;
import org.knowm.xchange.bitso.dto.funding.BitsoWithdrawalRequest;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

/**
 * @author Matija Mazi
 */
public class BitsoAccountService extends BitsoAccountServiceRaw implements AccountService {

  public BitsoAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(
        exchange.getExchangeSpecification().getUserName(),
        BitsoAdapters.adaptWallet(getBitsoBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    // Use default network for crypto withdrawals
    return withdrawCrypto(currency.getCurrencyCode(), amount, address, null, null);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof FiatWithdrawFundsParams) {
      FiatWithdrawFundsParams fiatParams = (FiatWithdrawFundsParams) params;
      return withdrawFiat(fiatParams);
    } else if (params instanceof NetworkWithdrawFundsParams) {
      NetworkWithdrawFundsParams networkParams = (NetworkWithdrawFundsParams) params;
      return withdrawCrypto(
          networkParams.getCurrency().getCurrencyCode(),
          networkParams.getAmount(),
          networkParams.getAddress(),
          networkParams.getNetwork(),
          networkParams.getAddressTag());
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }

    throw new IllegalArgumentException(
        "WithdrawFundsParams must be DefaultWithdrawFundsParams, NetworkWithdrawFundsParams, or FiatWithdrawFundsParams");
  }

  private String withdrawCrypto(
      String currency, BigDecimal amount, String address, String network, String addressTag)
      throws IOException {
    List<BitsoWithdrawalMethod> methods = getBitsoWithdrawalMethods(currency);

    System.out.println("Available withdrawal methods for " + currency + ":" + methods);

    List<BitsoWithdrawalMethod> availableMethods =
        methods.stream()
            .filter(m -> m.getMethod().equalsIgnoreCase(network))
            .collect(Collectors.toList());

    if (availableMethods.size() > 1) {
      throw new ExchangeException(
          "Multiple withdrawal methods available for " + currency + " with network " + network);
    }
    if (availableMethods.isEmpty()) {
      throw new ExchangeException(
          "No withdrawal method available for " + currency + " with network " + network);
    }

    BitsoWithdrawalMethod method = availableMethods.get(0);

    BitsoWithdrawalRequest request =
        BitsoWithdrawalRequest.builder()
            .currency(BitsoAdapters.toBitsoCurrency(currency))
            .asset(currency.toLowerCase())
            .amount(amount)
            .address(address)
            .network(method.getNetwork())
            .protocol(method.getProtocol())
            .method(method.getMethod())
            .addressTag(addressTag)
            .build();

    // Execute crypto withdrawal
    BitsoWithdrawal withdrawal = createBitsoCryptoWithdrawal(request);
    return withdrawal.getWithdrawalId();
  }

  private String withdrawFiat(FiatWithdrawFundsParams params) throws IOException {
    String currency = params.getCurrency().getCurrencyCode();

    // Handle different fiat currencies based on Bitso documentation
    switch (currency) {
      case "MXN":
        return withdrawMXN(params);
      default:
        throw new ExchangeException("Unsupported fiat currency for withdrawal: " + currency);
    }
  }

  private String withdrawMXN(FiatWithdrawFundsParams params) throws IOException {
    // MXN withdrawals via SPEI
    BitsoWithdrawalRequest request =
        BitsoWithdrawalRequest.builder()
            .currency(BitsoAdapters.toBitsoCurrency(params.getCurrency().getCurrencyCode()))
            .amount(params.getAmount())
            .protocol("clabe")
            .notesRef(params.getBeneficiary().getReference())
            .beneficiary(params.getBeneficiary().getName())
            .clabe(params.getBeneficiary().getAccountNumber())
            .institutionCode(params.getBeneficiary().getBank().getCode())
            .originId(params.getUserReference())
            .build();

    BitsoWithdrawal withdrawal = createBitsoFiatWithdrawal(request);
    return withdrawal.getWithdrawalId();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BitsoFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    HistoryParamsFundingType bitsoParams;

    boolean includeDeposits = true;
    boolean includeWithdrawals = true;
    Currency currency = null;

    if (params instanceof HistoryParamsFundingType) {
      bitsoParams = (HistoryParamsFundingType) params;
      includeDeposits = bitsoParams.getType() == FundingRecord.Type.DEPOSIT;
      includeWithdrawals = bitsoParams.getType() == FundingRecord.Type.WITHDRAWAL;
    }
    if (params instanceof TradeHistoryParamCurrency) {
      currency = ((TradeHistoryParamCurrency) params).getCurrency();
    }

    List<FundingRecord> fundingRecords = new ArrayList<>();

    // Get deposits if requested
    if (includeDeposits) {
      List<BitsoFunding> deposits =
          getBitsoFundings(
              currency != null ? currency.getCurrencyCode() : null,
              null,
              null, // marker for pagination
              "desc");

      fundingRecords.addAll(BitsoFundingAdapters.adaptFundingRecords(deposits));
    }

    // Get withdrawals if requested
    if (includeWithdrawals) {
      List<BitsoWithdrawal> withdrawals =
          getBitsoWithdrawals(
              currency != null ? currency.getCurrencyCode() : null,
              null,
              null, // marker for pagination
              "desc");

      fundingRecords.addAll(BitsoFundingAdapters.adaptWithdrawalRecords(withdrawals));
    }

    return fundingRecords;
  }
}
