# keycloak-spi
Keycloak SPIs

Implementation of Mobile phone validation added to the registration form.

Installation:
0) use mvn package in registration-mobile-phone-action directory
1) copy target/registration-mobile-phone-action.jar into %keycloak%/providers, where %keycloak% is you Keycloak home.
2) add new language strings to your theme (no chaining possible? To be investigated):
  - missingMobilePhoneMessage
  - invalidMobilePhoneMessage
3) add mobile phone form input (name, and id = mobilePhone) to the register.ftl
3) restart Keycloak (for the jars to be loaded & templates to be transformed)
4) create new a copy of the registration flow in your realms Authentication
5) add the newly added provider to the flow
