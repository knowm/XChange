package org.knowm.xchange.bibox.dto;

/**
 * @param <B> type of body parameter
 * @author odrotleff
 */
public class BiboxCommand<B> {

  private String cmd;
  private B body;

  public BiboxCommand(String cmd, B body) {
    super();
    this.cmd = cmd;
    this.body = body;
  }

  public String getCmd() {
    return cmd;
  }

  public B getBody() {
    return body;
  }
}
