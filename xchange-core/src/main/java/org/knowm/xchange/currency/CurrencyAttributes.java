package org.knowm.xchange.currency;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public interface CurrencyAttributes extends Serializable {
  Set<String> getCodes();

  String getIsoCode();

  String getCommonCode();

  String getName();

  String getUnicode();

  class Builder {
    private String commonCode;
    private String name;
    private String unicode;
    private String[] alternativeCodes;

    public Builder setCommonCode(String commonCode) {
      this.commonCode = commonCode;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setUnicode(String unicode) {
      this.unicode = unicode;
      return this;
    }

    public Builder setAlternativeCodes(String... alternativeCodes) {
      this.alternativeCodes = alternativeCodes;
      return this;
    }

    public org.knowm.xchange.currency.CurrencyAttributes createCurrencyAttributes() {
      return new CurrencyAttributesImpl(commonCode, name, unicode, alternativeCodes);
    }

    public static class CurrencyAttributesImpl
        implements org.knowm.xchange.currency.CurrencyAttributes, Serializable {

      private final Set<String> codes;
      private final String isoCode;
      private final String commonCode;
      private final String name;
      private final String unicode;

      public CurrencyAttributesImpl(
          String commonCode, String name, String unicode, String... alternativeCodes) {

        if (alternativeCodes.length > 0) {
          this.codes = new TreeSet<>(Arrays.asList(alternativeCodes));
          this.getCodes().add(commonCode);
        } else {
          this.codes = Collections.singleton(commonCode);
        }

        String possibleIsoProposalCryptoCode = null;

        java.util.Currency javaCurrency = null;
        for (String code : this.getCodes()) {
          if (javaCurrency == null) {
            try {
              javaCurrency = java.util.Currency.getInstance(code);
            } catch (IllegalArgumentException e) {
            }
          }
          if (code.startsWith("X")) {
            possibleIsoProposalCryptoCode = code;
          }
        }

        if (javaCurrency != null) {
          this.isoCode = javaCurrency.getCurrencyCode();
        } else {
          this.isoCode = possibleIsoProposalCryptoCode;
        }

        this.commonCode = commonCode;

        if (name != null) {
          this.name = name;
        } else if (javaCurrency != null) {
          this.name = javaCurrency.getDisplayName();
        } else {
          this.name = commonCode;
        }

        if (unicode != null) {
          this.unicode = unicode;
        } else if (javaCurrency != null) {
          this.unicode = javaCurrency.getSymbol();
        } else {
          this.unicode = commonCode;
        }
      }

      @Override
      public int hashCode() {
        return getCommonCode().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        org.knowm.xchange.currency.CurrencyAttributes other =
            (org.knowm.xchange.currency.CurrencyAttributes) obj;
        if (getCommonCode() == null) {
          if (other.getCommonCode() != null) return false;
        } else if (!getCommonCode().equals(other.getCommonCode())) return false;
        return true;
      }

      @Override
      public Set<String> getCodes() {
        return codes;
      }

      @Override
      public String getIsoCode() {
        return isoCode;
      }

      @Override
      public String getCommonCode() {
        return commonCode;
      }

      @Override
      public String getName() {
        return name;
      }

      @Override
      public String getUnicode() {
        return unicode;
      }
    }
  }
}
