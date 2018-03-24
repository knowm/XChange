package org.known.xchange.acx.utils;

public class ArgUtils {
  public static <T> T tryGet(Object[] args, int index, Class<T> clz, T defaultValue) {
    if (args.length > index) {
      return tryCast(args[index], clz);
    }
    return defaultValue;
  }

  public static <T, K> T tryCast(K arg, Class<T> clz) {
    if (clz.isAssignableFrom(arg.getClass())) {
      return clz.cast(arg);
    }
    throw new IllegalArgumentException("Argument has type " + arg.getClass() + ", expected " + clz);
  }
}
