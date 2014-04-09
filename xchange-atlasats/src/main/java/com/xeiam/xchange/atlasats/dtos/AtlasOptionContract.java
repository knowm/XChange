package com.xeiam.xchange.atlasats.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class AtlasOptionContract implements Serializable {

	public enum Type {
		CALL, PUT;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger id;

	private String description;

	private AtlasCurrencyPair currencyPair;

	private Type type;

	private Date expiration;

	private BigInteger strikePrice;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AtlasCurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(AtlasCurrencyPair currencyPair) {
		this.currencyPair = currencyPair;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public BigInteger getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(BigInteger strikePrice) {
		this.strikePrice = strikePrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currencyPair == null) ? 0 : currencyPair.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((expiration == null) ? 0 : expiration.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((strikePrice == null) ? 0 : strikePrice.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AtlasOptionContract)) {
			return false;
		}
		AtlasOptionContract other = (AtlasOptionContract) obj;
		if (currencyPair == null) {
			if (other.currencyPair != null) {
				return false;
			}
		} else if (!currencyPair.equals(other.currencyPair)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (expiration == null) {
			if (other.expiration != null) {
				return false;
			}
		} else if (!expiration.equals(other.expiration)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (strikePrice == null) {
			if (other.strikePrice != null) {
				return false;
			}
		} else if (!strikePrice.equals(other.strikePrice)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtlasOptionContract [id=");
		builder.append(id);
		builder.append(", description=");
		builder.append(description);
		builder.append(", currencyPair=");
		builder.append(currencyPair);
		builder.append(", type=");
		builder.append(type);
		builder.append(", expiration=");
		builder.append(expiration);
		builder.append(", strikePrice=");
		builder.append(strikePrice);
		builder.append("]");
		return builder.toString();
	}

}
