/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.campbx.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public class CampBXResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5749270949404186745L;
	@JsonProperty("Success")
	private String success;
	@JsonProperty("Info")
	private String info;
	@JsonProperty("Error")
	private String error;

	public String getSuccess() {

		return success;
	}

	public String getInfo() {

		return info;
	}

	public String getError() {

		return error;
	}

	public boolean isError() {

		return error != null;
	}

	public boolean isInfo() {

		return info != null;
	}

	public boolean isSuccess() {

		return success != null;
	}

	private String getUnwrappedResult() {

		return isError() ? error : isInfo() ? info : isSuccess() ? success : null;
	}

	private String getType() {

		return isError() ? "Error" : isInfo() ? "Info" : isSuccess() ? "Success" : "<Unknown>";
	}

	@Override
	public String toString() {

		return String.format("CampBXResponse[%s: %s]", getType(), getUnwrappedResult());
	}
}
