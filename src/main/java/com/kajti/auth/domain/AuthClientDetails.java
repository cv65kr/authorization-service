package com.kajti.auth.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

@Data
@Builder
@Document("auth_client_details")
public class AuthClientDetails implements ClientDetails {

    private static final long serialVersionUID = -2767919014226512337L;

    @Id
    private UUID uuid;

    private String clientId;

    private String clientSecret;

    private String grantTypes;

    private String scopes;

    private String resources;

    private String redirectUris;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return explode(resources);
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return explode(scopes);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return explode(grantTypes);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return explode(redirectUris);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    private Set<String> explode(String value) {
        if (value == null) {
            return null;
        }

        return new HashSet<>(Arrays.asList(value.split(",")));
    }
}