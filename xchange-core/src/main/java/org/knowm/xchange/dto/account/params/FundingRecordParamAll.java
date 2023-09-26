package org.knowm.xchange.dto.account.params;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;

@Getter
@Setter
@Builder
public class FundingRecordParamAll {

  private String transferId;
  private Currency currency;
  private FundingRecord.Status status;
  private Date startTime;
  private Date endTime;
  private Integer limit;
}
