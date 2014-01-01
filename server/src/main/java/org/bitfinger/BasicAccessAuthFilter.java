package org.bitfinger;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicAccessAuthFilter extends BasicHttpAuthenticationFilter {
	private static final Logger log = LoggerFactory
			.getLogger(BasicAccessAuthFilter.class);

	@Inject
	public BasicAccessAuthFilter() {
		this.setApplicationName("Password Self Service");
		this.setAuthcScheme("B4S1C");
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		String httpMethod = httpRequest.getMethod();
		if ("OPTIONS".equalsIgnoreCase(httpMethod)) {
			return true;
		} else {
			return super.isAccessAllowed(request, response, mappedValue);
		}
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String authorizationHeader = getAuthzHeader(request);
		if (authorizationHeader == null || authorizationHeader.length() == 0) {
			// Create an empty authentication token since there is no
			// Authorization header.
			return createToken("", "", request, response);
		}

		if (log.isDebugEnabled()) {
			log.debug("Attempting to execute login with headers ["
					+ authorizationHeader + "]");
		}

		String[] prinCred = getPrincipalsAndCredentials(authorizationHeader,
				request);
		if (prinCred == null || prinCred.length < 2) {
			// Create an authentication token with an empty password,
			// since one hasn't been provided in the request.
			String username = prinCred == null || prinCred.length == 0 ? ""
					: prinCred[0];
			return createToken(username, "", request, response);
		}

		String username = prinCred[0];
		String password = prinCred[1];
		return createToken(username, password, request, response);
	}

}
