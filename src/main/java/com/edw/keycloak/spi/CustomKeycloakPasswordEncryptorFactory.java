package com.edw.keycloak.spi;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     com.edw.keycloak.spi.CustomKeycloakPasswordEncryptorFactory
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 30 Mar 2021 12:19
 */
public class CustomKeycloakPasswordEncryptorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "password-encryption";

    private static final CustomKeycloakPasswordEncryptor SINGLETON = new CustomKeycloakPasswordEncryptor();

    public String getDisplayType() {
        return "Simple Password Encryption";
    }

    public String getReferenceCategory() {
        return "Simple Password Encryption";
    }

    public boolean isConfigurable() {
        return false;
    }

    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return CustomKeycloakPasswordEncryptorFactory.REQUIREMENT_CHOICES;
    }

    public boolean isUserSetupAllowed() {
        return false;
    }

    public String getHelpText() {
        return "Simple Password Encryption";
    }

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    public List<ProviderConfigProperty> getConfigProperties() {
        return new ArrayList<ProviderConfigProperty>();
    }

    public Authenticator create(KeycloakSession keycloakSession) {
        return SINGLETON;
    }

    public void init(Config.Scope scope) {

    }

    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    public void close() {

    }

    public String getId() {
        return CustomKeycloakPasswordEncryptorFactory.PROVIDER_ID;
    }

    public int order() {
        return 0;
    }
}
