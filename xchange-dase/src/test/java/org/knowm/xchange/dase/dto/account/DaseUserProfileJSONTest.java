package org.knowm.xchange.dase.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dase.dto.user.DaseUserProfile;

public class DaseUserProfileJSONTest {

  @Test
  public void unmarshal() throws Exception {
    String json = "{\n  \"portfolio_id\": \"11111111-2222-3333-4444-555555555555\"\n}";
    ObjectMapper mapper = new ObjectMapper();
    DaseUserProfile dto = mapper.readValue(json, DaseUserProfile.class);
    assertThat(dto.getPortfolioId()).isEqualTo("11111111-2222-3333-4444-555555555555");
  }
}
