package org.knowm.xchange.coinbene.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Map;
import java.util.TreeMap;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.CoinbeneAuthenticated;
import org.knowm.xchange.coinbene.CoinbeneException;
import org.knowm.xchange.coinbene.dto.CoinbeneResponse;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class CoinbeneBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final String secretKey;
  protected final CoinbeneAuthenticated coinbene;

  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CoinbeneBaseService(Exchange exchange) {
    super(exchange);
    this.coinbene =
        RestProxyFactory.createProxy(
            CoinbeneAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.secretKey = exchange.getExchangeSpecification().getSecretKey();
  }

  /** Sign request JSON. */
  protected JsonNode formAndSignRequestJson(Map<String, String> params) {
    CoinbeneUtils.signParams(params);
    return toJson(params);
  }

  /** Return private API common params. */
  protected Map<String, String> getCommonParams() {
    Map<String, String> params = new TreeMap<>();
    params.put("apiid", apiKey);
    params.put("secret", secretKey);
    params.put("timestamp", String.valueOf(exchange.getNonceFactory().createValue()));
    return params;
  }

  public static <T extends CoinbeneResponse> T checkSuccess(T response) {
    if (response.isOk()) {
      return response;
    } else {
      throw new CoinbeneException(response.getErrorDescription());
    }
  }

  private JsonNode toJson(Map<String, String> params) {
    ObjectNode node = MAPPER.createObjectNode();
    params.forEach(node::put);
    return node;
  }
}
