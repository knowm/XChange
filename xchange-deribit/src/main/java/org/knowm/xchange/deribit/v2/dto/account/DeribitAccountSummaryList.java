package org.knowm.xchange.deribit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class DeribitAccountSummaryList {

  @JsonProperty("summaries")
  private List<DeribitAccountSummary> accountSummaries;

}
