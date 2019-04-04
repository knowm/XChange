package com.okcoin.commons.okex.open.api.bean.ett.result;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttOrder {

    private String order_id;
    private String created_at;
    private Integer type;
    private String ett;
    private String quote_currency;
    private BigDecimal amount;
    private BigDecimal size;
    private BigDecimal price;
    private String status;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEtt() {
        return ett;
    }

    public void setEtt(String ett) {
        this.ett = ett;
    }

    public String getQuote_currency() {
        return quote_currency;
    }

    public void setQuote_currency(String quote_currency) {
        this.quote_currency = quote_currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        EttOrder ettOrder = (EttOrder)o;
        return Objects.equals(order_id, ettOrder.order_id) &&
            Objects.equals(created_at, ettOrder.created_at) &&
            Objects.equals(type, ettOrder.type) &&
            Objects.equals(ett, ettOrder.ett) &&
            Objects.equals(quote_currency, ettOrder.quote_currency) &&
            Objects.equals(amount, ettOrder.amount) &&
            Objects.equals(size, ettOrder.size) &&
            Objects.equals(price, ettOrder.price) &&
            Objects.equals(status, ettOrder.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, created_at, type, ett, quote_currency, amount, size, price, status);
    }

    @Override
    public String toString() {
        return "EttOrder{" +
            "order_id='" + order_id + '\'' +
            ", created_at=" + created_at +
            ", type=" + type +
            ", ett='" + ett + '\'' +
            ", quote_currency='" + quote_currency + '\'' +
            ", amount=" + amount +
            ", size=" + size +
            ", price=" + price +
            ", status='" + status + '\'' +
            '}';
    }
}
