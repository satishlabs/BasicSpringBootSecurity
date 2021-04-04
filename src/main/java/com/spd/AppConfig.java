package com.spd;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
public class AppConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
	
	@Autowired 
	DataSource dataSource;

	
	@Bean 
	 public BCryptPasswordEncoder passwordEncoder() { 
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder; 
	 } 

	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		registry.viewResolver(viewResolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("super").password("{noop}super").roles("CUSTOMER","ADMIN");

		auth.inMemoryAuthentication().withUser("satish").password("{noop}satish").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("vas").password("{noop}vas").roles("STOREKEEPER");
		auth.inMemoryAuthentication().withUser("sri").password("{noop}sri").roles("CUSTOMER");
		auth.inMemoryAuthentication().withUser("sd").password("{noop}sd").roles("CUSTOMER");
		auth.inMemoryAuthentication().withUser("ds").password("{noop}ds").roles("CUSTOMER");
	}
	 */
	@Autowired 
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
		String usersQuery="select username,password, active from myusers where username=?"; 
		String rolesQuery="select username, role from myroles where username=?"; 
		
		auth.jdbcAuthentication() 
		.dataSource(dataSource) 
		.usersByUsernameQuery(usersQuery) 
		.authoritiesByUsernameQuery(rolesQuery); 
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/deleteBook**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/addBook**").access("hasAnyRole('ROLE_ADMIN','ROLE_STOREKEEPER')")
		.antMatchers("/editBook**").access("hasAnyRole('ROLE_ADMIN','ROLE_STOREKEEPER')")
		.antMatchers("/placeOrder**").access("hasAnyRole('ROLE_CUSTOMER')")
		.and()
		.formLogin()
		.loginPage("/login")
		.failureUrl("/login?error")
		.usernameParameter("myusername")
		.passwordParameter("mypassword")
		.and()
		.logout()
		.invalidateHttpSession(true)
		.logoutSuccessUrl("/login?logout")
		.and()
		.exceptionHandling().accessDeniedPage("/WEB-INF/views/invalidAccess.jsp");
	}
}
