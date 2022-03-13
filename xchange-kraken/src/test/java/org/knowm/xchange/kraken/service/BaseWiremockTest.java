package org.knowm.xchange.kraken.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.collect.*;
import org.junit.Rule;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.*;
import org.knowm.xchange.kraken.dto.marketdata.*;

public class BaseWiremockTest {

  @Rule public WireMockRule wireMockRule = new WireMockRule();

  public Exchange createExchange() {
    KrakenUtils.setKrakenAssets(ASSETS);
    KrakenUtils.setKrakenAssetPairs(ASSET_PAIRS);
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(KrakenExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("localhost");
    specification.setSslUri("http://localhost:" + wireMockRule.port());
    specification.setPort(wireMockRule.port());
    specification.setShouldLoadRemoteMetaData(false);
    exchange.applySpecification(specification);
    return exchange;
  }

  public static final ImmutableMap<String, KrakenAsset> ASSETS =
      ImmutableMap.of(
          "XXBT", new KrakenAsset("XBT", "currency", 8, 6),
          "ZUSD", new KrakenAsset("USD", "currency", 4, 2));

  public static final ImmutableMap<String, KrakenAssetPair> ASSET_PAIRS =
      ImmutableMap.of("XXBTZUSD", KrakenAssetPair.builder().base("XXBT").quote("ZUSD").build());
}
