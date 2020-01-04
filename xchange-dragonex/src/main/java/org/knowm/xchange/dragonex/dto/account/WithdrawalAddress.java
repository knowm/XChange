package org.knowm.xchange.dragonex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class WithdrawalAddress {

  public final long addressId;
  public final long uid;
  public final long coinId;
  public final String name;
  public final String address;
  public final long status;
  public final long createTime;
  public final long updateTime;
  public final String tag;
  public final String tagName;

  public WithdrawalAddress(
      @JsonProperty("addr_id") long addressId,
      @JsonProperty("uid") long uid,
      @JsonProperty("coin_id") long coinId,
      @JsonProperty("name") String name,
      @JsonProperty("addr") String address,
      @JsonProperty("status") long status,
      @JsonProperty("create_time") long createTime,
      @JsonProperty("update_time") long updateTime,
      @JsonProperty("tag") String tag,
      @JsonProperty("tag_name") String tagName) {
    super();
    this.addressId = addressId;
    this.uid = uid;
    this.coinId = coinId;
    this.name = name;
    this.address = address;
    this.status = status;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.tag = tag;
    this.tagName = tagName;
  }

  public Date getCreateTime() {
    return new Date(createTime * 1000);
  }

  public Date getUpdateTime() {
    return new Date(updateTime * 1000);
  }

  @Override
  public String toString() {
    return "WithdrawalAddress [addressId="
        + addressId
        + ", uid="
        + uid
        + ", coinId="
        + coinId
        + ", "
        + (name != null ? "name=" + name + ", " : "")
        + (address != null ? "address=" + address + ", " : "")
        + "status="
        + status
        + ", createTime="
        + createTime
        + ", updateTime="
        + updateTime
        + ", "
        + (tag != null ? "tag=" + tag + ", " : "")
        + (tagName != null ? "tagName=" + tagName + ", " : "")
        + (getCreateTime() != null ? "getCreateTime()=" + getCreateTime() + ", " : "")
        + (getUpdateTime() != null ? "getUpdateTime()=" + getUpdateTime() : "")
        + "]";
  }
}
