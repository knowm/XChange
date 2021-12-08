package org.knowm.xchange.raydium;

import io.github.makarid.solanaj.core.PublicKey;

public class RaydiumAdapter {

  private static final PublicKey SYSTEM_PROGRAM_ID =
      new PublicKey("11111111111111111111111111111111");
  private static final PublicKey TOKEN_PROGRAM_ID =
      new PublicKey("TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA");
  private static final PublicKey MEMO_PROGRAM_ID =
      new PublicKey("Memo1UhkJRfHyvLMcVucJwxXeuD728EqVDDwQDxFMNo");
  private static final PublicKey RENT_PROGRAM_ID =
      new PublicKey("SysvarRent111111111111111111111111111111111");
  private static final PublicKey CLOCK_PROGRAM_ID =
      new PublicKey("SysvarC1ock11111111111111111111111111111111");
  private static final PublicKey ASSOCIATED_TOKEN_PROGRAM_ID =
      new PublicKey("ATokenGPvbdGVxr1b2hvZbsiqW5xWH25efTNsLJA8knL");

  private static final PublicKey SERUM_PROGRAM_ID_V2 =
      new PublicKey("EUqojwWA2rd19FZrzeBncJsm38Jm1hEhE3zsmX3bRc2o");
  private static final PublicKey SERUM_PROGRAM_ID_V3 =
      new PublicKey("9xQeWvG816bUx9EPjHmaT23yvVM2ZWbrrpZb9PusVFin");

  private static final PublicKey LIQUIDITY_POOL_PROGRAM_ID_V2 =
      new PublicKey("RVKd61ztZW9GUwhRbbLoYVRE5Xf1B2tVscKqwZqXgEr");
  private static final PublicKey LIQUIDITY_POOL_PROGRAM_ID_V3 =
      new PublicKey("27haf8L6oxUeXrHrgEgsexjSY5hbVUWEmvv9Nyxg8vQv");
  private static final PublicKey LIQUIDITY_POOL_PROGRAM_ID_V4 =
      new PublicKey("675kPX9MHTjS2zt1qfr1NYHuzeLXfQM9H24wFSUt1Mp8");

  private static final PublicKey STABLE_POOL_PROGRAM_ID =
      new PublicKey("5quBtoiQqxF9Jv6KYKctB59NT3gtJD2Y65kdnB1Uev3h");

  private static final PublicKey ROUTE_SWAP_PROGRAM_ID =
      new PublicKey("93BgeoLHo5AdNbpqy9bD12dtfxtA5M2fh3rj72bE35Y3");

  private static final PublicKey STAKE_PROGRAM_ID =
      new PublicKey("EhhTKczWMGQt46ynNeRX1WfeagwwJd7ufHvCDjRxjo5Q");
  private static final PublicKey STAKE_PROGRAM_ID_V4 =
      new PublicKey("CBuCnLe26faBpcBP2fktp4rp8abpcAnTWft6ZrP5Q4T");
  private static final PublicKey STAKE_PROGRAM_ID_V5 =
      new PublicKey("9KEPoZmtHUrBbhWN1v1KWLMkkvwY6WLtAVUCPRtRjP4z");

  private static final PublicKey IDO_PROGRAM_ID =
      new PublicKey("6FJon3QE27qgPVggARueB22hLvoh22VzJpXv4rBEoSLF");
  private static final PublicKey IDO_PROGRAM_ID_V2 =
      new PublicKey("CC12se5To1CdEuw7fDS27B7Geo5jJyL7t5UK2B44NgiH");
  private static final PublicKey IDO_PROGRAM_ID_V3 =
      new PublicKey("9HzJyW1qZsEiSfMUf6L2jo3CcTKAyBmSyKdwQeYisHrC");
}
