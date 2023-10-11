package org.knowm.xchange.dto.account.params;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FundingRecordParamAll {

  private String accountCategory;
  private String transferId;
  private String subAccountId;
  private Currency currency;
  private FundingRecord.Status status;
  private Date startTime;
  private Date endTime;
  private Integer limit;
  private Type type;
  private boolean usePagination;

  public static FundingRecordParamAllBuilder builder() {
    return new FundingRecordParamAllBuilder().usePagination(true);
  }
}