package org.knowm.xchange.dto.account.params;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.currency.Currency;

@Getter
@Setter
@Builder
public class FundingRecordParamAll {

  private String transferId;
  private Currency currency;
  private FundingRecordStatus status;
  private Date startTime;
  private Date endTime;
  private Integer limit;


  public enum FundingRecordStatus {

    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    PENDING("PENDING");

    private final String value;

    FundingRecordStatus(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
}
