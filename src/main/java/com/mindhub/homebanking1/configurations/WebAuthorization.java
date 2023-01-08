package com.mindhub.homebanking1.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {
  @Override
 public void configure(HttpSecurity http) throws Exception{
    http.authorizeRequests()

            .antMatchers("/rest/**", "/h2-console/**", "/api/accounts").hasAuthority("ADMIN")
            .antMatchers("web/src/pages/**").hasAnyAuthority("CLIENT" , "ADMIN")
            .antMatchers("/api/clients/current").hasAnyAuthority("CLIENT" , "ADMIN")
            .antMatchers(HttpMethod.POST,"/api/transactions").hasAnyAuthority("CLIENT" , "ADMIN")
            .antMatchers(HttpMethod.POST,"/api/clients/current/accounts").hasAnyAuthority("CLIENT" , "ADMIN")
            .antMatchers(HttpMethod.POST,"/api/clients/current/cards").hasAnyAuthority("CLIENT" , "ADMIN")
            .antMatchers(HttpMethod.POST,"/api/accounts/current/**").hasAnyAuthority("CLIENT" , "ADMIN")
            .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
            .antMatchers("/api/clients").hasAuthority("ADMIN")
            //.antMatchers("/api/**").hasAuthority("ADMIN")
            .antMatchers("/api/loans").permitAll()
            .antMatchers("web/src/index.html").permitAll();




    http.formLogin()
        .usernameParameter("email")
        .passwordParameter("password")
        .loginPage("/api/login");

    http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");


    http.csrf().disable();

    http.headers().frameOptions().disable();

    http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

    http.formLogin().successHandler((req,res,auth) -> clearAuthenticationAttributes(req));

    http.formLogin().failureHandler((req,res,exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

    http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }


    private void clearAuthenticationAttributes(HttpServletRequest request){
      HttpSession session = request.getSession(false);

      if(session != null){
          session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
      }
}

}
