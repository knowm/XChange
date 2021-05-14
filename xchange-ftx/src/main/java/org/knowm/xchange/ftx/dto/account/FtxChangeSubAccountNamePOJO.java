package org.knowm.xchange.ftx.dto.account;

public class FtxChangeSubAccountNamePOJO {

  private String nickname;
  private String newNickname;

  public FtxChangeSubAccountNamePOJO(String nickname, String newNickname) {
    this.nickname = nickname;
    this.newNickname = newNickname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getNewNickname() {
    return newNickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setNewNickname(String newNickname) {
    this.newNickname = newNickname;
  }

  @Override
  public String toString() {
    return "FtxChangeSubAccountNamePOJO{"
        + "nickname='"
        + nickname
        + '\''
        + ", newNickname='"
        + newNickname
        + '\''
        + '}';
  }
}
