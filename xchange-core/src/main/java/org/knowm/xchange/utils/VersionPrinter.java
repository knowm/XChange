// Example only — educational demonstration
package org.knowm.xchange.utils;

/**
 * Simple helper utility that prints the current XChange version.
 * This file is meant for demo or build testing purposes.
 */
public final class VersionPrinter {

    private VersionPrinter() {}

    public static void printVersion() {
        System.out.println("XChange - Demo Build v5.2.4-SNAPSHOT");
    }

    public static void main(String[] args) {
        printVersion();
    }
}
