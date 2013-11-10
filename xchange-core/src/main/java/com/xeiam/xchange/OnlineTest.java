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
package com.xeiam.xchange;

// todo: This doesn't javadoc because Category is not in the classpath for this interface. Fix by either adding junit as a dependency, or moving this out into an xcahnge-core-test module with the dependency.
// edited below to remove the offending link
///**
// * Use this to mark tests that require an Internet connection. These tests will not be run with mvn test but only in Maven's integration-test phase (eg. when running mvn install). Usage: annotate the
// * test class with @{@link org.junit.experimental.categories.Category}(OnlineTest.class) This class is in xchange-core because it must be visible in all test sources, as well as in core (it is
// * referenced in the core pom).
// *
// * @author Matija Mazi
// */
/**
 * Use this to mark tests that require an Internet connection. These tests will not be run with mvn test but only in Maven's integration-test phase (eg. when running mvn install). Usage: annotate the
 * test class with Category(OnlineTest.class) This class is in xchange-core because it must be visible in all test sources, as well as in core (it is
 * referenced in the core pom).
 * 
 * @author Matija Mazi
 */
public interface OnlineTest {

}
