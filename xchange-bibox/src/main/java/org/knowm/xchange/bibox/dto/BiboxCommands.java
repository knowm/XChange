package org.knowm.xchange.bibox.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.bibox.dto.account.BiboxDepositAddressCommandBody;

/** @author odrotleff */
public class BiboxCommands extends ArrayList<BiboxCommand<?>> {

  public static final BiboxCommands COIN_LIST_CMD =
      BiboxCommands.of(new BiboxCommand<BiboxEmptyBody>("transfer/coinList", new BiboxEmptyBody()));
  private static final ObjectMapper MAPPER =
      new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  private static final long serialVersionUID = 1L;

  private BiboxCommands() {
    super();
  }

  public static BiboxCommands depositAddressCommand(String coinSymbol) {
    return BiboxCommands.of(
        new BiboxCommand<BiboxDepositAddressCommandBody>(
            "transfer/transferIn", new BiboxDepositAddressCommandBody(coinSymbol)));
  }

  public static BiboxCommands of(List<BiboxCommand<?>> commands) {
    BiboxCommands cmds = new BiboxCommands();
    cmds.addAll(commands);
    return cmds;
  }

  public static BiboxCommands of(BiboxCommand<?>... commands) {
    BiboxCommands cmds = new BiboxCommands();
    cmds.addAll(Arrays.asList(commands));
    return cmds;
  }

  public String json() {
    try {
      return MAPPER.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
