package org.knowm.xchange.kucoin;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import com.kucoin.sdk.KucoinClientBuilder;
import com.kucoin.sdk.KucoinRestClient;

public class KucoinBaseService extends BaseExchangeService<KucoinExchange> implements BaseService {

  protected final KucoinRestClient kucoinRestClient;

  protected KucoinBaseService(KucoinExchange exchange) {
    super(exchange);
    ExchangeSpecification spec = exchange.getExchangeSpecification();
    KucoinClientBuilder builder = new KucoinClientBuilder().withBaseUrl(spec.getSslUri());
    if (StringUtils.isNotEmpty(spec.getApiKey())) {
      builder.withApiKey(
        spec.getApiKey(),
        spec.getSecretKey(),
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase")
      );
    }
    kucoinRestClient = builder.buildRestClient();
  }
}
