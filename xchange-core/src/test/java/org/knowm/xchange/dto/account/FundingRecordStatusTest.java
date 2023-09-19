package org.knowm.xchange.dto.account;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.dto.account.FundingRecord.Type.DEPOSIT;

import org.junit.Test;
import org.knowm.xchange.dto.account.FundingRecord.Status;

public class FundingRecordStatusTest {

  @Test
  public void shouldProcessStatusDescriptionNormal() throws Exception {
    testStatusDesc("PROCESSING", "foo", FundingRecord.Status.PROCESSING, "foo");
  }

  @Test
  public void shouldProcessStatusToUpercase() throws Exception {
    testStatusDesc("Complete", "bar", FundingRecord.Status.COMPLETE, "bar");
  }

  @Test
  public void shouldProcessNullDescription() throws Exception {
    testStatusDesc("COMPLETE", null, FundingRecord.Status.COMPLETE, null);
  }

  @Test
  public void shouldProcessStatusAsDescriptionWhenDescInputNull() throws Exception {
    testStatusDesc("Unknown", null, null, null);
  }

  private void testStatusDesc(
      String statusInput,
      String descriptionInput,
      FundingRecord.Status expectedStatus,
      String expectedDescription) {
    final FundingRecord fundingRecord = FundingRecord.builder()
        .currency(BTC)
        .amount(ONE)
        .type(DEPOSIT)
        .status(Status.resolveStatus(statusInput))
        .fee(ONE)
        .balance(ONE)
        .description(descriptionInput)
        .build();

    assertThat(fundingRecord.getStatus()).isEqualTo(expectedStatus);
    assertThat(fundingRecord.getDescription()).isEqualTo(expectedDescription);
  }
}
