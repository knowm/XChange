package com.xeiam.xchange.bitmarket.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * @author kpysniak
 */
public class BitMarketBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitMarketBasePollingService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    return exchange.getMetaData().getCurrencyPairs();
  }

  public boolean isSuccess(BitMarketBaseResponse response) {
    return response != null && response.isSuccess() != null && response.isSuccess() && response.getData() != null;
  }

  public boolean isError(BitMarketBaseResponse response) {
    return response != null && response.getErrorMsg() != null;
  }

}
