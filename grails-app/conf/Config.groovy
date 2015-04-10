grails.config.locations = [
	"classpath:${appName}-config.groovy",
	"file:./${appName}-config.groovy"]

String systemPropertyLocation = System.properties[appName + '.config.location']
if (systemPropertyLocation) grails.config.locations << 'file:' + systemPropertyLocation

grails.controllers.defaultScope = 'singleton'
grails.converters.encoding = 'UTF-8'
grails.enable.native2ascii = true
grails.exceptionresolver.params.exclude = ['password']
grails.hibernate.cache.queries = false
grails.hibernate.pass.readonly = false
grails.hibernate.osiv.readonly = false
grails.json.legacy.builder = false
grails.logging.jul.usebridge = true
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [
	all:           '*/*',
	atom:          'application/atom+xml',
	css:           'text/css',
	csv:           'text/csv',
	form:          'application/x-www-form-urlencoded',
	html:          ['text/html','application/xhtml+xml'],
	js:            'text/javascript',
	json:          ['application/json', 'text/json'],
	multipartForm: 'multipart/form-data',
	rss:           'application/rss+xml',
	text:          'text/plain',
	hal:           ['application/hal+json','application/hal+xml'],
	xml:           ['text/xml', 'application/xml']
]
grails.project.groupId = appName
grails.scaffolding.templates.domainSuffix = ''
grails.spring.bean.packages = []
grails.views.default.codec = 'html'
grails {
	views {
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml'
			codecs {
				expression = 'html'
				scriptlet = 'html'
				taglib = 'none'
				staticparts = 'none'
			}
		}
	}
}
grails.web.disable.multipart = false

environments {
	production {
		grails.logging.jul.usebridge = false
	}
}

log4j.main = {
	error 'org.codehaus.groovy.grails',
	      'org.springframework',
	      'org.hibernate',
	      'net.sf.ehcache.hibernate'
	debug 'org.hibernate.SQL'
	// trace 'org.hibernate.type.descriptor.sql.BasicBinder'
}

grails {
	plugin {
		springsecurity {
			authority {
				className = 'hacking.Role'
			}
			controllerAnnotations {
				staticRules = [
					'/':               ['permitAll'],
					'/index':          ['permitAll'],
					'/index.gsp':      ['permitAll'],
					'/assets/**':      ['permitAll'],
					'/**/js/**':       ['permitAll'],
					'/**/css/**':      ['permitAll'],
					'/**/images/**':   ['permitAll'],
					'/**/favicon.ico': ['permitAll']
				]
			}
			logout {
				postOnly = false
			}
			userLookup {
				userDomainClassName = 'hacking.User'
				authorityJoinClassName = 'hacking.UserRole'
			}
		}
	}
}
