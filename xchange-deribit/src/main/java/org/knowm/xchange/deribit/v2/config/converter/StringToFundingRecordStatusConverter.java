package org.knowm.xchange.deribit.v2.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.Locale;
import org.knowm.xchange.dto.account.FundingRecord.Status;

/** Converts string to {@code FundingRecord.Status} */
public class StringToFundingRecordStatusConverter extends StdConverter<String, Status> {

  @Override
  public Status convert(String value) {

    switch (value.toUpperCase(Locale.ROOT)) {
      case "CONFIRMED":
      case "PENDING":
      case "REPLACED":
      case "UNCONFIRMED":
        return Status.PROCESSING;
      case "CANCELLED":
      case "REJECTED":
        return Status.CANCELLED;
      case "COMPLETED":
        return Status.COMPLETE;
      case "INTERRUPTED":
        return Status.FAILED;
      default:
        throw new IllegalArgumentException("Can't map " + value);
    }
  }
}
