package com.swpc.organicledger.spring.config;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.servlet.LocaleResolver;

import com.swpc.organicledger.spring.error.controller.AppErrorController;
import com.swpc.organicledger.spring.impl.AuditorAwareImpl;
import com.swpc.organicledger.spring.resolver.SmartLocaleResolver;

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(ServerSecurityConfig.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder oauthClientPasswordEncoder;

	@Autowired
    private ErrorAttributes errorAttributes;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new SmartLocaleResolver();
	}
	
    @Bean
    public AppErrorController appErrorController(){
        return new AppErrorController(errorAttributes);
    }
    
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(oauthClientPasswordEncoder);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
	}
}
