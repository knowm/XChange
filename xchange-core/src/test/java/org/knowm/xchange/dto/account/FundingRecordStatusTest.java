package org.knowm.xchange.dto.account;

import static java.math.BigDecimal.ONE;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.dto.account.FundingRecord.Type.DEPOSIT;

import org.junit.Test;

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
    testStatusDesc("Unknown", null, null, "Unknown");
  }

  @Test
  public void shouldPrependUnrecognizedStatusStringToDescription() throws Exception {
    testStatusDesc("AdminCancelled", "The administrator has cancelled the transfers.", null, "AdminCancelled: The administrator has cancelled the transfers.");
  }

  private void testStatusDesc(
      String statusInput,
      String descriptionInput,
      FundingRecord.Status expectedStatus, String expectedDescription
  ) {
    final FundingRecord fundingRecord = new FundingRecord("", null, BTC, ONE, "", "", DEPOSIT, statusInput, ONE, ONE, descriptionInput);
    assertThat(fundingRecord.getStatus()).isEqualTo(expectedStatus);
    assertThat(fundingRecord.getDescription()).isEqualTo(expectedDescription);
  }
}