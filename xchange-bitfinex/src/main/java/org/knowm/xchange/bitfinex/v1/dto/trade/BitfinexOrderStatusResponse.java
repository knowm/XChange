package org.knowm.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class BitfinexOrderStatusResponse extends Order {

	private final String id;
	private final String symbol;
	private final String exchange;
	private final BigDecimal price;
	private final BigDecimal avgExecutionPrice;
	private final String side;
	private final String type;
	private final long timestamp;
	private final boolean isLive;
	private final boolean isCancelled;
	private final boolean wasForced;
	private final BigDecimal originalAmount;
	private final BigDecimal remainingAmount;
	private final BigDecimal executedAmount;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param symbol
	 * @param exchange
	 * @param price
	 * @param avgExecutionPrice
	 * @param side
	 * @param type
	 * @param timestamp
	 * @param isLive
	 * @param isCancelled
	 * @param wasForced
	 * @param originalAmount
	 * @param remainingAmount
	 * @param executedAmount
	 */
	public BitfinexOrderStatusResponse(@JsonProperty("id") String id, @JsonProperty("symbol") String symbol,
			@JsonProperty("exchange") String exchange, @JsonProperty("price") BigDecimal price,
			@JsonProperty("avg_execution_price") BigDecimal avgExecutionPrice, @JsonProperty("side") String side,
			@JsonProperty("type") String type, @JsonProperty("timestamp") long timestamp,
			@JsonProperty("is_live") boolean isLive, @JsonProperty("is_cancelled") boolean isCancelled,
			@JsonProperty("was_forced") boolean wasForced, @JsonProperty("original_amount") BigDecimal originalAmount,
			@JsonProperty("remaining_amount") BigDecimal remainingAmount,
			@JsonProperty("executed_amount") BigDecimal executedAmount) {

		super(side == "buy" ? OrderType.BID : OrderType.ASK, originalAmount,
				new CurrencyPair(symbol.substring(0, 3), symbol.substring(3, 6)), id, new Date(timestamp),
				avgExecutionPrice, executedAmount,
				isCancelled ? OrderStatus.CANCELED
						: remainingAmount != BigDecimal.ZERO ? OrderStatus.PARTIALLY_FILLED
								: executedAmount.equals(originalAmount) ? OrderStatus.FILLED : OrderStatus.PENDING_NEW);

		this.id = id;
		this.symbol = symbol;
		this.exchange = exchange;
		this.price = price;
		this.avgExecutionPrice = avgExecutionPrice;
		this.side = side;
		this.type = type;
		this.timestamp = timestamp;
		this.isLive = isLive;
		this.isCancelled = isCancelled;
		this.wasForced = wasForced;
		this.originalAmount = originalAmount;
		this.remainingAmount = remainingAmount;
		this.executedAmount = executedAmount;
	}

	public BigDecimal getExecutedAmount() {

		return executedAmount;
	}

	public BigDecimal getRemainingAmount() {

		return remainingAmount;
	}

	public BigDecimal getOriginalAmount() {

		return originalAmount;
	}

	public boolean getWasForced() {

		return wasForced;
	}

	public String getExchange() {

		return exchange;
	}

	public OrderType getType() {

		return OrderType.valueOf(side);
	}

	public String getOrderType() {

		return type;
	}

	public String getSymbol() {

		return symbol;
	}

	public boolean isCancelled() {

		return isCancelled;
	}

	public BigDecimal getPrice() {

		return price;
	}

	public String getSide() {

		return side;
	}

	public Date getTimestamp() {

		return new Date(timestamp);
	}

	public String getId() {

		return id;
	}

	public boolean isLive() {

		return isLive;
	}

	public BigDecimal getAvgExecutionPrice() {

		return avgExecutionPrice;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("BitfinexOrderStatusResponse [id=");
		builder.append(id);
		builder.append(", symbol=");
		builder.append(symbol);
		builder.append(", exchange=");
		builder.append(exchange);
		builder.append(", price=");
		builder.append(price);
		builder.append(", avgExecutionPrice=");
		builder.append(avgExecutionPrice);
		builder.append(", side=");
		builder.append(side);
		builder.append(", type=");
		builder.append(type);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", isLive=");
		builder.append(isLive);
		builder.append(", isCancelled=");
		builder.append(isCancelled);
		builder.append(", wasForced=");
		builder.append(wasForced);
		builder.append(", originalAmount=");
		builder.append(originalAmount);
		builder.append(", remainingAmount=");
		builder.append(remainingAmount);
		builder.append(", executedAmount=");
		builder.append(executedAmount);
		builder.append("]");
		return builder.toString();
	}
}
