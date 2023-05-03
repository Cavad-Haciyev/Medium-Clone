package com.example.mediumclone.core.configs;

import com.example.mediumclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;
  private final JwtFilter jwtFiler;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .cors()
        .disable()
        .authorizeRequests()
//        .antMatchers("/v1/users/user").hasRole("ADMIN")
//        .antMatchers("/v1/users/{id}").hasRole("ADMIN")
//        .antMatchers("/v1/users/myProfileDetails").permitAll()
//        .antMatchers("/v1/users/savePost").permitAll()
//        .antMatchers("/v1/users/savedPosts").permitAll()
//        .antMatchers("/v1/users/followUp").permitAll()
//        .antMatchers("/v1/users/follow").permitAll()
//        .antMatchers("/v1/users/updateProfile").permitAll()
//        .antMatchers("/v1/users/register").permitAll()
//        .antMatchers("/v1/users/activate/{email}").permitAll()
//        .antMatchers("/v1/users/login").permitAll()
        .antMatchers("/v1/users/**").permitAll()
        .antMatchers("/v1/posts/post").permitAll()
        .antMatchers("/v1/posts/followersPost").permitAll()
        .antMatchers("/v1/posts/tittle").permitAll()
        .antMatchers("/v1/posts/{id}").permitAll()
        .antMatchers("/v1/posts/").permitAll()
        .antMatchers("/v1/posts/likeIt/{postId}").permitAll()

        .antMatchers("/v1/comments/comment").permitAll()
        .antMatchers("/v1/comments/{id}").permitAll()
        .antMatchers("/v1/comments").permitAll()

        .antMatchers("/v1/fpt/token/{email}").permitAll()
        .antMatchers("/v1/fpt/token/{token}").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtFiler, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
