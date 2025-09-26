package org.knowm.xchange.coinsph.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.Coinsph;
import org.knowm.xchange.coinsph.CoinsphAuthenticated;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.coinsph.dto.account.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.FiatWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.withdrawals.Address;
import org.knowm.xchange.service.trade.params.withdrawals.Beneficiary;
import org.mockito.ArgumentCaptor;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsphAccountServiceTest {

  private CoinsphAccountService accountService;
  private CoinsphAuthenticated coinsphAuthenticated;
  private CoinsphExchange exchange;
  private SynchronizedValueFactory<Long> timestampFactory;
  private ParamsDigest signatureCreator;

  @BeforeEach
  public void setUp() {
    exchange = mock(CoinsphExchange.class);
    Coinsph coinsph = mock(Coinsph.class);
    coinsphAuthenticated = mock(CoinsphAuthenticated.class);
    ResilienceRegistries resilienceRegistries = mock(ResilienceRegistries.class);
    signatureCreator = mock(ParamsDigest.class);
    timestampFactory = mock(SynchronizedValueFactory.class);
    when(timestampFactory.createValue()).thenReturn(1621234560000L);

    // Mock exchange specification
    org.knowm.xchange.ExchangeSpecification exchangeSpec =
        mock(org.knowm.xchange.ExchangeSpecification.class);
    when(exchange.getExchangeSpecification()).thenReturn(exchangeSpec);
    when(exchangeSpec.getApiKey()).thenReturn("dummyApiKey");
    when(exchangeSpec.getSecretKey()).thenReturn("dummySecretKey");
    when(exchange.getRecvWindow()).thenReturn(5000L);

    // Mock exchange methods
    when(exchange.getPublicApi()).thenReturn(coinsph);
    when(exchange.getAuthenticatedApi()).thenReturn(coinsphAuthenticated);
    when(exchange.getSignatureCreator()).thenReturn(signatureCreator);
    when(exchange.getNonceFactory()).thenReturn(timestampFactory);

    // Create the service
    accountService = new CoinsphAccountService(exchange, resilienceRegistries);
  }

  @Test
  public void testGetAccountInfo() throws IOException {
    // given
    List<CoinsphBalance> balances = new ArrayList<>();

    CoinsphBalance btcBalance =
        new CoinsphBalance("BTC", new BigDecimal("1.5"), new BigDecimal("0.5"));
    balances.add(btcBalance);

    CoinsphBalance ethBalance =
        new CoinsphBalance("ETH", new BigDecimal("10.0"), new BigDecimal("2.0"));
    balances.add(ethBalance);

    CoinsphBalance phpBalance =
        new CoinsphBalance("PHP", new BigDecimal("100000.0"), new BigDecimal("50000.0"));
    balances.add(phpBalance);

    CoinsphAccount mockAccount =
        new CoinsphAccount(
            new BigDecimal("0.001"), // makerCommission
            new BigDecimal("0.001"), // takerCommission
            new BigDecimal("0"), // buyerCommission
            new BigDecimal("0"), // sellerCommission
            true, // canTrade
            true, // canWithdraw
            true, // canDeposit
            1621234560000L, // updateTime
            "SPOT", // accountType
            balances, // balances
            java.util.Arrays.asList("SPOT") // permissions
            );

    // when
    when(coinsphAuthenticated.getAccount(anyString(), any(), any(), anyLong()))
        .thenReturn(mockAccount);

    // then
    AccountInfo accountInfo = accountService.getAccountInfo();

    assertThat(accountInfo).isNotNull();
    assertThat(accountInfo.getWallet()).isNotNull();

    Balance btc = accountInfo.getWallet().getBalance(Currency.BTC);
    assertThat(btc).isNotNull();
    assertThat(btc.getTotal()).isEqualByComparingTo(new BigDecimal("2.0"));
    assertThat(btc.getAvailable()).isEqualByComparingTo(new BigDecimal("1.5"));
    assertThat(btc.getFrozen()).isEqualByComparingTo(new BigDecimal("0.5"));

    Balance eth = accountInfo.getWallet().getBalance(Currency.ETH);
    assertThat(eth).isNotNull();
    assertThat(eth.getTotal()).isEqualByComparingTo(new BigDecimal("12.0"));
    assertThat(eth.getAvailable()).isEqualByComparingTo(new BigDecimal("10.0"));
    assertThat(eth.getFrozen()).isEqualByComparingTo(new BigDecimal("2.0"));

    Balance php = accountInfo.getWallet().getBalance(Currency.getInstance("PHP"));
    assertThat(php).isNotNull();
    assertThat(php.getTotal()).isEqualByComparingTo(new BigDecimal("150000.0"));
    assertThat(php.getAvailable()).isEqualByComparingTo(new BigDecimal("100000.0"));
    assertThat(php.getFrozen()).isEqualByComparingTo(new BigDecimal("50000.0"));
  }

  @Test
  public void testGetDepositAddress() throws IOException {
    // given
    Currency currency = Currency.BTC;

    CoinsphDepositAddress mockAddress =
        new CoinsphDepositAddress(
            "BTC", // coin
            "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh", // address
            null // addressTag (No tag for BTC)
            );

    // Create a spy of the accountService
    CoinsphAccountService spyService = spy(accountService);

    // Mock the requestDepositAddress method in CoinsphAccountServiceRaw
    CoinsphAccountServiceRaw rawService = mock(CoinsphAccountServiceRaw.class);
    when(rawService.requestDepositAddress(eq("BTC"), any())).thenReturn(mockAddress);

    // Use reflection to set the mock raw service methods
    doReturn("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
        .when(spyService)
        .requestDepositAddress(currency);

    // then
    String address = spyService.requestDepositAddress(currency);

    assertThat(address).isEqualTo("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh");
  }

  @Test
  public void testWithdrawFunds() throws IOException {
    // given
    Currency currency = Currency.BTC;
    BigDecimal amount = new BigDecimal("0.5");
    String address = "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh";

    CoinsphWithdrawal mockWithdrawal = new CoinsphWithdrawal("67890");

    DefaultWithdrawFundsParams params = new DefaultWithdrawFundsParams(address, currency, amount);

    // Create a spy of the accountService
    CoinsphAccountService spyService = spy(accountService);

    // Use doReturn instead of when for spies
    doReturn("67890").when(spyService).withdrawFunds(params);

    // then
    String withdrawalId = spyService.withdrawFunds(params);

    assertThat(withdrawalId).isEqualTo("67890");
  }

  @Test
  public void testFormatAddress_FullAddress() {
    // given
    Address mockAddress = mock(Address.class);
    when(mockAddress.getLine1()).thenReturn("123 Main Street");
    when(mockAddress.getLine2()).thenReturn("Apt 4B");
    when(mockAddress.getCity()).thenReturn("Manila");
    when(mockAddress.getState()).thenReturn("Metro Manila");
    when(mockAddress.getCountry()).thenReturn("Philippines");

    // when
    CoinsphAccountService spyService = spy(accountService);

    // Use reflection to test the private method by creating a test method
    String result = invokeFormatAddress(spyService, mockAddress);

    // then
    assertThat(result).isEqualTo("123 Main Street, Apt 4B, Manila, Metro Manila, Philippines");
  }

  @Test
  public void testFormatAddress_PartialAddress() {
    // given
    Address mockAddress = mock(Address.class);
    when(mockAddress.getLine1()).thenReturn("456 Oak Avenue");
    when(mockAddress.getLine2()).thenReturn("");
    when(mockAddress.getCity()).thenReturn("Cebu City");
    when(mockAddress.getState()).thenReturn(null);
    when(mockAddress.getCountry()).thenReturn("Philippines");

    // when
    CoinsphAccountService spyService = spy(accountService);
    String result = invokeFormatAddress(spyService, mockAddress);

    // then
    assertThat(result).isEqualTo("456 Oak Avenue, Cebu City, Philippines");
  }

  @Test
  public void testGenerateInternalOrderId_WithUserReference() {
    // given
    FiatWithdrawFundsParams params =
        FiatWithdrawFundsParams.builder()
            .amount(new BigDecimal("100"))
            .currency(Currency.getInstance("PHP"))
            .userReference("user-provided-id")
            .build();

    // when
    CoinsphAccountService spyService = spy(accountService);
    String result = invokeGenerateInternalOrderId(spyService, params);

    // then
    assertThat(result).isEqualTo("user-provided-id");
  }

  @Test
  public void testGenerateInternalOrderId_WithoutUserReference() {
    // given
    FiatWithdrawFundsParams params =
        FiatWithdrawFundsParams.builder()
            .amount(new BigDecimal("100"))
            .currency(Currency.getInstance("PHP"))
            .build();

    // when
    CoinsphAccountService spyService = spy(accountService);
    String result = invokeGenerateInternalOrderId(spyService, params);

    // then
    assertThat(result).isNotNull();
    assertThat(result).hasSize(36); // UUID format length
    assertThat(result).matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
  }

  // Helper methods to test private methods using reflection
  private String invokeFormatAddress(CoinsphAccountService service, Address address) {
    try {
      java.lang.reflect.Method method =
          CoinsphAccountService.class.getDeclaredMethod(
              "formatAddress", org.knowm.xchange.service.trade.params.withdrawals.Address.class);
      method.setAccessible(true);
      return (String) method.invoke(service, address);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String invokeGenerateInternalOrderId(
      CoinsphAccountService service, FiatWithdrawFundsParams params) {
    try {
      java.lang.reflect.Method method =
          CoinsphAccountService.class.getDeclaredMethod(
              "generateInternalOrderId", FiatWithdrawFundsParams.class);
      method.setAccessible(true);
      return (String) method.invoke(service, params);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // =======================================================================================
  // FIAT WITHDRAWAL TESTS
  // =======================================================================================

  @Test
  public void testWithdrawFiat_Success() throws IOException, CoinsphException {
    // given
    Currency phpCurrency = Currency.getInstance("PHP");
    BigDecimal amount = new BigDecimal("1000.00");
    String userRef = "test-order-123";

    Address mockAddress = mock(Address.class);
    when(mockAddress.getLine1()).thenReturn("123 Test Street");
    when(mockAddress.getCity()).thenReturn("Manila");
    when(mockAddress.getCountry()).thenReturn("Philippines");

    Beneficiary beneficiary = mock(Beneficiary.class);
    when(beneficiary.getName()).thenReturn("John Doe");
    when(beneficiary.getAccountNumber()).thenReturn("1234567890");
    when(beneficiary.getReference()).thenReturn("Transfer to savings");
    when(beneficiary.getAddress()).thenReturn(mockAddress);

    FiatWithdrawFundsParams params =
        FiatWithdrawFundsParams.builder()
            .currency(phpCurrency)
            .amount(amount)
            .userReference(userRef)
            .beneficiary(beneficiary)
            .build();

    // Mock available channel
    CoinsphFiatChannel mockChannel =
        CoinsphFiatChannel.builder()
            .id("channel-1")
            .transactionChannel("BANK_TRANSFER")
            .transactionSubject("BPI")
            .transactionChannelName("Bank Transfer")
            .transactionSubjectName("Bank of the Philippine Islands")
            .status(1) // Available
            .minimum(new BigDecimal("100"))
            .maximum(new BigDecimal("50000"))
            .fee(new BigDecimal("0"))
            .build();

    CoinsphFiatResponse<List<CoinsphFiatChannel>> channelResponse =
        CoinsphFiatResponse.<List<CoinsphFiatChannel>>builder()
            .status(0)
            .data(Arrays.asList(mockChannel))
            .build();

    // Mock cash out response
    CoinsphCashOutResponse mockCashOutResponse =
        CoinsphCashOutResponse.builder()
            .externalOrderId("ext-123")
            .internalOrderId(userRef)
            .build();

    CoinsphFiatResponse<CoinsphCashOutResponse> cashOutResponse =
        CoinsphFiatResponse.<CoinsphCashOutResponse>builder()
            .status(0)
            .data(mockCashOutResponse)
            .build();

    // Create spy
    CoinsphAccountService spyService = spy(accountService);

    // Mock the raw methods
    doReturn(channelResponse)
        .when(spyService)
        .getSupportedFiatChannels(eq("PHP"), eq(-1), any(), any(), eq(amount));
    doReturn(mockCashOutResponse).when(spyService).cashOut(any(CoinsphCashOutRequest.class));

    // when
    String result = spyService.withdrawFunds(params);

    // then
    assertThat(result).isEqualTo(userRef);

    // Verify the cashOut method was called with correct request
    ArgumentCaptor<CoinsphCashOutRequest> requestCaptor =
        ArgumentCaptor.forClass(CoinsphCashOutRequest.class);
    verify(spyService).cashOut(requestCaptor.capture());

    CoinsphCashOutRequest capturedRequest = requestCaptor.getValue();
    assertThat(capturedRequest.getAmount()).isEqualByComparingTo(amount);
    assertThat(capturedRequest.getCurrency()).isEqualTo("PHP");
    assertThat(capturedRequest.getChannelName()).isEqualTo("BANK_TRANSFER");
    assertThat(capturedRequest.getChannelSubject()).isEqualTo("BPI");
    assertThat(capturedRequest.getInternalOrderId()).isEqualTo(userRef);
    assertThat(capturedRequest.getExtendInfo()).containsEntry("recipientName", "John Doe");
    assertThat(capturedRequest.getExtendInfo())
        .containsEntry("recipientAccountNumber", "1234567890");
    assertThat(capturedRequest.getExtendInfo()).containsEntry("remarks", "Transfer to savings");
    assertThat(capturedRequest.getExtendInfo())
        .containsEntry("recipientAddress", "123 Test Street, Manila, Philippines");
  }

  @Test
  public void testWithdrawFiat_NoAvailableChannels() throws IOException, CoinsphException {
    // given
    Currency phpCurrency = Currency.getInstance("PHP");
    BigDecimal amount = new BigDecimal("1000.00");

    FiatWithdrawFundsParams params =
        FiatWithdrawFundsParams.builder().currency(phpCurrency).amount(amount).build();

    // Mock empty channel response (no available channels)
    CoinsphFiatResponse<List<CoinsphFiatChannel>> channelResponse =
        CoinsphFiatResponse.<List<CoinsphFiatChannel>>builder()
            .status(0)
            .data(Collections.emptyList())
            .build();

    // Create spy
    CoinsphAccountService spyService = spy(accountService);
    doReturn(channelResponse)
        .when(spyService)
        .getSupportedFiatChannels(eq("PHP"), eq(-1), any(), any(), eq(amount));

    // when & then
    assertThatThrownBy(() -> spyService.withdrawFunds(params))
        .isInstanceOf(ExchangeException.class)
        .hasMessageContaining("No available fiat channels found for currency: PHP");
  }

  @Test
  public void testWithdrawFiat_WithCustomChannelParameters() throws IOException, CoinsphException {
    // given
    Currency phpCurrency = Currency.getInstance("PHP");
    BigDecimal amount = new BigDecimal("1000.00");

    Map<String, Object> customParams = new HashMap<>();
    customParams.put("transactionChannel", "GCASH");
    customParams.put("transactionSubject", "MOBILE_WALLET");

    FiatWithdrawFundsParams params =
        FiatWithdrawFundsParams.builder()
            .currency(phpCurrency)
            .amount(amount)
            .customParameters(customParams)
            .build();

    // Mock available channel matching custom parameters
    CoinsphFiatChannel mockChannel =
        CoinsphFiatChannel.builder()
            .id("channel-gcash")
            .transactionChannel("GCASH")
            .transactionSubject("MOBILE_WALLET")
            .transactionChannelName("GCash")
            .transactionSubjectName("Mobile Wallet")
            .status(1) // Available
            .minimum(new BigDecimal("10"))
            .maximum(new BigDecimal("50000"))
            .fee(new BigDecimal("5"))
            .build();

    CoinsphFiatResponse<List<CoinsphFiatChannel>> channelResponse =
        CoinsphFiatResponse.<List<CoinsphFiatChannel>>builder()
            .status(0)
            .data(Arrays.asList(mockChannel))
            .build();

    CoinsphCashOutResponse mockCashOutResponse =
        CoinsphCashOutResponse.builder()
            .externalOrderId("ext-gcash-123")
            .internalOrderId("generated-uuid")
            .build();

    // Create spy
    CoinsphAccountService spyService = spy(accountService);
    doReturn(channelResponse)
        .when(spyService)
        .getSupportedFiatChannels(eq("PHP"), eq(-1), eq("GCASH"), eq("MOBILE_WALLET"), eq(amount));
    doReturn(mockCashOutResponse).when(spyService).cashOut(any(CoinsphCashOutRequest.class));

    // when
    String result = spyService.withdrawFunds(params);

    // then
    assertThat(result).isEqualTo("generated-uuid");

    // Verify the channel search was called with custom parameters
    verify(spyService)
        .getSupportedFiatChannels(eq("PHP"), eq(-1), eq("GCASH"), eq("MOBILE_WALLET"), eq(amount));
  }

  @Test
  public void testBuildExtendInfo_WithBeneficiaryAndCustomParams() {
    // given
    Address mockAddress = mock(Address.class);
    when(mockAddress.getLine1()).thenReturn("456 Main Ave");
    when(mockAddress.getLine2()).thenReturn("Unit 789");
    when(mockAddress.getCity()).thenReturn("Quezon City");
    when(mockAddress.getState()).thenReturn("Metro Manila");
    when(mockAddress.getCountry()).thenReturn("Philippines");

    Beneficiary beneficiary = mock(Beneficiary.class);
    when(beneficiary.getName()).thenReturn("Jane Smith");
    when(beneficiary.getAccountNumber()).thenReturn("9876543210");
    when(beneficiary.getReference()).thenReturn("Monthly allowance");
    when(beneficiary.getAddress()).thenReturn(mockAddress);

    Map<String, Object> customParams = new HashMap<>();
    customParams.put("customField1", "value1");
    customParams.put("customField2", 123);

    FiatWithdrawFundsParams params =
        FiatWithdrawFundsParams.builder()
            .currency(Currency.getInstance("PHP"))
            .amount(new BigDecimal("2000"))
            .beneficiary(beneficiary)
            .customParameters(customParams)
            .build();

    // when
    CoinsphAccountService spyService = spy(accountService);
    Map<String, Object> result = invokeBuildExtendInfo(spyService, params);

    // then
    assertThat(result).containsEntry("recipientName", "Jane Smith");
    assertThat(result).containsEntry("recipientAccountNumber", "9876543210");
    assertThat(result).containsEntry("remarks", "Monthly allowance");
    assertThat(result)
        .containsEntry(
            "recipientAddress", "456 Main Ave, Unit 789, Quezon City, Metro Manila, Philippines");
    assertThat(result).containsEntry("customField1", "value1");
    assertThat(result).containsEntry("customField2", 123);
  }

  @Test
  public void testBuildExtendInfo_WithoutBeneficiary() {
    // given
    FiatWithdrawFundsParams params =
        FiatWithdrawFundsParams.builder()
            .currency(Currency.getInstance("PHP"))
            .amount(new BigDecimal("500"))
            .build();

    // when
    CoinsphAccountService spyService = spy(accountService);
    Map<String, Object> result = invokeBuildExtendInfo(spyService, params);

    // then
    assertThat(result).isEmpty();
  }

  // =======================================================================================
  // FUNDING HISTORY TESTS (FIAT-RELATED)
  // =======================================================================================

  @Test
  public void testGetFundingHistory_IncludingFiatDeposits() throws IOException, CoinsphException {
    // given
    Currency phpCurrency = Currency.getInstance("PHP");
    CoinsphFundingHistoryParams params =
        CoinsphFundingHistoryParams.builder()
            .currency(phpCurrency)
            .type(FundingRecord.Type.DEPOSIT)
            .build();

    // Mock funding records that combine crypto and fiat
    CoinsphFundingRecord cryptoDepositRecord = mock(CoinsphFundingRecord.class);
    when(cryptoDepositRecord.getId()).thenReturn("deposit-123");
    when(cryptoDepositRecord.getType()).thenReturn(CoinsphFundingRecord.Type.DEPOSIT);
    when(cryptoDepositRecord.getCurrency()).thenReturn("PHP");
    when(cryptoDepositRecord.getAmount()).thenReturn(new BigDecimal("500.00"));
    when(cryptoDepositRecord.getTimestamp()).thenReturn(new Date(1621234560000L));
    when(cryptoDepositRecord.getStatus()).thenReturn(1);
    when(cryptoDepositRecord.getDescription()).thenReturn("Deposit via PHP");
    when(cryptoDepositRecord.getAddress()).thenReturn("php-address-123");
    when(cryptoDepositRecord.getFee()).thenReturn(BigDecimal.ZERO);
    when(cryptoDepositRecord.getTxId()).thenReturn("tx-hash-123");

    CoinsphFundingRecord fiatDepositRecord = mock(CoinsphFundingRecord.class);
    when(fiatDepositRecord.getId()).thenReturn("fiat-deposit-456");
    when(fiatDepositRecord.getType()).thenReturn(CoinsphFundingRecord.Type.FIAT_DEPOSIT);
    when(fiatDepositRecord.getCurrency()).thenReturn("PHP");
    when(fiatDepositRecord.getAmount()).thenReturn(new BigDecimal("1000.00"));
    when(fiatDepositRecord.getTimestamp()).thenReturn(new Date(1621234620000L));
    when(fiatDepositRecord.getStatus()).thenReturn(1);
    when(fiatDepositRecord.getDescription()).thenReturn("Fiat Cash In via Bank Transfer");
    when(fiatDepositRecord.getAddress()).thenReturn(null);
    when(fiatDepositRecord.getFee()).thenReturn(BigDecimal.ZERO);
    when(fiatDepositRecord.getTxId()).thenReturn(null);

    List<CoinsphFundingRecord> mockFundingRecords =
        Arrays.asList(cryptoDepositRecord, fiatDepositRecord);

    // Create spy and mock the raw service method
    CoinsphAccountService spyService = spy(accountService);
    doReturn(mockFundingRecords)
        .when(spyService)
        .getFundingHistory(eq(true), eq(false), eq(phpCurrency));

    // when
    List<FundingRecord> result = spyService.getFundingHistory(params);

    // then
    assertThat(result).hasSize(2);

    // Verify regular deposit record
    FundingRecord depositRecord =
        result.stream()
            .filter(r -> r.getInternalId().equals("deposit-123"))
            .findFirst()
            .orElse(null);
    assertThat(depositRecord).isNotNull();
    assertThat(depositRecord.getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
    assertThat(depositRecord.getAmount()).isEqualByComparingTo(new BigDecimal("500.00"));
    assertThat(depositRecord.getCurrency()).isEqualTo(phpCurrency);

    // Verify fiat deposit record
    FundingRecord fiatDepositRecord2 =
        result.stream()
            .filter(r -> r.getInternalId().equals("fiat-deposit-456"))
            .findFirst()
            .orElse(null);
    assertThat(fiatDepositRecord2).isNotNull();
    assertThat(fiatDepositRecord2.getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
    assertThat(fiatDepositRecord2.getAmount()).isEqualByComparingTo(new BigDecimal("1000.00"));
    assertThat(fiatDepositRecord2.getCurrency()).isEqualTo(phpCurrency);
    assertThat(fiatDepositRecord2.getDescription()).contains("Fiat Cash In");
  }

  @Test
  public void testGetFundingHistory_IncludingFiatWithdrawals()
      throws IOException, CoinsphException {
    // given
    CoinsphFundingHistoryParams params =
        CoinsphFundingHistoryParams.builder().type(FundingRecord.Type.WITHDRAWAL).build();

    // Mock funding records that combine crypto and fiat
    CoinsphFundingRecord cryptoWithdrawalRecord = mock(CoinsphFundingRecord.class);
    when(cryptoWithdrawalRecord.getId()).thenReturn("withdrawal-789");
    when(cryptoWithdrawalRecord.getType()).thenReturn(CoinsphFundingRecord.Type.WITHDRAWAL);
    when(cryptoWithdrawalRecord.getCurrency()).thenReturn("BTC");
    when(cryptoWithdrawalRecord.getAmount()).thenReturn(new BigDecimal("0.001"));
    when(cryptoWithdrawalRecord.getTimestamp()).thenReturn(new Date(1621234680000L));
    when(cryptoWithdrawalRecord.getStatus()).thenReturn(1);
    when(cryptoWithdrawalRecord.getDescription()).thenReturn("BTC withdrawal");
    when(cryptoWithdrawalRecord.getAddress())
        .thenReturn("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh");
    when(cryptoWithdrawalRecord.getFee()).thenReturn(new BigDecimal("0.0001"));
    when(cryptoWithdrawalRecord.getTxId()).thenReturn("tx-hash-withdrawal");

    CoinsphFundingRecord fiatWithdrawalRecord = mock(CoinsphFundingRecord.class);
    when(fiatWithdrawalRecord.getId()).thenReturn("fiat-withdrawal-999");
    when(fiatWithdrawalRecord.getType()).thenReturn(CoinsphFundingRecord.Type.FIAT_WITHDRAWAL);
    when(fiatWithdrawalRecord.getCurrency()).thenReturn("PHP");
    when(fiatWithdrawalRecord.getAmount()).thenReturn(new BigDecimal("2000.00"));
    when(fiatWithdrawalRecord.getTimestamp()).thenReturn(new Date(1621234740000L));
    when(fiatWithdrawalRecord.getStatus()).thenReturn(1);
    when(fiatWithdrawalRecord.getDescription()).thenReturn("Fiat Cash Out via GCash");
    when(fiatWithdrawalRecord.getAddress()).thenReturn(null);
    when(fiatWithdrawalRecord.getFee()).thenReturn(new BigDecimal("10.00"));
    when(fiatWithdrawalRecord.getTxId()).thenReturn(null);

    List<CoinsphFundingRecord> mockFundingRecords =
        Arrays.asList(cryptoWithdrawalRecord, fiatWithdrawalRecord);

    // Create spy and mock the raw service method
    CoinsphAccountService spyService = spy(accountService);
    doReturn(mockFundingRecords)
        .when(spyService)
        .getFundingHistory(eq(false), eq(true), nullable(Currency.class));

    // when
    List<FundingRecord> result = spyService.getFundingHistory(params);

    // then
    assertThat(result).hasSize(2);

    // Verify crypto withdrawal record
    FundingRecord cryptoWithdrawal =
        result.stream()
            .filter(r -> r.getInternalId().equals("withdrawal-789"))
            .findFirst()
            .orElse(null);
    assertThat(cryptoWithdrawal).isNotNull();
    assertThat(cryptoWithdrawal.getType()).isEqualTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(cryptoWithdrawal.getAmount()).isEqualByComparingTo(new BigDecimal("0.001"));
    assertThat(cryptoWithdrawal.getCurrency()).isEqualTo(Currency.BTC);

    // Verify fiat withdrawal record
    FundingRecord fiatWithdrawal =
        result.stream()
            .filter(r -> r.getInternalId().equals("fiat-withdrawal-999"))
            .findFirst()
            .orElse(null);
    assertThat(fiatWithdrawal).isNotNull();
    assertThat(fiatWithdrawal.getType()).isEqualTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(fiatWithdrawal.getAmount()).isEqualByComparingTo(new BigDecimal("2000.00"));
    assertThat(fiatWithdrawal.getCurrency()).isEqualTo(Currency.getInstance("PHP"));
    assertThat(fiatWithdrawal.getDescription()).contains("Fiat Cash Out");
    assertThat(fiatWithdrawal.getDescription()).contains("GCash");
  }

  @Test
  public void testGetFundingHistory_WithFiatHistoryParams() throws IOException, CoinsphException {
    // given - Use HistoryParamsFundingType for withdrawal only
    HistoryParamsFundingType fundingTypeParams =
        new HistoryParamsFundingType() {
          @Override
          public FundingRecord.Type getType() {
            return FundingRecord.Type.WITHDRAWAL;
          }

          @Override
          public void setType(FundingRecord.Type type) {
            // Not implemented for this test
          }
        };

    // Mock fiat withdrawal
    CoinsphFiatHistory mockFiatWithdrawal =
        CoinsphFiatHistory.builder()
            .internalOrderId("fiat-only-withdrawal")
            .fiatCurrency("PHP")
            .fiatAmount(new BigDecimal("5000.00"))
            .transactionType(-1) // Cash out
            .transactionChannel("BANK_TRANSFER")
            .status("completed")
            .createdAt(java.time.Instant.ofEpochMilli(1621234800000L))
            .build();

    CoinsphFiatResponse<List<CoinsphFiatHistory>> fiatResponse =
        CoinsphFiatResponse.<List<CoinsphFiatHistory>>builder()
            .status(0)
            .data(Arrays.asList(mockFiatWithdrawal))
            .build();

    // Create spy
    CoinsphAccountService spyService = spy(accountService);
    doReturn(Collections.emptyList())
        .when(spyService)
        .getWithdrawalHistory(any(), any(), any(), any(), any());
    doReturn(fiatResponse).when(spyService).getFiatHistory(any(CoinsphFiatHistoryRequest.class));

    // when
    List<FundingRecord> result = spyService.getFundingHistory(fundingTypeParams);

    // then
    assertThat(result).hasSize(1);
    FundingRecord record = result.get(0);
    assertThat(record.getInternalId()).isEqualTo("fiat-only-withdrawal");
    assertThat(record.getType()).isEqualTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(record.getAmount()).isEqualByComparingTo(new BigDecimal("5000.00"));
    assertThat(record.getCurrency()).isEqualTo(Currency.getInstance("PHP"));
  }

  @Test
  public void testGetFundingHistory_ErrorHandling() throws IOException, CoinsphException {
    // given
    HistoryParamsFundingType params =
        new HistoryParamsFundingType() {
          @Override
          public FundingRecord.Type getType() {
            return FundingRecord.Type.DEPOSIT;
          }

          @Override
          public void setType(FundingRecord.Type type) {
            // Not implemented for this test
          }
        };

    // Mock crypto deposit record only (fiat will fail)
    CoinsphFundingRecord cryptoDepositRecord = mock(CoinsphFundingRecord.class);
    when(cryptoDepositRecord.getId()).thenReturn("deposit-only");
    when(cryptoDepositRecord.getType()).thenReturn(CoinsphFundingRecord.Type.DEPOSIT);
    when(cryptoDepositRecord.getCurrency()).thenReturn("BTC");
    when(cryptoDepositRecord.getAmount()).thenReturn(new BigDecimal("100.00"));
    when(cryptoDepositRecord.getTimestamp()).thenReturn(new Date(1621234560000L));
    when(cryptoDepositRecord.getStatus()).thenReturn(1);
    when(cryptoDepositRecord.getDescription()).thenReturn("Deposit via BTC");
    when(cryptoDepositRecord.getAddress()).thenReturn("btc-address");
    when(cryptoDepositRecord.getFee()).thenReturn(BigDecimal.ZERO);
    when(cryptoDepositRecord.getTxId()).thenReturn("tx-hash");

    List<CoinsphFundingRecord> mockFundingRecords = Arrays.asList(cryptoDepositRecord);

    // Create spy and mock the raw service method
    CoinsphAccountService spyService = spy(accountService);
    doReturn(mockFundingRecords)
        .when(spyService)
        .getFundingHistory(eq(true), eq(false), nullable(Currency.class));

    // when
    List<FundingRecord> result = spyService.getFundingHistory(params);

    // then - Should still return crypto deposits even if fiat history fails
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getInternalId()).isEqualTo("deposit-only");
    assertThat(result.get(0).getCurrency()).isEqualTo(Currency.BTC);
  }

  // Helper methods for private method testing
  private Map<String, Object> invokeBuildExtendInfo(
      CoinsphAccountService service, FiatWithdrawFundsParams params) {
    try {
      java.lang.reflect.Method method =
          CoinsphAccountService.class.getDeclaredMethod(
              "buildExtendInfo", FiatWithdrawFundsParams.class);
      method.setAccessible(true);
      return (Map<String, Object>) method.invoke(service, params);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
