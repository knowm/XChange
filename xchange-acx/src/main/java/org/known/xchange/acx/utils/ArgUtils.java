package org.known.xchange.acx.utils;

public class ArgUtils {
    public static <T> T tryGet(Object[] args, int index, Class<T> clz, T defaultValue) {
        if (args.length > index) {
            Object arg = args[index];
            if (clz.isAssignableFrom(arg.getClass())) {
                return clz.cast(arg);
            } else {
                throw new IllegalArgumentException("Argument has type " + arg.getClass() + ", expected " + clz);
            }
        }
        return defaultValue;
    }
}
