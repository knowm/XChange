package org.knowm.xchange.okcoin.v3.service;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.okcoin.OkexDigestV3;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.OkexV3;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class OkexBaseService extends BaseExchangeService<OkexExchangeV3> implements BaseService {

  protected final OkexV3 okex;
  protected final String apikey;
  protected final String passphrase;
  protected final OkexDigestV3 digest;

  protected final String tradepwd;

  public OkexBaseService(OkexExchangeV3 exchange) {
    super(exchange);
    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    okex = ExchangeRestProxyBuilder.forInterface(OkexV3.class, spec).build();
    apikey = spec.getApiKey();
    passphrase = (String) spec.getExchangeSpecificParametersItem("passphrase");

    String secretKey = spec.getSecretKey();
    digest = secretKey == null ? null : new OkexDigestV3(secretKey);
    tradepwd = (String) spec.getExchangeSpecificParametersItem("tradepwd");
  }

  protected static String timestamp() {
    return System.currentTimeMillis() / 1000 + ".000"; //          <-- works as well
    // return Instant.now().toString();
  }
}
