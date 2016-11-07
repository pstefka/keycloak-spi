package sk.orange.keycloak.authentication.forms;

import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.services.validation.Validation;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.authentication.forms.RegistrationPage;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
 
import javax.ws.rs.core.MultivaluedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationMobilePhone implements FormAction {

    public static final String FIELD_MOBILE_PHONE = "mobilePhone";
    public static final String MISSING_MOBILE_PHONE = "missingMobilePhoneMessage";
    public static final String INVALID_MOBILE_PHONE = "invalidMobilePhoneMessage";

    @Override
    public void buildPage(FormContext context, LoginFormsProvider form) {
        /*AuthenticatorConfigModel captchaConfig = context.getAuthenticatorConfig();
        if (captchaConfig == null || captchaConfig.getConfig() == null
                || captchaConfig.getConfig().get(SITE_KEY) == null
                || captchaConfig.getConfig().get(SITE_SECRET) == null
                ) {
            form.addError(new FormMessage(null, Messages.RECAPTCHA_NOT_CONFIGURED));
            return;
        }
        String siteKey = captchaConfig.getConfig().get(SITE_KEY);
        form.setAttribute("recaptchaRequired", true);
        form.setAttribute("recaptchaSiteKey", siteKey);
        form.addScript("https://www.google.com/recaptcha/api.js");*/
    }

    @Override
    public void validate(ValidationContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        List<FormMessage> errors = new ArrayList<>();

        if (Validation.isBlank(formData.getFirst((FIELD_MOBILE_PHONE)))) {
            errors.add(new FormMessage(FIELD_MOBILE_PHONE, MISSING_MOBILE_PHONE));
        }

        String pattern = "\\+(\\d){9}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(formData.getFirst((FIELD_MOBILE_PHONE)));
        if (!m.find()) {
            errors.add(new FormMessage(FIELD_MOBILE_PHONE, INVALID_MOBILE_PHONE));
        }

        if (errors.size() > 0) {
            context.validationError(formData, errors);
            return;

        } else {
            context.success();
        }
    }

    @Override
    public void success(FormContext context) {
        UserModel user = context.getUser();
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        List<String> attributes = Arrays.asList(formData.getFirst(FIELD_MOBILE_PHONE));
        user.setAttribute(FIELD_MOBILE_PHONE, attributes);
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true; // PSPS je nakonfigurovany pri registracii? ano!
    }

    @Override
    public boolean requiresUser() {
        return false; 
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {        
        // PSPS sem pride v buducnosti overenie t.c. SMS
    }

    public void close() {
    }

}
