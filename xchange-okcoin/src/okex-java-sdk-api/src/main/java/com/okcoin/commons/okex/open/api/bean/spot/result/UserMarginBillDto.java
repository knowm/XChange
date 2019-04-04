package com.okcoin.commons.okex.open.api.bean.spot.result;

public class UserMarginBillDto {
    private Long ledger_id;
    private String timestamp;
    private String amount;
    private String balance;
    private String type;
    private UserMarginBillDto.Details details;

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getLedger_id() {
        return this.ledger_id;
    }

    public void setLedger_id(final Long ledger_id) {
        this.ledger_id = ledger_id;
    }


    public String getAmount() {
        return this.amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(final String balance) {
        this.balance = balance;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public UserMarginBillDto.Details getDetails() {
        return this.details;
    }

    public void setDetails(final UserMarginBillDto.Details details) {
        this.details = details;
    }

    public static class Details {
        private Long order_id;
        private String instrument_id;

        public Long getOrder_id() {
            return this.order_id;
        }

        public void setOrder_id(final Long order_id) {
            this.order_id = order_id;
        }

        public String getInstrument_id() {
            return this.instrument_id;
        }

        public void setInstrument_id(final String instrument_id) {
            this.instrument_id = instrument_id;
        }
    }
}
