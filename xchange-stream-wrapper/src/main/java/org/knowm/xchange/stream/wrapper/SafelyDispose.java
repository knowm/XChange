package org.knowm.xchange.stream.wrapper;

import io.reactivex.disposables.Disposable;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class SafelyDispose {

  private static final Logger LOGGER = LoggerFactory.getLogger(SafelyDispose.class);

  private SafelyDispose() {
    // Not instantiatable
  }

  static void of(Disposable... disposables) {
    of(Arrays.asList(disposables));
  }

  static void of(Iterable<Disposable> disposables) {
    disposables.forEach(
        d -> {
          if (d == null) {
            return;
          }
          Safely.run("disposing of subscription", d::dispose);
        });
  }
}
