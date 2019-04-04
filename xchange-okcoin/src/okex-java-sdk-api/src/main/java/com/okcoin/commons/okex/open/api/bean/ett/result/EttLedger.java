package com.okcoin.commons.okex.open.api.bean.ett.result;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttLedger {

    private Long ledger_id;
    private String currency;
    private BigDecimal balance;
    private BigDecimal amount;
    private String type;
    private String created_at;
    private Long details;

    public Long getLedger_id() {
        return ledger_id;
    }

    public void setLedger_id(Long ledger_id) {
        this.ledger_id = ledger_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Long getDetails() {
        return details;
    }

    public void setDetails(Long details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        EttLedger ettLedger = (EttLedger)o;
        return Objects.equals(ledger_id, ettLedger.ledger_id) &&
            Objects.equals(currency, ettLedger.currency) &&
            Objects.equals(balance, ettLedger.balance) &&
            Objects.equals(amount, ettLedger.amount) &&
            Objects.equals(type, ettLedger.type) &&
            Objects.equals(created_at, ettLedger.created_at) &&
            Objects.equals(details, ettLedger.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ledger_id, currency, balance, amount, type, created_at, details);
    }

    @Override
    public String toString() {
        return "EttLedger{" +
            "ledger_id='" + ledger_id + '\'' +
            ", currency='" + currency + '\'' +
            ", balance=" + balance +
            ", amount=" + amount +
            ", type='" + type + '\'' +
            ", created_at=" + created_at +
            ", details='" + details + '\'' +
            '}';
    }
}
