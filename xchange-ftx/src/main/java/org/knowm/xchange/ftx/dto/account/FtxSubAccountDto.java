package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxSubAccountDto {

  private final String nickname;

  private final String deletable;

  private final String editable;

  @JsonCreator
  public FtxSubAccountDto(
      @JsonProperty("nickname") String nickname,
      @JsonProperty("deletable") String deletable,
      @JsonProperty("editable") String editable) {
    this.nickname = nickname;
    this.deletable = deletable;
    this.editable = editable;
  }

  public String getNickname() {
    return nickname;
  }

  public String getDeletable() {
    return deletable;
  }

  public String getEditable() {
    return editable;
  }

  @Override
  public String toString() {
    return "FtxSubAccountDto{"
        + "nickname='"
        + nickname
        + '\''
        + ", deletable='"
        + deletable
        + '\''
        + ", editable='"
        + editable
        + '\''
        + '}';
  }
}
