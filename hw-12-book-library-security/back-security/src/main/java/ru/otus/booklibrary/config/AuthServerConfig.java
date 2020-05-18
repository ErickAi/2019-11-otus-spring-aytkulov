package ru.otus.booklibrary.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import static ru.otus.booklibrary.config.SecurityConfig.DELEGATING_PASSWORD_ENCODER;


@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
@EnableConfigurationProperties(AppProperties.class)
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter tokenEnhancer;
    private final AppProperties properties;
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManagerBean;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")              // '/oauth/token_key'
                .checkTokenAccess("isAuthenticated()")      // '/oauth/check_token'
                ;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore)
                .accessTokenConverter(tokenEnhancer)
                .authenticationManager(authenticationManagerBean);
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception { // @formatter:off
        String encodedPass = DELEGATING_PASSWORD_ENCODER.encode(properties.getUiClientSecret());
        clients.inMemory()
                .withClient(properties.getUiClientId()).secret(encodedPass)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write")
                .autoApprove(true)
                .accessTokenValiditySeconds(properties.getAccessTokenLifetime())
                .refreshTokenValiditySeconds(properties.getRefreshTokenLifetime())
                ;
    } // @formatter:on
}
