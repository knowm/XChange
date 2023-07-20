package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigInteger;
import lombok.Getter;
import lombok.ToString;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"price", "quantity"})
@Getter
@ToString
public class PriceAndQuantity {

  BigInteger price;

  BigInteger quantity;

  public PriceAndQuantity(@JsonProperty("price") BigInteger price, @JsonProperty("quantity") BigInteger quantity) {
    this.price = price;
    this.quantity = quantity;
  }
}
