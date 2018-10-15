package org.knowm.xchange.bitmex.dto.trade;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public enum BitmexExecutionInstruction {
  PARTICIPATE_DO_NOT_INITIATE("ParticipateDoNotInitiate"),
  ALL_OR_NONE("AllOrNone"),
  MARK_PRICE("MarkPrice"),
  INDEX_PRICE("IndexPrice"),
  LAST_PRICE("LastPrice"),
  CLOSE("Close"),
  REDUCE_ONLY("ReduceOnly"),
  FIXED("Fixed");

  private String apiParameter;

  BitmexExecutionInstruction(String apiParameter) {
    this.apiParameter = apiParameter;
  }

  @Override
  public String toString() {
    return apiParameter;
  }

  public static class Builder {

    private boolean postOnly;
    private boolean allOrNone;
    private boolean markPrice;
    private boolean indexPrice;
    private boolean lastPrice;
    private boolean close;
    private boolean reduceOnly;
    private boolean fixed;

    public List<BitmexExecutionInstruction> build() {
      final ArrayList<BitmexExecutionInstruction> instructions = new ArrayList<>();
      if (postOnly) {
        instructions.add(PARTICIPATE_DO_NOT_INITIATE);
      }
      if (allOrNone) {
        instructions.add(ALL_OR_NONE);
      }
      if (markPrice) {
        instructions.add(MARK_PRICE);
      }
      if (indexPrice) {
        instructions.add(INDEX_PRICE);
      }
      if (lastPrice) {
        instructions.add(LAST_PRICE);
      }
      if (close) {
        instructions.add(CLOSE);
      }
      if (reduceOnly) {
        instructions.add(REDUCE_ONLY);
      }
      if (fixed) {
        instructions.add(FIXED);
      }
      return instructions;
    }

    public Builder setPostOnly(boolean postOnly) {
      this.postOnly = postOnly;
      return this;
    }

    public Builder setAllOrNone(boolean allOrNone) {
      this.allOrNone = allOrNone;
      return this;
    }

    public Builder setMarkPrice(boolean markPrice) {
      this.markPrice = markPrice;
      return this;
    }

    public Builder setIndexPrice(boolean indexPrice) {
      this.indexPrice = indexPrice;
      return this;
    }

    public Builder setLastPrice(boolean lastPrice) {
      this.lastPrice = lastPrice;
      return this;
    }

    public Builder setClose(boolean close) {
      this.close = close;
      return this;
    }

    public Builder setReduceOnly(boolean reduceOnly) {
      this.reduceOnly = reduceOnly;
      return this;
    }

    public Builder setFixed(boolean fixed) {
      this.fixed = fixed;
      return this;
    }
  }
}
