package com.sparksys.oauth.config;

import cn.hutool.json.JSONUtil;
import com.sparksys.oauth.enhancer.JwtTokenEnhancer;
import com.sparksys.oauth.properties.Oauth2Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 认证服务器配置
 *
 * @author zhouxinlei
 * @date 2020-05-24 13:23:36
 */
@Configuration
@EnableConfigurationProperties(Oauth2Properties.class)
@EnableAuthorizationServer
@Slf4j
public class Oauth2ServerAutoConfiguration extends AuthorizationServerConfigurerAdapter {


    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;
    @Autowired(required = false)
    private UserDetailsService userDetailsService;
    @Autowired(required = false)
    private AuthenticationManager authenticationManager;
    @Autowired(required = false)
    private Oauth2Properties oAuth2Properties;
    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        log.info("OAuth2Properties：{}", JSONUtil.toJsonPrettyStr(oAuth2Properties));
        clients.inMemory()
                .withClient(oAuth2Properties.getClientId())
                .secret(passwordEncoder.encode(oAuth2Properties.getClientSecret()))
                .accessTokenValiditySeconds(oAuth2Properties.getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(oAuth2Properties.getRefreshTokenValiditySeconds())
                .redirectUris(oAuth2Properties.getRegisteredRedirectUris())
                .scopes(oAuth2Properties.getScopes())
                .authorizedGrantTypes(oAuth2Properties.getAuthorizedGrantTypes());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer());
        //配置JWT的内容增强器
        delegates.add(accessTokenConverter());
        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //配置令牌存储策略
                .tokenStore(redisTokenStore())
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();
    }

    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }

    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnClass(value = KeyPair.class)
    public JwtAccessTokenConverter accessTokenConverter(KeyPair keyPair) {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair);
        return jwtAccessTokenConverter;
    }

    @Bean
    @ConditionalOnMissingBean(value = KeyPair.class)
    public JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter();
    }

}