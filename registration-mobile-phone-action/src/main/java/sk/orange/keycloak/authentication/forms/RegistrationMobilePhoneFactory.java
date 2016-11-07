package sk.orange.keycloak.authentication.forms;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class RegistrationMobilePhoneFactory implements FormActionFactory, ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "registration-mobile-phone-action";
    private static final RegistrationMobilePhone SINGLETON = new RegistrationMobilePhone();

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    /*static {
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName("cookie.max.age");
        property.setLabel("Cookie Max Age");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Max age in seconds of the SECRET_QUESTION_COOKIE.");
        configProperties.add(property);
    }*/

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public FormAction create(KeycloakSession session) {
        return SINGLETON;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getHelpText() {
        return "Validates that a mobile phone number was sent and is in correct international MSISDN form (e.g. +421905123456). Number is afterwards saved to user attributes.";
    }

    @Override
    public String getDisplayType() {
        return "Mobile Phone Validation";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return false; // PSPS - tbd
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

}
