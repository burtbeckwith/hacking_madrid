package hacking

import groovy.transform.CompileStatic
import hacking.extralogin.ui.OrganizationFilter
import hacking.logout.CustomLogoutSuccessHandler

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.GenericBeanDefinition

@CompileStatic
class HackingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition)beanFactory.getBeanDefinition('authenticationProcessingFilter')
		beanDefinition.beanClass = OrganizationFilter

		beanDefinition = (GenericBeanDefinition)beanFactory.getBeanDefinition('logoutSuccessHandler')
		beanDefinition.beanClass = CustomLogoutSuccessHandler
	}
}
