package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DaseDigestTest {

  @Test
  public void sample_signature_matches_docs() {
    // Given sample from docs
    String secret =
        "NbMBz+ZLqON9yc/tTA8+5eynWD/37pk6b5yq28q9yH99aJyz4fgifpN1wOv28ReSubHvcsT4Yaq8+c12XjArdg==";
    String method = "GET";
    String pathWithQuery = "/v1/orders?status=open";
    String timestamp = "1719325936838";
    String body = "";

    DaseDigest digest = DaseDigest.createInstance(secret);

    // When
    String signature = digest.sign(timestamp, method, pathWithQuery, body);

    // Then
    assertThat(signature).isEqualTo("t7y7D/gZgY+pEugfGN4GhEvZf9Cuee3fTQdDAl8s0rY=");
  }
}
