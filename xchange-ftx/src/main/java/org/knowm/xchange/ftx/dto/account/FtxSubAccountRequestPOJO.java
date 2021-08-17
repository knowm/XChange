package org.knowm.xchange.ftx.dto.account;

public class FtxSubAccountRequestPOJO {

  private String nickname;

  public FtxSubAccountRequestPOJO(String nickname) {
    this.nickname = nickname;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  @Override
  public String toString() {
    return "FtxSubAccountPOJO{" + "nickname='" + nickname + '\'' + '}';
  }
}
