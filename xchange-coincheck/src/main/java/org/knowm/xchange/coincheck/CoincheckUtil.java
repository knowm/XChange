package org.knowm.xchange.coincheck;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoincheckUtil {
  public static <T> Optional<T> getArg(Object[] args, Class<T> desired) {
    if (args == null || args.length == 0) {
      return Optional.empty();
    }
    return Stream.of(args)
        .filter(a -> a != null)
        .filter(a -> desired.isAssignableFrom(a.getClass()))
        .map(a -> (T) a)
        .findFirst();
  }
}
