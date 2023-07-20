package com.knowm.xchange.vertex.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class RewardsRequest {


  private final RewardAddress rewards;

  public RewardsRequest(RewardAddress rewards) {
    this.rewards = rewards;
  }

  @ToString
  @Getter
  public static class RewardAddress {

    private final String address;

    public RewardAddress(String address) {
      this.address = address;
    }
  }
}
