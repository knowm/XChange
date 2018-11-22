package org.knowm.xchange.kuna.dto;

public class KunaAccountDto {

  private String email;

  private Boolean activated;

  private KunaAccountItemDto[] accounts;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getActivated() {
    return activated;
  }

  public void setActivated(Boolean activated) {
    this.activated = activated;
  }

  public KunaAccountItemDto[] getAccounts() {
    return accounts;
  }

  public void setAccounts(KunaAccountItemDto[] accounts) {
    this.accounts = accounts;
  }
}
