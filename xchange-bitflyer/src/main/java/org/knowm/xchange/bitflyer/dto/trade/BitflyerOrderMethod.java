package org.knowm.xchange.bitflyer.dto.trade;

public enum BitflyerOrderMethod {
  SIMPLE, // plain old market or limit order
  IFD, // if first order is filled, place the second
  OCO, // if one of two orders is filled, cancel the other one
  IFDOCO // after IFD order is filled, place an OCO order
}
