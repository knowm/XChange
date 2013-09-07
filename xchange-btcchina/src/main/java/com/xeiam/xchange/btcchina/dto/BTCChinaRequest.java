/**
 * Copyright (C) 2013 David Yam
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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

package com.xeiam.xchange.btcchina.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.xeiam.xchange.btcchina.BTCChinaUtils;

/**
 * @author David Yam
 */
public class BTCChinaRequest {
  
  @JsonProperty("id")
  protected long id = BTCChinaUtils.getGeneratedId();
  
  @JsonProperty("method")
  protected String method;
  
  @JsonProperty("params")
  @JsonRawValue
  protected String params;
 
  public long getId() {
    return id;
  }
  
  public void setId(long id){
    this.id = id;
  }
  
  public String getMethod(){
    return method;
  }
  
  public void setMethod(String method){
    this.method = method;
  }
  
  public String getParams(){
    return params;
  }
  
  public void setParams(String params) {
    this.params = params;
  }
  
}
