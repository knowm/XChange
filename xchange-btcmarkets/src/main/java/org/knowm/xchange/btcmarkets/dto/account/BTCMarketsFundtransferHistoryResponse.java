package org.knowm.xchange.btcmarkets.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;

public class BTCMarketsFundtransferHistoryResponse extends BTCMarketsBaseResponse {
  public static class Paging {
    private String newer;
    private String older;

    public Paging(@JsonProperty("newer") String newer, @JsonProperty("older") String older) {
      this.newer = newer;
      this.older = older;
    }

    @Override
    public String toString() {
      return "Paging{" + "newer='" + newer + '\'' + ", older='" + older + '\'' + '}';
    }
  }

  private List<BTCMarketsFundtransfer> fundTransfers;
  private Paging paging;

  public BTCMarketsFundtransferHistoryResponse(
      @JsonProperty("success") Boolean success,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode,
      @JsonProperty("fundTransfers") List<BTCMarketsFundtransfer> fundTransfers,
      @JsonProperty("paging") Paging paging) {
    super(success, errorMessage, errorCode);
    this.fundTransfers = fundTransfers;
    this.paging = paging;
  }

  public List<BTCMarketsFundtransfer> getFundTransfers() {
    return fundTransfers;
  }

  public void setFundTransfers(List<BTCMarketsFundtransfer> fundTransfers) {
    this.fundTransfers = fundTransfers;
  }

  public Paging getPaging() {
    return paging;
  }

  public void setPaging(Paging paging) {
    this.paging = paging;
  }

  @Override
  public String toString() {
    return "BTCMarketsFundtransferHistoryResponse{"
        + "fundTransfers="
        + fundTransfers
        + ", paging="
        + paging
        + '}';
  }
}
