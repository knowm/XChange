package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.ripple.dto.RippleAmount;

public class RipplePaymentTransaction implements IRippleTradeTransaction {

  private Payment payment;

  @JsonProperty("client_resource_id")
  private String clientResourceId;

  private String hash;

  private String ledger;

  private String state;

  private boolean success;

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(final Payment value) {
    payment = value;
  }

  public String getClientResourceId() {
    return clientResourceId;
  }

  public void setClientResourceId(final String value) {
    clientResourceId = value;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(final String value) {
    hash = value;
  }

  public String getLedger() {
    return ledger;
  }

  public void setLedger(final String value) {
    ledger = value;
  }

  public String getState() {
    return state;
  }

  public void setState(final String value) {
    state = value;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(final boolean value) {
    success = value;
  }

  @Override
  public List<RippleAmount> getBalanceChanges() {
    return payment.getBalanceChanges();
  }

  @Override
  public BigDecimal getFee() {
    return payment.getFee();
  }

  @Override
  public long getOrderId() {
    if (payment.orderChanges.size() == 1) {
      return payment.orderChanges.get(0).getSequence();
    } else {
      return 0; // cannot identify a single order
    }
  }

  @Override
  public Date getTimestamp() {
    return getPayment().getTimestamp();
  }

  public static class OrderChange {
    @JsonProperty("taker_pays")
    private RippleAmount takerPays;

    @JsonProperty("taker_gets")
    private RippleAmount takerGets;

    private long sequence;

    private String status;

    public RippleAmount getTakerPays() {
      return takerPays;
    }

    public void setTakerPays(final RippleAmount value) {
      takerPays = value;
    }

    public RippleAmount getTakerGets() {
      return takerGets;
    }

    public void setTakerGets(final RippleAmount value) {
      takerGets = value;
    }

    public long getSequence() {
      return sequence;
    }

    public void setSequence(final long value) {
      sequence = value;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(final String value) {
      status = value;
    }
  }

  public static class Memo {
    @JsonProperty("MemoType")
    private String memoType;

    @JsonProperty("MemoData")
    private String memoData;

    @JsonProperty("MemoFormat")
    private String memoFormat;

    @JsonProperty("parsed_memo_type")
    private String parsedMemoType;

    @JsonProperty("parsed_memo_format")
    private String parsedMemoFormat;

    public String getMemoType() {
      return memoType;
    }

    public void setMemoType(final String value) {
      memoType = value;
    }

    public String getMemoData() {
      return memoData;
    }

    public void setMemoData(final String value) {
      memoData = value;
    }

    public String getMemoFormat() {
      return memoFormat;
    }

    public void setMemoFormat(final String value) {
      memoFormat = value;
    }

    public String getParsedMemoType() {
      return parsedMemoType;
    }

    public void setParsedMemoType(final String value) {
      parsedMemoType = value;
    }

    public String getParsedMemoFormat() {
      return parsedMemoFormat;
    }

    public void setParsedMemoFormat(final String value) {
      parsedMemoFormat = value;
    }
  }

  public static class Payment {
    @JsonProperty("source_account")
    private String sourceAccount;

    @JsonProperty("source_tag")
    private String sourceTag;

    @JsonProperty("source_amount")
    private RippleAmount sourceAmount;

    @JsonProperty("source_slippage")
    private String sourceSlippage;

    @JsonProperty("destination_account")
    private String destinationAccount;

    @JsonProperty("destination_tag")
    private String destinationTag;

    @JsonProperty("destination_amount")
    private RippleAmount destinationAmount;

    @JsonProperty("invoice_id")
    private String invoiceID;

    private String paths;

    @JsonProperty("no_direct_ripple")
    private boolean noDirectRipple;

    @JsonProperty("partial_payment")
    private boolean partialPayment;

    private String direction;

    @JsonProperty("timestamp")
    private Date timestamp;

    private BigDecimal fee;

    private String result;

    @JsonProperty("balance_changes")
    private List<RippleAmount> balanceChanges;

    @JsonProperty("source_balance_changes")
    private List<RippleAmount> sourceBalanceChanges;

    @JsonProperty("destination_balance_changes")
    private List<RippleAmount> destinationBalanceChanges;

    @JsonProperty("order_changes")
    private List<OrderChange> orderChanges;

    public String getSourceAccount() {
      return sourceAccount;
    }

    public void setSourceAccount(final String value) {
      sourceAccount = value;
    }

    public String getSourceTag() {
      return sourceTag;
    }

    public void setSourceTag(final String value) {
      sourceTag = value;
    }

    public RippleAmount getSourceAmount() {
      return sourceAmount;
    }

    public void setSourceAmount(final RippleAmount value) {
      sourceAmount = value;
    }

    public String getSourceSlippage() {
      return sourceSlippage;
    }

    public void setSourceSlippage(final String value) {
      sourceSlippage = value;
    }

    public String getDestinationAccount() {
      return destinationAccount;
    }

    public void setDestinationAccount(final String value) {
      destinationAccount = value;
    }

    public String getDestinationTag() {
      return destinationTag;
    }

    public void setDestinationTag(final String value) {
      destinationTag = value;
    }

    public RippleAmount getDestinationAmount() {
      return destinationAmount;
    }

    public void setDestinationAmount(final RippleAmount value) {
      destinationAmount = value;
    }

    public String getInvoiceID() {
      return invoiceID;
    }

    public void setInvoiceID(final String value) {
      invoiceID = value;
    }

    public String getPaths() {
      return paths;
    }

    public void setPaths(final String value) {
      paths = value;
    }

    public boolean isNoDirectRipple() {
      return noDirectRipple;
    }

    public void setNoDirectRipple(final boolean value) {
      noDirectRipple = value;
    }

    public boolean isPartialPayment() {
      return partialPayment;
    }

    public void setPartialPayment(final boolean value) {
      partialPayment = value;
    }

    public String getDirection() {
      return direction;
    }

    public void setDirection(final String value) {
      direction = value;
    }

    public Date getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(final Date value) {
      timestamp = value;
    }

    public BigDecimal getFee() {
      return fee;
    }

    public void setFee(final BigDecimal value) {
      fee = value;
    }

    public String getResult() {
      return result;
    }

    public void setResult(final String value) {
      result = value;
    }

    public List<RippleAmount> getBalanceChanges() {
      return balanceChanges;
    }

    public void setBalanceChanges(final List<RippleAmount> value) {
      balanceChanges = value;
    }

    public List<RippleAmount> getSourceBalanceChanges() {
      return sourceBalanceChanges;
    }

    public void setSourceBalanceChanges(final List<RippleAmount> value) {
      sourceBalanceChanges = value;
    }

    public List<RippleAmount> getDestinationBalanceChanges() {
      return destinationBalanceChanges;
    }

    public void setDestinationBalanceChanges(final List<RippleAmount> value) {
      destinationBalanceChanges = value;
    }

    public List<OrderChange> getOrderChanges() {
      return orderChanges;
    }

    public void setOrderChanges(final List<OrderChange> value) {
      orderChanges = value;
    }
  }
}
