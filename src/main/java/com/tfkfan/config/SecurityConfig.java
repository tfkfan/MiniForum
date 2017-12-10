package com.tfkfan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ComponentScan("com.tfkfan")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").hasAnyRole("ADMIN", "USER").antMatchers("/admin/**")
				.hasAnyRole("ADMIN").and().csrf().and().formLogin().loginPage("/login").permitAll().and()
				.logout().logoutSuccessUrl("/login?logout").permitAll();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("ram").password("ram123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("ravan").password("ravan123").roles("USER");
		auth.inMemoryAuthentication().withUser("kans").password("kans123").roles("USER");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(true);
	}
}