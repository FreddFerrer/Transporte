package TransportesYIYO.seguimiento.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/camiones").hasAnyRole("ADMIN", "CHOFER1", "CHOFER2")
                .antMatchers("/api/camiones/{id}").hasAnyRole("ADMIN", "CHOFER1", "CHOFER2")
                .antMatchers("/api/pedidos").hasAnyRole("ADMIN", "CHOFER1", "CHOFER2")
                .antMatchers("/api/pedidos/{id}").hasAnyRole("ADMIN", "CHOFER1", "CHOFER2")
                .antMatchers("/api/pedidos/{id}/entregado").hasAnyRole("ADMIN", "CHOFER1", "CHOFER2")
                .antMatchers("/api/pedidos/{id}/estados").hasAnyRole("ADMIN", "CHOFER1", "CHOFER2")
                .antMatchers("/api/pedidos/{id}/estados").hasAnyRole("ADMIN", "CHOFER1", "CHOFER2")
                .antMatchers("/api/pedidos/{id}/estados").hasAnyRole("ADMIN", "CHOFER1", "CHOFER2")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMIN")
                .and()
                .withUser("chofer1").password("{noop}chofer1").roles("CHOFER1")
                .and()
                .withUser("chofer2").password("{noop}chofer2").roles("CHOFER2");
    }
}

