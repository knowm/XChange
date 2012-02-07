/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.mtgox.v1.service.marketdata.dto;

/**
 * Data object representing Ticker from Mt Gox
 */
public class MtGoxTicker {

  public String result;
  public Return _return;

  public class Return {

    public High high;
    public Low low;
    public Avg avg;
    public VWAP vwap;
    public Vol vol;
    public Last_Local last_local;
    public Last last;
    public Last_Orig last_orig;
    public Last_All last_all;
    public Buy buy;
    public Sell sell;

    public class High {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }

    }

    public class Low {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class Avg {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class VWAP {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class Vol {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class Last_Local {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class Last {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class Last_Orig {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class Last_All {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class Buy {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public class Sell {
      String value;
      String value_int;
      String display;
      String currency;

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getValue_int() {
        return value_int;
      }

      public void setValue_int(String value_int) {
        this.value_int = value_int;
      }

      public String getDisplay() {
        return display;
      }

      public void setDisplay(String display) {
        this.display = display;
      }

      public String getCurrency() {
        return currency;
      }

      public void setCurrency(String currency) {
        this.currency = currency;
      }
    }

    public High getHigh() {
      return high;
    }

    public void setHigh(High high) {
      this.high = high;
    }

    public Low getLow() {
      return low;
    }

    public void setLow(Low low) {
      this.low = low;
    }

    public Avg getAvg() {
      return avg;
    }

    public void setAvg(Avg avg) {
      this.avg = avg;
    }

    public VWAP getVwap() {
      return vwap;
    }

    public void setVwap(VWAP vwap) {
      this.vwap = vwap;
    }

    public Vol getVol() {
      return vol;
    }

    public void setVol(Vol vol) {
      this.vol = vol;
    }

    public Last_Local getLast_local() {
      return last_local;
    }

    public void setLast_local(Last_Local last_local) {
      this.last_local = last_local;
    }

    public Last getLast() {
      return last;
    }

    public void setLast(Last last) {
      this.last = last;
    }

    public Last_Orig getLast_orig() {
      return last_orig;
    }

    public void setLast_orig(Last_Orig last_orig) {
      this.last_orig = last_orig;
    }

    public Last_All getLast_all() {
      return last_all;
    }

    public void setLast_all(Last_All last_all) {
      this.last_all = last_all;
    }

    public Buy getBuy() {
      return buy;
    }

    public void setBuy(Buy buy) {
      this.buy = buy;
    }

    public Sell getSell() {
      return sell;
    }

    public void setSell(Sell sell) {
      this.sell = sell;
    }

  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Return getReturn() {
    return _return;
  }

  public void setReturn(Return _return) {
    this._return = _return;
  }

}
