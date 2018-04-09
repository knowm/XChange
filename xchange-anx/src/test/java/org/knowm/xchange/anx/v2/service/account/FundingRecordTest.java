package org.knowm.xchange.anx.v2.service.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.anx.v2.ANXV2;
import org.knowm.xchange.anx.v2.dto.account.ANXWalletHistoryWrapper;
import org.knowm.xchange.anx.v2.service.ANXAccountService;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.IRestProxyFactory;

@RunWith(MockitoJUnitRunner.class)
public class FundingRecordTest {

  @Mock private ANXV2 anxv2;

  private AccountService accountService;

  private ObjectMapper mapper;

  @Before
  public void setUp() {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    IRestProxyFactory restProxyFactory = mock(IRestProxyFactory.class);
    when(restProxyFactory.createProxy(eq(ANXV2.class), any(), any())).thenReturn(anxv2);

    ExchangeSpecification mockExchangeSpecification = mock(ExchangeSpecification.class);
    when(mockExchangeSpecification.getSslUri()).thenReturn("Non-null-ssl-uri");

    Exchange exchange = mock(Exchange.class);
    when(exchange.getExchangeSpecification()).thenReturn(mockExchangeSpecification);
    accountService = new ANXAccountService(exchange, restProxyFactory);
  }

  @Test
  public void testGetFundingHistory() throws IOException {
    InputStream is =
        getClass().getResourceAsStream("/v2/account/example-wallethistory-response.json");
    ANXWalletHistoryWrapper walletHistoryWrapper =
        mapper.readValue(is, ANXWalletHistoryWrapper.class);
    when(anxv2.getWalletHistory(any(), any(), any(), any(), any(), any(), any()))
        .thenReturn(walletHistoryWrapper);
    List<FundingRecord> fundingRecords =
        accountService.getFundingHistory(accountService.createFundingHistoryParams());
    assertThat(fundingRecords.size()).isGreaterThan(0);
    assertThat(fundingRecords.get(0).getFee()).isNotNull();
  }
}
