import grails.plugin.springsecurity.SpringSecurityUtils
import hacking.HackingBeanFactoryPostProcessor
import hacking.extralogin.auth.OrganizationAuthenticationProvider
import hacking.extralogin.ui.OrganizationFilter
import hacking.logout.CustomLogoutSuccessHandler

beans = {

	def conf = SpringSecurityUtils.securityConfig

	hackingBeanFactoryPostProcessor(HackingBeanFactoryPostProcessor)

//	// custom authentication
//	authenticationProcessingFilter(OrganizationFilter) {
//		authenticationManager = ref('authenticationManager')
//		sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')
//		authenticationSuccessHandler = ref('authenticationSuccessHandler')
//		authenticationFailureHandler = ref('authenticationFailureHandler')
//		rememberMeServices = ref('rememberMeServices')
//		authenticationDetailsSource = ref('authenticationDetailsSource')
//		usernameParameter = conf.apf.usernameParameter
//		passwordParameter = conf.apf.passwordParameter
//		continueChainBeforeSuccessfulAuthentication = conf.apf.continueChainBeforeSuccessfulAuthentication
//		allowSessionCreation = conf.apf.allowSessionCreation
//		postOnly = conf.apf.postOnly
//		requiresAuthenticationRequestMatcher = ref('filterProcessUrlRequestMatcher')
//		storeLastUsername = conf.apf.storeLastUsername
//	}

	// custom authentication
	daoAuthenticationProvider(OrganizationAuthenticationProvider) {
		passwordEncoder = ref('passwordEncoder')
		saltSource = ref('saltSource')
		preAuthenticationChecks = ref('preAuthenticationChecks')
		postAuthenticationChecks = ref('postAuthenticationChecks')
	}

//	// custom logout redirect
//	logoutSuccessHandler(CustomLogoutSuccessHandler) {
//		redirectStrategy = ref('redirectStrategy')
//		defaultTargetUrl = conf.logout.afterLogoutUrl
//		alwaysUseDefaultTargetUrl = conf.logout.alwaysUseDefaultTargetUrl
//		targetUrlParameter = conf.logout.targetUrlParameter
//		useReferer = conf.logout.redirectToReferer
//	}
}
