package com.knowm.xchange.serum.service;

import static com.knowm.xchange.serum.core.PublicKeys.WRAPPED_SOL_MINT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.knowm.xchange.serum.SerumBaseService;
import com.knowm.xchange.serum.SerumConfigs.Commitment;
import com.knowm.xchange.serum.core.Market;
import com.knowm.xchange.serum.core.MarketOptions;
import com.knowm.xchange.serum.dto.PublicKey;
import com.knowm.xchange.serum.dto.SolanaResponse;
import com.knowm.xchange.serum.dto.SolanaValue;
import com.knowm.xchange.serum.structures.AccountFlagsLayout.AccountFlags;
import com.knowm.xchange.serum.structures.MarketLayout;
import com.knowm.xchange.serum.structures.MarketStat;
import com.knowm.xchange.serum.structures.MintLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;

/**
 * This class borrows logic heavily from the Serum foundation's codebase:
 *
 * <p>https://github.com/project-serum/serum-ts/blob/master/packages/serum/src/market.ts
 */
public class SerumMarketDataServiceRaw extends SerumBaseService {

  private static final ObjectMapper mapper = new ObjectMapper();

  public SerumMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public int getMintDecimals(final PublicKey mint) throws IOException {
    if (mint.equals(WRAPPED_SOL_MINT)) {
      return 9;
    }
    final SolanaValue result = getAccountInfoResult(mint).result.value;
    final MintLayout layout = MintLayout.DECODER.decode(result.data[0]);
    return layout.decimals & 0xFF;
  }

  /**
   * Loads a full qualified market from Serum. A market is analogous to a trading pair. This is
   * Serum's nomenclature for trading pairs.
   *
   * @param address tied to market
   * @param options for request
   * @param programId market is running on
   * @return market
   */
  public Market load(PublicKey address, MarketOptions options, PublicKey programId)
      throws Exception {

    final SolanaValue result = getAccountInfoResult(address).result.value;
    if (!result.owner.equals(programId)) {
      throw new Exception("Address not owned by program: " + result.owner.getKeyString());
    }

    final MarketStat m = MarketLayout.getLayout(programId).decode(result.data[0]);
    final AccountFlags accountFlags = m.getAccountFlags();
    if (!accountFlags.initialized || !accountFlags.market || !m.getOwnAddress().equals(address)) {
      throw new Error("Invalid market");
    }
    final int baseMint = getMintDecimals(m.baseMint());
    final int quoteMint = getMintDecimals(m.quoteMint());

    return new Market(m, baseMint, quoteMint, options, programId);
  }

  /**
   * Overloaded method for account info
   *
   * @param address to load account info for
   * @return account information associated
   */
  private SolanaResponse getAccountInfoResult(final PublicKey address) throws IOException {
    return getAccountInfoResult(address, null);
  }

  /**
   * Method for loading account info tied to address
   *
   * @param address to load account info for
   * @param commitment level of responses, explore class for more detail
   * @return information associated with account
   */
  private SolanaResponse getAccountInfoResult(final PublicKey address, final Commitment commitment)
      throws IOException {
    final String publicKey = address.getKeyString();
    final List<Object> argsList = new ArrayList<>();
    argsList.add(publicKey);

    final Object args = buildArgs(argsList, commitment, "base64");

    final JsonNode payload = mapper.convertValue(args, JsonNode.class);

    final ObjectNode request = JsonNodeFactory.instance.objectNode();
    request.put("method", "getAccountInfo");
    request.put("jsonrpc", "2.0");
    request.put("id", 1);
    request.set("params", payload);

    return serum.getAccountInfo(request);
  }

  private List<Object> buildArgs(
      final List<Object> args, final Commitment override, final String encoding) {

    final Map<String, String> resultArgs = new HashMap<>();

    final Commitment commitment = override == null ? Commitment.max : override;
    if (commitment != null) {
      resultArgs.put("commitment", commitment.name());
    }

    if (encoding != null) {
      resultArgs.put("encoding", encoding);
    }
    args.add(resultArgs);
    return args;
  }
}
