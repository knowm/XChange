package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dase.dto.account.ApiAccountTxn;
import org.knowm.xchange.dase.dto.account.ApiGetAccountTxnsOutput;
import org.knowm.xchange.dase.dto.user.DaseUserProfile;

public class DaseAccountServiceRawTest {

  @Test
  public void deserialize_user_profile_stub() throws Exception {
    String json = "{\n  \"portfolio_id\": \"cbd1e8f4-8b94-4e90-a2b0-20d3a2a2b11f\"\n}";
    ObjectMapper mapper = new ObjectMapper();
    DaseUserProfile dto = mapper.readValue(json, DaseUserProfile.class);
    assertThat(dto.getPortfolioId()).isEqualTo("cbd1e8f4-8b94-4e90-a2b0-20d3a2a2b11f");
  }

  @Test
  public void deserialize_account_transactions_stub() throws Exception {
    String json =
        "{\n"
            + "  \"transactions\": [\n"
            + "    {\n"
            + "      \"id\": \"6a0b7c40-1e16-4e1c-a4c5-1d9fdf7e9d21\",\n"
            + "      \"currency\": \"EUR\",\n"
            + "      \"txn_type\": \"deposit\",\n"
            + "      \"amount\": \"100.00\",\n"
            + "      \"created_at\": 1719354237834,\n"
            + "      \"trade_id\": null,\n"
            + "      \"funding_id\": \"b7c2d8e1-3f4a-4b5c-8d9e-1f2a3b4c5d6e\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";
    ObjectMapper mapper = new ObjectMapper();
    ApiGetAccountTxnsOutput dto = mapper.readValue(json, ApiGetAccountTxnsOutput.class);
    assertThat(dto.getTransactions()).hasSize(1);
    ApiAccountTxn t = dto.getTransactions().get(0);
    assertThat(t.getId()).isEqualTo("6a0b7c40-1e16-4e1c-a4c5-1d9fdf7e9d21");
    assertThat(t.getCurrency()).isEqualTo("EUR");
    assertThat(t.getTxnType()).isEqualTo("deposit");
    assertThat(t.getAmount().toPlainString()).isEqualTo("100.00");
    assertThat(t.getCreatedAt()).isEqualTo(1719354237834L);
    assertThat(t.getTradeId()).isNull();
    assertThat(t.getFundingId()).isEqualTo("b7c2d8e1-3f4a-4b5c-8d9e-1f2a3b4c5d6e");
  }

  @Test
  public void deserialize_account_transactions_with_optional_null_funding_id() throws Exception {
    String json =
        "{\n"
            + "  \"transactions\": [\n"
            + "    {\n"
            + "      \"id\": \"7e2f8c91-4d5e-4a6b-9c8d-2e3f4a5b6c7d\",\n"
            + "      \"currency\": \"USD\",\n"
            + "      \"txn_type\": \"withdrawal\",\n"
            + "      \"amount\": \"50.25\",\n"
            + "      \"created_at\": 1719354300000,\n"
            + "      \"trade_id\": \"t456\",\n"
            + "      \"funding_id\": null\n"
            + "    }\n"
            + "  ]\n"
            + "}";
    ObjectMapper mapper = new ObjectMapper();
    ApiGetAccountTxnsOutput dto = mapper.readValue(json, ApiGetAccountTxnsOutput.class);
    assertThat(dto.getTransactions()).hasSize(1);
    ApiAccountTxn t = dto.getTransactions().get(0);
    assertThat(t.getId()).isEqualTo("7e2f8c91-4d5e-4a6b-9c8d-2e3f4a5b6c7d");
    assertThat(t.getCurrency()).isEqualTo("USD");
    assertThat(t.getTxnType()).isEqualTo("withdrawal");
    assertThat(t.getAmount().toPlainString()).isEqualTo("50.25");
    assertThat(t.getCreatedAt()).isEqualTo(1719354300000L);
    assertThat(t.getTradeId()).isEqualTo("t456");
    assertThat(t.getFundingId()).isNull(); // Demonstrating optional nature
  }
}
