package org.knowm.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class OpenPosition extends Order {

    public OpenPosition(
            OrderType type,
            BigDecimal originalAmount,
            CurrencyPair currencyPair,
            String id,
            Date timestamp,
            BigDecimal averagePrice,
            BigDecimal fee) {
        super(type, originalAmount, currencyPair, id, timestamp);
        setAveragePrice(averagePrice);
        setFee(fee);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder extends Order.Builder{

        @JsonCreator
        public Builder(
                @JsonProperty("orderType") OrderType orderType,
                @JsonProperty("currencyPair") CurrencyPair currencyPair) {

            super(orderType, currencyPair);
        }

        @Override
        public OpenPosition.Builder orderType(OrderType orderType) {

            return (OpenPosition.Builder) super.orderType(orderType);
        }

        @Override
        public OpenPosition.Builder originalAmount(BigDecimal originalAmount) {

            return (OpenPosition.Builder) super.originalAmount(originalAmount);
        }

        @Override
        public OpenPosition.Builder cumulativeAmount(BigDecimal originalAmount) {

            return (OpenPosition.Builder) super.cumulativeAmount(originalAmount);
        }

        @Override
        public OpenPosition.Builder currencyPair(CurrencyPair currencyPair) {

            return (OpenPosition.Builder) super.currencyPair(currencyPair);
        }

        @Override
        public OpenPosition.Builder id(String id) {

            return (OpenPosition.Builder) super.id(id);
        }

        @Override
        public OpenPosition.Builder userReference(String userReference) {

            return (OpenPosition.Builder) super.userReference(userReference);
        }

        @Override
        public OpenPosition.Builder timestamp(Date timestamp) {

            return (OpenPosition.Builder) super.timestamp(timestamp);
        }

        @Override
        public OpenPosition.Builder averagePrice(BigDecimal averagePrice) {

            return (OpenPosition.Builder) super.averagePrice(averagePrice);
        }

        @Override
        public OpenPosition.Builder flag(IOrderFlags flag) {

            return (OpenPosition.Builder) super.flag(flag);
        }

        @Override
        public OpenPosition.Builder flags(Set<IOrderFlags> flags) {

            return (OpenPosition.Builder) super.flags(flags);
        }

        @Override
        public OpenPosition.Builder fee(BigDecimal fee) {
            return (OpenPosition.Builder) super.fee(fee);
        }


        @Override
        public OpenPosition build() {

            return new OpenPosition(
                    orderType,
                    originalAmount,
                    currencyPair,
                    id,
                    timestamp,
                    averagePrice,
                    fee
            );
        }
    }
}