package org.knowm.xchange.lgo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

@Ignore
public class LgoExchangeMetadataIntegration {

  @Test
  public void fetchRemoteMetadata() throws IOException {
    Exchange exchange = exchangeWithCredentials();

    assertThat(exchange.getExchangeMetaData().getCurrencyPairs()).hasSize(1);
  }

  // api key and secret key are expected to be in test resources under
  // integration directory
  // this directory is added to .gitignore to avoid committing a real usable key
  protected LgoExchange exchangeWithCredentials() throws IOException {
    ExchangeSpecification spec = LgoEnv.sandbox();
    spec.setSecretKey(readResource("/integration/private_key.pem"));
    spec.setApiKey(readResource("/integration/api_key.txt"));

    return (LgoExchange) ExchangeFactory.INSTANCE.createExchange(spec);
  }

  private String readResource(String path) throws IOException {
    try {
      return new String(
          Files.readAllBytes(Paths.get(getClass().getResource(path).toURI())),
          StandardCharsets.UTF_8);
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
