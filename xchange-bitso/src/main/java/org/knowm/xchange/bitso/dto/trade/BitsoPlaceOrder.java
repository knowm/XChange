package org.knowm.xchange.bitso.dto.trade;

public class BitsoPlaceOrder {

	private String book;
	private String side;
	private String type;
	private String major;
	private String price;

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "BitsoPlaceOrder [book=" + book + ", side=" + side + ", type=" + type + ", major=" + major + ", price="
				+ price + "]";
	}

}
