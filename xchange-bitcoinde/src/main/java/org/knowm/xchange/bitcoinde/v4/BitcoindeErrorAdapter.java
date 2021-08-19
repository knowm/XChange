package org.knowm.xchange.bitcoinde.v4;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeError;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.exceptions.*;

public final class BitcoindeErrorAdapter {

  private static final List<ExceptionTranslation> TRANSLATIONS = new LinkedList<>();

  static {
    TRANSLATIONS.add(
        new ExceptionTranslation(
            (error, exception) ->
                new NonceException(error.toString() + ", Last Nonce: " + exception.getNonce()),
            4));

    TRANSLATIONS.add(
        new ExceptionTranslation(
            (error, exception) ->
                new RateLimitExceededException(
                    error.toString() + ", Credits: " + exception.getCredits()),
            6));

    TRANSLATIONS.add(
        new ExceptionTranslation(
            (error, exception) -> new ExchangeSecurityException(error.toString()),
            2, // Inactive api key
            3, // Invalid api key
            5, // Invalid signature
            9, // Additional agreement not accepted
            10, //	No 2 factor authentication
            11, //	No beta group user
            14, // No action permission for api key
            32, //	Api key banned
            33, //	Ip banned
            94 //	Ip access restricted
            ));

    TRANSLATIONS.add(
        new ExceptionTranslation(
            (error, exception) -> new NotAvailableFromExchangeException(error.toString()),
            7, // Invalid route
            8 // Unkown api action
            ));
  }

  private BitcoindeErrorAdapter() {}

  public static RuntimeException adaptBitcoindeException(final BitcoindeException exception) {
    for (ExceptionTranslation translation : TRANSLATIONS) {
      final Optional<BitcoindeError> error =
          getErrorCode(exception.getErrors(), translation.getErrorCodes());
      if (error.isPresent()) {
        return translation.provider.provide(error.get(), exception);
      }
    }

    return new ExchangeException(exception.getMessage(), exception);
  }

  private static Optional<BitcoindeError> getErrorCode(
      final BitcoindeError[] errors, final int[] errorCodes) {
    return Arrays.stream(errors)
        .filter(
            error -> Arrays.stream(errorCodes).anyMatch(errorCode -> error.getCode() == errorCode))
        .findFirst();
  }

  @Getter
  @EqualsAndHashCode
  @ToString
  private static final class ExceptionTranslation {
    private final ExceptionProvider provider;
    private final int[] errorCodes;

    public ExceptionTranslation(final ExceptionProvider provider, final int... errorCodes) {
      this.provider = provider;
      this.errorCodes = errorCodes;
    }
  }

  @FunctionalInterface
  private interface ExceptionProvider {
    RuntimeException provide(BitcoindeError error, BitcoindeException exception);
  }
}
