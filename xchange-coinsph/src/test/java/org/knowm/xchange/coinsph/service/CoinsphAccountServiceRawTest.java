package org.knowm.xchange.coinsph.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.CoinsphAuthenticated;
import org.knowm.xchange.coinsph.CoinsphExchange;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinsphAccountServiceRawTest {

  private CoinsphAccountServiceRaw accountServiceRaw;
  private CoinsphAuthenticated coinsphAuthenticated;
  private CoinsphExchange exchange;
  private SynchronizedValueFactory<Long> timestampFactory;
  private ParamsDigest signatureCreator;

  @BeforeEach
  public void setUp() {
    exchange = mock(CoinsphExchange.class);
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
    when(exchange.getAuthenticatedApi()).thenReturn(coinsphAuthenticated);
    when(exchange.getSignatureCreator()).thenReturn(signatureCreator);
    when(exchange.getNonceFactory()).thenReturn(timestampFactory);

    // Create the raw service
    accountServiceRaw = new CoinsphAccountServiceRaw(exchange, resilienceRegistries);
  }
}
