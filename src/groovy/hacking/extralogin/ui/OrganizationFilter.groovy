package hacking.extralogin.ui

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.web.authentication.RequestHolderAuthenticationFilter
import hacking.extralogin.OrganizationAuthentication

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class OrganizationFilter extends RequestHolderAuthenticationFilter {

	@Override
	Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		if (!request.post) {
			throw new AuthenticationServiceException("Authentication method not supported: $request.method")
		}

		String username = (obtainUsername(request) ?: '').trim()
		String password = obtainPassword(request) ?: ''
		String orgName = request.getParameter('orgName')

		def authentication = new OrganizationAuthentication(username, password, orgName)

		HttpSession session = request.getSession(false)
		if (session || getAllowSessionCreation()) {
			request.session.setAttribute SpringSecurityUtils.SPRING_SECURITY_LAST_USERNAME_KEY, username
		}

		setDetails request, authentication

		authenticationManager.authenticate authentication
	}
}
