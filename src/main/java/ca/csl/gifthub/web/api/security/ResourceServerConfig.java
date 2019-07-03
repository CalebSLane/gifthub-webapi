package ca.csl.gifthub.web.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    
    public static final String API_PREFIX = "api";
    private static final String SECURED_PATTERN = "/" + API_PREFIX + "/**"; 
    
    private ResourceServerTokenServices tokenServices;
    
    public ResourceServerConfig(ResourceServerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("api").tokenServices(this.tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher(SECURED_PATTERN)
            .authorizeRequests()
                .antMatchers("/**/v2/**").permitAll()
                .antMatchers(HttpMethod.GET).access(SECURED_READ_SCOPE)
                .antMatchers(HttpMethod.POST).access(SECURED_WRITE_SCOPE)
                .antMatchers(HttpMethod.PUT).access(SECURED_WRITE_SCOPE)
                .antMatchers(HttpMethod.DELETE).access(SECURED_WRITE_SCOPE)
                .anyRequest().authenticated();
    }
}
