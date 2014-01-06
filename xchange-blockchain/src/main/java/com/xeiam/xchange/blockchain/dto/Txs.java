/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.blockchain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public final class Txs {

  private final long block_height;
  private final String hash;
  private final List<Inputs> inputs;
  private final List<Out> out;
  private final String relayed_by;
  private final long result;
  private final long size;
  private final long time;
  private final long tx_index;
  private final int ver;
  private final int vin_sz;
  private final int vout_sz;

  /**
   * Constructor
   * 
   * @param block_height
   * @param hash
   * @param inputs
   * @param out
   * @param relayed_by
   * @param result
   * @param size
   * @param time
   * @param tx_index
   * @param ver
   * @param vin_sz
   * @param vout_sz
   */
  public Txs(@JsonProperty("block_height") long block_height, @JsonProperty("hash") String hash, @JsonProperty("inputs") List<Inputs> inputs, @JsonProperty("out") List<Out> out,
      @JsonProperty("relayed_by") String relayed_by, @JsonProperty("result") long result, @JsonProperty("size") long size, @JsonProperty("time") long time, @JsonProperty("tx_index") long tx_index,
      @JsonProperty("ver") int ver, @JsonProperty("vin_sz") int vin_sz, @JsonProperty("vout_sz") int vout_sz) {

    this.block_height = block_height;
    this.hash = hash;
    this.inputs = inputs;
    this.out = out;
    this.relayed_by = relayed_by;
    this.result = result;
    this.size = size;
    this.time = time;
    this.tx_index = tx_index;
    this.ver = ver;
    this.vin_sz = vin_sz;
    this.vout_sz = vout_sz;
  }

  public long getBlock_height() {

    return this.block_height;
  }

  public String getHash() {

    return this.hash;
  }

  public List<Inputs> getInputs() {

    return this.inputs;
  }

  public List<Out> getOut() {

    return this.out;
  }

  public String getRelayed_by() {

    return this.relayed_by;
  }

  public long getResult() {

    return this.result;
  }

  public long getSize() {

    return this.size;
  }

  public long getTime() {

    return this.time;
  }

  public long getTx_index() {

    return this.tx_index;
  }

  public int getVer() {

    return this.ver;
  }

  public int getVin_sz() {

    return this.vin_sz;
  }

  public int getVout_sz() {

    return this.vout_sz;
  }

}
