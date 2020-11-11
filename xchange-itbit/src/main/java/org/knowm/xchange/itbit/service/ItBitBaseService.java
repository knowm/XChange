package org.knowm.xchange.itbit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.itbit.ItBit;
import org.knowm.xchange.itbit.ItBitAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class ItBitBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final ItBitAuthenticated itBitAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final ItBit itBitPublic;

  protected String userId;
  protected String walletId;

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitBaseService(Exchange exchange) {

    super(exchange);

    final String baseUrl =
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("authHost");
    this.itBitAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                ItBitAuthenticated.class, exchange.getExchangeSpecification())
            .baseUrl(baseUrl)
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        ItBitHmacPostBodyDigest.createInstance(
            apiKey, exchange.getExchangeSpecification().getSecretKey());

    this.itBitPublic =
        ExchangeRestProxyBuilder.forInterface(ItBit.class, exchange.getExchangeSpecification())
            .build();

    this.userId =
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("userId");
    this.walletId =
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("walletId");

    if ((this.userId == null || this.walletId == null)
        && exchange.getExchangeSpecification().getUserName() != null) {
      String[] userIdAndWalletId = exchange.getExchangeSpecification().getUserName().split("/");
      if (userIdAndWalletId.length != 2) {
        throw new IllegalArgumentException(
            "Please specify the userId and walletId either in the ExchangeSpecification specific parameters, or in the userName field as userId/walletId.");
      }
      this.userId = userIdAndWalletId[0];
      this.walletId = userIdAndWalletId[1];
    }
  }
}
