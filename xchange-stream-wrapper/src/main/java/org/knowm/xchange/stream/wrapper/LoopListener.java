package org.knowm.xchange.stream.wrapper;

import com.google.common.annotations.VisibleForTesting;

@VisibleForTesting
interface LoopListener {

  default void onBlocked() {
  }

  default void onStop() {
  }

}
