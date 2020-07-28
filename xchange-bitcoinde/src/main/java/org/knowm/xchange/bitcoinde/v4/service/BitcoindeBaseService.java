package org.knowm.xchange.bitcoinde.v4.service;

import org.knowm.xchange.bitcoinde.v4.Bitcoinde;
import org.knowm.xchange.bitcoinde.v4.BitcoindeErrorAdapter;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class BitcoindeBaseService extends BaseExchangeService<BitcoindeExchange>
    implements BaseService {

  protected final Bitcoinde bitcoinde;
  protected final String apiKey;
  protected final BitcoindeDigest signatureCreator;

  protected BitcoindeBaseService(BitcoindeExchange exchange) {
    super(exchange);
    this.bitcoinde =
        RestProxyFactory.createProxy(
            Bitcoinde.class,
            exchange.getExchangeSpecification().getSslUri(),
            ExchangeRestProxyBuilder.createClientConfig(exchange.getExchangeSpecification()));
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BitcoindeDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), apiKey);
  }

  protected RuntimeException handleError(BitcoindeException exception) {
    return BitcoindeErrorAdapter.adaptBitcoindeException(exception);
  }
}
