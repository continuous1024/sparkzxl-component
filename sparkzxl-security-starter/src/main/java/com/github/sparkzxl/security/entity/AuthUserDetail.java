package com.github.sparkzxl.security.entity;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * description：security用户
 *
 * @author： zhouxinlei
 * @date： 2020-06-24 16:35:11
 */
@Data
public class AuthUserDetail implements UserDetails {

    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    private List<String> authorityList;

    public AuthUserDetail(Long id, String username, String password, Boolean enabled, List<String> authorityList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorityList = authorityList;
        if (CollectionUtils.isNotEmpty(authorityList)) {
            this.authorities = Lists.newArrayList();
            authorityList.forEach(item -> this.authorities.add(new SimpleGrantedAuthority(item)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
