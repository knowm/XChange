package org.knowm.xchange.lgo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lgo.CertificateAuthority;
import org.knowm.xchange.lgo.LgoAdapters;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.dto.key.LgoKey;
import si.mazi.rescu.RestProxyFactory;

public class LgoKeyService {

  private final CertificateAuthority proxy;
  private LgoKey currentKey;

  public LgoKeyService(ExchangeSpecification exchangeSpecification) {
    proxy =
        RestProxyFactory.createProxy(
            CertificateAuthority.class,
            exchangeSpecification.getExchangeSpecificParametersItem(LgoEnv.KEYS_URL).toString());
  }

  public LgoKey selectKey() {
    if (mustFetchNewKey()) {
      fetchKey();
    }
    return currentKey;
  }

  private boolean mustFetchNewKey() {
    return currentKey == null
        || Instant.now().minus(10, ChronoUnit.MINUTES).isAfter(currentKey.getDisabledAt());
  }

  private void fetchKey() {
    currentKey =
        LgoAdapters.adaptKeysIndex(proxy.fetchIndex())
            .filter(
                k -> {
                  Instant now = Instant.now();
                  return now.isBefore(k.getDisabledAt()) && now.isAfter(k.getEnabledAt());
                })
            .max(Comparator.comparing(LgoKey::getDisabledAt))
            .orElseThrow(() -> new ExchangeException("Can't find a proper key"));
    String value = proxy.fetchKey(currentKey.getId());
    currentKey.setValue(CryptoUtils.parsePublicKey(value));
  }
}
