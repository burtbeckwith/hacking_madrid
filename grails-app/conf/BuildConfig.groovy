grails.servlet.version = '3.0'
grails.project.work.dir = 'target'
grails.project.target.level = 1.7
grails.project.source.level = 1.7

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {
	inherits 'global'
	log 'warn'
	checksums true
	legacyResolve false

	repositories {
		inherits true

		grailsPlugins()
		grailsHome()
		mavenLocal()
		grailsCentral()
		mavenCentral()
	}

	dependencies {
		test 'org.grails:grails-datastore-test-support:1.0.2-grails-2.4'
	}

	plugins {
		build ':tomcat:8.0.20'

		compile ':scaffolding:2.1.2'
		compile ':cache:1.1.8'
		compile ':asset-pipeline:2.1.5'

		runtime ':database-migration:1.4.0'
		runtime ':hibernate4:4.3.8.1'
		runtime ':jquery:1.11.1'

		compile ':spring-security-core:2.0-SNAPSHOT'
	}
}
