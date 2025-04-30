package org.knowm.xchange.bitget.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.Locale;
import org.knowm.xchange.dto.account.FundingRecord.Status;

/** Converts string to {@code FundingRecord.Status} */
public class StringToFundingRecordStatusConverter extends StdConverter<String, Status> {

  @Override
  public Status convert(String value) {
    switch (value.toUpperCase(Locale.ROOT)) {
      case "PENDING":
        return Status.PROCESSING;
      case "FAIL":
        return Status.FAILED;
      case "SUCCESS":
        return Status.COMPLETE;
      default:
        throw new IllegalArgumentException("Can't map " + value);
    }
  }
}
