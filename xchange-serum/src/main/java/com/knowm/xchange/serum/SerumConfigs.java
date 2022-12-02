package com.knowm.xchange.serum;

public class SerumConfigs {

  /**
   * The level of commitment desired when querying state
   *
   * <p>'max': Query the most recent block which has been finalized by the cluster 'recent': Query
   * the most recent block which has reached 1 confirmation by the connected node 'root': Query the
   * most recent block which has been rooted by the connected node 'single': Query the most recent
   * block which has reached 1 confirmation by the cluster 'singleGossip': Query the most recent
   * block which has reached 1 confirmation according to votes seen in gossip
   *
   * <p>For more https://docs.solana.com/developing/clients/jsonrpc-api#configuring-state-commitment
   */
  public enum Commitment {
    max,
    recent,
    root,
    single,
    singleGossip
  }

  public enum SubscriptionType {
    accountSubscribe,
    programSubscribe,
    slotSubscribe,
    rootSubscribe,
    signatureSubscribe
  }

  public enum Solana {
    MAINNET("api.mainnet-beta.solana.com"),
    TESTNET("testnet.solana.com"),
    DEVNET("devnet.solana.com");

    private final String url;

    Solana(final String url) {
      this.url = url;
    }

    public String wsUrl() {
      return "wss://" + url;
    }

    public String restUrl() {
      return "http://" + url;
    }
  }

  public static final String environment = "Env";
}
