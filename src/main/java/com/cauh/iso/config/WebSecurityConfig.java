package com.cauh.iso.config;

import com.cauh.common.config.ClassPathTldsLoader;
import com.cauh.common.security.authentication.CustomAuthenticationProvider;
import com.cauh.common.security.authentication.CustomWebAuthenticationDetailsSource;
import com.cauh.common.security.filter.AjaxRequestHandlingFilter;
import com.cauh.common.security.vote.CustomWebAccessDecisionVoter;
import com.cauh.common.service.CustomUserDetailsService;
import com.cauh.iso.security.CustomAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//securedEnabled = true
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * Thread 에서도 인증정보를 사용 할수 있도록 설정.
     */
//    public WebSecurityConfig() {
//        super();
//        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }

    @Value("${role.admin-1}")
    private String adminRole1;

//    @Value("${role.admin-2}")
//    private String adminRole2;
//
//    @Value("${role.admin-3}")
//    private String adminRole3;
//
//    @Value("${role.admin-4}")
//    private String adminRole4;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("==> Security Configure");
        http.authorizeRequests()
                .antMatchers(
                        "/static/**",
                        "/signUp/**",
                        "/login"
                ).permitAll()
                .antMatchers("/admin/**").hasAnyAuthority(adminRole1)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .authenticationDetailsSource(webAuthenticationDetailsSource())
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=0")
                .defaultSuccessUrl("/", true)
//                .successHandler(successHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("SESSION", "JSESSIONID")
//                .logoutSuccessUrl("/login?logout")
//                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .authenticationProvider(customAuthenticationProvider())
//                .addFilterAfter(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
//                .addFilterBefore(webAuthenticationFilter(), FilterSecurityInterceptor.class)
//                .addFilterAt(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
                .addFilterAfter(ajaxRequestHandlingFilter(), ExceptionTranslationFilter.class)

                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
//                .accessDeniedPage("/access-denied")
                .and()
                .csrf().disable();

        http.headers().frameOptions().sameOrigin();

        http.sessionManagement()
//                .sessionAuthenticationStrategy(customConcurrentSessionControlAuthenticationStrategy())
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .invalidSessionStrategy((req, res) -> {
//                logger.info("onInvalidSessionDetected *********************************************");
//                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        })
//                .sessionFixation().changeSessionId()
                .invalidSessionUrl("/login?invalidSession")
                .sessionAuthenticationErrorUrl("/login?maximumSessions")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
//                .maximumSessions(1)
//                .expiredSessionStrategy((e) -> {
//                    logger.info("expiredSessionStrategy -> {}", e);
//                    logger.info("This session has been expired (possibly due to multiple concurrent logins being attempted as the same user). = {}", e.getSessionInformation().getPrincipal());
//                    e.getResponse().sendRedirect("/expired");
//                })
                .expiredUrl("/login?sessionExpired");
//                .sessionRegistry(sessionRegistry());

    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Override
    public void configure(WebSecurity web) {
        web.
                ignoring().antMatchers("/static/**", "/favicon.ico")
                .and()
//                .securityInterceptor(customFilterSecurityInterceptor())
//                .privilegeEvaluator(webInvocationPrivilegeEvaluator())
                .expressionHandler(webSecurityExpressionHandler());
    }

//    @Bean
//    public WebInvocationPrivilegeEvaluator webInvocationPrivilegeEvaluator() throws Exception {
//        return new DefaultWebInvocationPrivilegeEvaluator(customFilterSecurityInterceptor());
//    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        webSecurityExpressionHandler.setDefaultRolePrefix("");
//        webSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return webSecurityExpressionHandler;
    }


//    @Bean
//    public CustomConcurrentSessionControlAuthenticationStrategy customConcurrentSessionControlAuthenticationStrategy() {
//        CustomConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy
//                = new CustomConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
//        concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
//        concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);
//        concurrentSessionControlAuthenticationStrategy.setForceLogin(true);
//
//        return concurrentSessionControlAuthenticationStrategy;
//    }


    @Bean
    public CustomWebAuthenticationDetailsSource webAuthenticationDetailsSource() {
        return new CustomWebAuthenticationDetailsSource();
    }

//    @Bean
//    @SuppressWarnings("unchecked")
//    public SpringSessionBackedSessionRegistry sessionRegistry() {
//        return new SpringSessionBackedSessionRegistry(this.sessionRepository);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider()).userDetailsService(userDetailsService());
    }

    @Bean
    @Override
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        log.info("@@@@@@@@customAuthenticationProvider();");
        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        customAuthenticationProvider.setUserDetailsService(userDetailsService());
        return customAuthenticationProvider;
    }


    @Bean
    public AjaxRequestHandlingFilter ajaxRequestHandlingFilter() {
        AjaxRequestHandlingFilter ajaxRequestHandlingFilter = new AjaxRequestHandlingFilter();
        return ajaxRequestHandlingFilter;
    }


    @Bean
    public AffirmativeBased accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(webAccessDecisionVoter());

        AffirmativeBased affirmativeBased = new AffirmativeBased(accessDecisionVoters);
        return affirmativeBased;
    }

    @Bean
    public CustomWebAccessDecisionVoter webAccessDecisionVoter() {
//        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
//        roleHierarchyVoter.setRolePrefix("ROLE_");
        CustomWebAccessDecisionVoter webAccessDecisionVoter = new CustomWebAccessDecisionVoter();
//        edcWebAccessDecisionVoter.setRolePrefix("ROLE_");
        return webAccessDecisionVoter;
    }
//    @Bean
//    public CustomWebAccessDecisionVoter webAccessDecisionVoter() {
////        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
////        roleHierarchyVoter.setRolePrefix("ROLE_");
//        CustomWebAccessDecisionVoter webAccessDecisionVoter = new CustomWebAccessDecisionVoter();
////        edcWebAccessDecisionVoter.setRolePrefix("ROLE_");
//        return webAccessDecisionVoter;
//    }

//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        CustomLogoutSuccessHandler logoutSuccessHandler = new CustomLogoutSuccessHandler();
//        logoutSuccessHandler.setDefaultTargetUrl("/login?logout");
//        return logoutSuccessHandler;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(ClassPathTldsLoader.class)
    public ClassPathTldsLoader classPathTldsLoader() {
        return new ClassPathTldsLoader();
    }

//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

}