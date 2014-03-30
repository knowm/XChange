package com.xeiam.xchange.atlasats.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AtlasAccountInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "account")
	private String accountNumber;

	@JsonProperty(value = "exposure")
	private BigDecimal exposure;

	@JsonProperty(value = "unrealizedpl")
	private BigDecimal unrealizedProfit;

	@JsonProperty(value = "margindebit")
	private BigDecimal marginDebit;

	@JsonProperty(value = "netvalue")
	private BigDecimal netValue;

	@JsonProperty(value = "marginused")
	private BigDecimal marginUsed;

	@JsonProperty(value = "withdrawcash")
	private BigDecimal withdrawableCash;

	@JsonProperty(value = "leverage")
	private BigDecimal leverage;

	@JsonProperty(value = "totalpl")
	private BigDecimal totalProfit;

	@JsonProperty(value = "currency")
	private Currency currency;

	@JsonProperty(value = "positions")
	private List<AtlasPosition> positions;

	@JsonProperty(value = "balance")
	private BigDecimal balance;

	@JsonProperty(value = "realizedpl")
	private BigDecimal realizedProfit;

	@JsonProperty(value = "buyingpower")
	private BigDecimal buyingPower;

	@JsonProperty(value = "orders")
	private List<AtlasOrderId> orderIds;

	@JsonProperty(value = "commission")
	private BigDecimal commission;

	@JsonProperty(value = "marginavail")
	private BigDecimal marginAvailable;

	public String getAccountNumber() {
		return accountNumber;
	}

	public BigDecimal getExposure() {
		return exposure;
	}

	public BigDecimal getUnrealizedProfit() {
		return unrealizedProfit;
	}

	public BigDecimal getMarginDebit() {
		return marginDebit;
	}

	public BigDecimal getNetValue() {
		return netValue;
	}

	public BigDecimal getMarginUsed() {
		return marginUsed;
	}

	public BigDecimal getWithdrawableCash() {
		return withdrawableCash;
	}

	public BigDecimal getLeverage() {
		return leverage;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public Currency getCurrency() {
		return currency;
	}

	public List<AtlasPosition> getPositions() {
		return positions;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal getRealizedProfit() {
		return realizedProfit;
	}

	public BigDecimal getBuyingPower() {
		return buyingPower;
	}

	public List<AtlasOrderId> getOrderIds() {
		return orderIds;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public BigDecimal getMarginAvailable() {
		return marginAvailable;
	}

	protected void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	protected void setExposure(BigDecimal exposure) {
		this.exposure = exposure;
	}

	protected void setUnrealizedProfit(BigDecimal unrealizedProfit) {
		this.unrealizedProfit = unrealizedProfit;
	}

	protected void setMarginDebit(BigDecimal marginDebit) {
		this.marginDebit = marginDebit;
	}

	protected void setNetValue(BigDecimal netValue) {
		this.netValue = netValue;
	}

	protected void setMarginUsed(BigDecimal marginUsed) {
		this.marginUsed = marginUsed;
	}

	protected void setWithdrawableCash(BigDecimal withdrawableCash) {
		this.withdrawableCash = withdrawableCash;
	}

	protected void setLeverage(BigDecimal leverage) {
		this.leverage = leverage;
	}

	protected void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	protected void setCurrency(Currency currency) {
		this.currency = currency;
	}

	protected void setPositions(List<AtlasPosition> positions) {
		this.positions = positions;
	}

	protected void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	protected void setRealizedProfit(BigDecimal realizedProfit) {
		this.realizedProfit = realizedProfit;
	}

	protected void setBuyingPower(BigDecimal buyingPower) {
		this.buyingPower = buyingPower;
	}

	protected void setOrderIds(List<AtlasOrderId> orderIds) {
		this.orderIds = orderIds;
	}

	protected void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	protected void setMarginAvailable(BigDecimal marginAvailable) {
		this.marginAvailable = marginAvailable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtlasAccountInfo [accountNumber=");
		builder.append(accountNumber);
		builder.append(", exposure=");
		builder.append(exposure);
		builder.append(", unrealizedProfit=");
		builder.append(unrealizedProfit);
		builder.append(", marginDebit=");
		builder.append(marginDebit);
		builder.append(", netValue=");
		builder.append(netValue);
		builder.append(", marginUsed=");
		builder.append(marginUsed);
		builder.append(", withdrawableCash=");
		builder.append(withdrawableCash);
		builder.append(", leverage=");
		builder.append(leverage);
		builder.append(", totalProfit=");
		builder.append(totalProfit);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", positions=");
		builder.append(positions);
		builder.append(", balance=");
		builder.append(balance);
		builder.append(", realizedProfit=");
		builder.append(realizedProfit);
		builder.append(", buyingPower=");
		builder.append(buyingPower);
		builder.append(", orderIds=");
		builder.append(orderIds);
		builder.append(", commission=");
		builder.append(commission);
		builder.append(", marginAvailable=");
		builder.append(marginAvailable);
		builder.append("]");
		return builder.toString();
	}

	
}
