package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dase.dto.account.ApiGetAccountTxnsOutput;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.mockito.Mockito;

public class DaseAccountServiceTest {

  @Test
  public void funding_history_params_and_mapping() throws Exception {
    Exchange exchange = Mockito.mock(Exchange.class);
    ExchangeSpecification spec =
        new ExchangeSpecification(org.knowm.xchange.dase.DaseExchange.class);
    when(exchange.getExchangeSpecification()).thenReturn(spec);

    DaseAccountService svc = Mockito.spy(new DaseAccountService(exchange));
    ApiGetAccountTxnsOutput empty = new ApiGetAccountTxnsOutput(Collections.emptyList());
    Mockito.doReturn(empty).when(svc).getAccountTransactions(50, "cursor123");

    TradeHistoryParams p = svc.createFundingHistoryParams();
    ((DaseAccountService.DaseFundingHistoryParams) p).setLimit(50);
    ((DaseAccountService.DaseFundingHistoryParams) p).setBefore("cursor123");

    assertThat(svc.getFundingHistory(p)).isInstanceOf(java.util.List.class);
    assertThat(svc.getFundingHistory(p)).hasSize(0);
  }
}
