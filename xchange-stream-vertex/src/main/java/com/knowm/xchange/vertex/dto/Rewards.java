package com.knowm.xchange.vertex.dto;


import lombok.Getter;
import lombok.ToString;

/*
  Uptime is in minutes, and volumes & fees are in USD
 */
@ToString
@Getter
public class Rewards {

  private int epoch;
  private long start_time;

  private long period;
  private AddressRewards[] address_rewards;
  private GlobalRewards[] global_rewards;


}
