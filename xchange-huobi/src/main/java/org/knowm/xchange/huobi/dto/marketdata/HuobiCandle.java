package org.knowm.xchange.huobi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class HuobiCandle {

	private long id;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal amount;
	private Double vol;
	private long count;

	public HuobiCandle(@JsonProperty("id") long id, @JsonProperty("open") BigDecimal open,
			@JsonProperty("close") BigDecimal close, @JsonProperty("low") BigDecimal low,
			@JsonProperty("high") BigDecimal high, @JsonProperty("amount") BigDecimal amount,
			@JsonProperty("vol") Double vol, @JsonProperty("count") long count) {

		this.id = id;
		this.open = open;
		this.close = close;
		this.low = low;
		this.high = high;
		this.amount = amount;
		this.vol = vol;
		this.count = count;

	}

	public long getId() {
		return id;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public BigDecimal getClose() {
		return close;
	}

	public BigDecimal getLow() {
		return low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Double getVol() {
		return vol;
	}

	public long getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "HuobiCandle [id=" + id + ", open=" + open + ", close=" + close + ", low=" + low + ", high=" + high
				+ ", amount=" + amount + ", vol=" + vol + ", count=" + count + "]";
	}

}
