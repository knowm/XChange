package org.knowm.xchange.anx.v2.service.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.anx.v2.ANXExchange;
import org.knowm.xchange.anx.v2.ANXV2;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryWrapper;
import org.knowm.xchange.anx.v2.service.ANXAccountService;
import org.knowm.xchange.anx.v2.service.ANXV2Digest;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.mockito.internal.stubbing.answers.Returns;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author ujjwal on 28/02/18.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RestProxyFactory.class, ANXV2Digest.class})
public class FundingRecordTest {
  private ANXV2 anxv2;
  private ANXV2Digest signatureCreator;
  private AccountService accountService;
  private Exchange exchange;
  private ObjectMapper mapper;

  @Before
  public void setUp() {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    anxv2 = mock(ANXV2.class);
    signatureCreator = mock(ANXV2Digest.class);
    exchange = mock(ANXExchange.class);
    mockStatic(RestProxyFactory.class);
    when(RestProxyFactory.createProxy(eq(ANXV2.class), any(), any())).thenReturn(anxv2);
    mockStatic(ANXV2Digest.class, new Returns(signatureCreator));
    ExchangeSpecification mockExchangeSpecification = mock(ExchangeSpecification.class);
    when(mockExchangeSpecification.getSslUri()).thenReturn("Non-null-ssl-uri");
    when(exchange.getExchangeSpecification()).thenReturn(mockExchangeSpecification);
    accountService = new ANXAccountService(exchange);
  }

  @Test
  public void testGetFundingHistory() throws IOException {
    InputStream is = getClass().getResourceAsStream("/v2/account/example-wallethistory-response.json");
    ANXWalletHistoryWrapper walletHistoryWrapper = mapper.readValue(is, ANXWalletHistoryWrapper.class);
    when(anxv2.getWalletHistory(any(), any(), any(), any(), any(), any(), any())).thenReturn(walletHistoryWrapper);
    List<FundingRecord> fundingRecords = accountService.getFundingHistory(accountService.createFundingHistoryParams());
    assertThat(fundingRecords.size()).isGreaterThan(0);
    assertThat(fundingRecords.get(0).getFee()).isNotNull();
  }
}
