package hacking.extralogin.auth

import grails.plugin.springsecurity.userdetails.GormUserDetailsService
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.util.logging.Slf4j
import hacking.OrgUser
import hacking.User
import hacking.extralogin.OrganizationAuthentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.dao.SaltSource
import org.springframework.security.authentication.encoding.PasswordEncoder
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsChecker
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Slf4j
class OrganizationAuthenticationProvider implements AuthenticationProvider {

	PasswordEncoder passwordEncoder
	SaltSource saltSource
	UserDetailsChecker preAuthenticationChecks
	UserDetailsChecker postAuthenticationChecks

	Authentication authenticate(Authentication auth) throws AuthenticationException {
		OrganizationAuthentication authentication = (OrganizationAuthentication) auth

		String password = authentication.credentials
		String username = authentication.name
		String organizationName = authentication.organizationName

		GrailsUser userDetails
		Collection<GrantedAuthority> authorities

		// use withTransaction to avoid lazy loading exceptions
		User.withTransaction { status ->
			User user = OrgUser.createCriteria().get {
				organization {
					eq 'name', organizationName
				}
				user {
					eq 'username', username
				}
				projections {
					property 'user'
				}
			}

			if (!user) {
				// TODO customize 'springSecurity.errors.login.fail' i18n message in app's messages.properties with org name
				log.warn "User not found: $username in organization $organizationName"
				throw new UsernameNotFoundException('User not found')
			}

			authorities = user.authorities.collect { new SimpleGrantedAuthority(it.authority) } ?: [GormUserDetailsService.NO_ROLE]

			userDetails = new GrailsUser(
				user.username, user.password, user.enabled, !user.accountExpired,
				!user.passwordExpired, !user.accountLocked, authorities, user.id)
		}

		preAuthenticationChecks.check userDetails
		additionalAuthenticationChecks userDetails, authentication
		postAuthenticationChecks.check userDetails

		def result = new OrganizationAuthentication(userDetails, authentication.credentials, organizationName, authorities)
		result.details = authentication.details
		result
	}

	protected void additionalAuthenticationChecks(GrailsUser userDetails,
			OrganizationAuthentication authentication) throws AuthenticationException {

		def salt = saltSource.getSalt(userDetails)

		if (authentication.credentials == null) {
			log.debug 'Authentication failed: no credentials provided'
			throw new BadCredentialsException('Bad credentials', userDetails)
		}

		String presentedPassword = authentication.credentials
		if (!passwordEncoder.isPasswordValid(userDetails.password, presentedPassword, salt)) {
			log.debug 'Authentication failed: password does not match stored value'

			throw new BadCredentialsException('Bad credentials', userDetails)
		}
	}

	boolean supports(Class<?> authenticationClass) {
      OrganizationAuthentication.isAssignableFrom authenticationClass
	}
}
