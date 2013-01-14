/**
 * Classes in this package may be used to create proxy client objects for REST web services.
 * <p/>
 * For a working example see the Bitstamp interface and its usages in the xchange-bitstamp module.
 * <p/>
 * This is based on JAX-RS ({@link javax.ws.rs} -- only the JAX-RS annotations are used) and {@link java.lang.reflect.Proxy}.
 * <p/>
 * Usage:
 * <ol>
 *   <li>Create a java interface that represents the API -- see BitStamp.java for an example</li>
 *   <li>Annotate the interface with JAX-RS annotations</li>
 *   <li>Call {@link RestProxyFactory#createProxy(Class, String, com.xeiam.xchange.utils.HttpTemplate, org.codehaus.jackson.map.ObjectMapper)}</li>
 * </ol>
 *
 * <p/>
 * Basic support is provided for {@link javax.ws.rs.Path}, {@link javax.ws.rs.GET}, {@link javax.ws.rs.POST},
 * {@link javax.ws.rs.QueryParam}, {@link javax.ws.rs.FormParam}, {@link javax.ws.rs.HeaderParam}, {@link javax.ws.rs.PathParam}.
 * <p/>
 *
 * Some of the classes here may also be useful even when not using the JAX-RS approach to calling REST web services,
 * eg. {@link Params}.
 *
 * @see RestProxyFactory
 *
 */
package com.xeiam.xchange.rest;