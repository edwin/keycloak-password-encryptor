package com.edw.keycloak.spi;

import com.edw.keycloak.spi.helper.EncryptionHelper;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordUserCredentialModel;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * <pre>
 *     com.edw.keycloak.spi.CustomKeycloakPasswordEncryptor
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 30 Mar 2021 11:54
 */
public class CustomKeycloakPasswordEncryptor implements Authenticator {

    public void authenticate(AuthenticationFlowContext authenticationFlowContext) {

        // not bringing username
        if(authenticationFlowContext.getHttpRequest().getFormParameters().get("username") == null
                || authenticationFlowContext.getHttpRequest().getFormParameters().get("username").isEmpty()) {

            Response challenge =  Response.status(400)
                    .entity("{\"error\":\"invalid_request\",\"error_description\":\"No Username\"}")
                    .header("Content-Type", "application/json")
                    .build();
            authenticationFlowContext.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }

        // not bringing password
        if(authenticationFlowContext.getHttpRequest().getFormParameters().get("password") == null
                || authenticationFlowContext.getHttpRequest().getFormParameters().get("password").isEmpty()) {

            Response challenge =  Response.status(400)
                    .entity("{\"error\":\"invalid_request\",\"error_description\":\"No Password\"}")
                    .header("Content-Type", "application/json")
                    .build();
            authenticationFlowContext.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }

        // capture username
        String username = authenticationFlowContext.getHttpRequest().getFormParameters().getFirst("username").trim();

        // search for corresponding user
        List<UserModel> userModels = authenticationFlowContext.getSession().users().searchForUser(username, authenticationFlowContext.getRealm());

        // user not exists
        if(userModels.isEmpty()) {
            Response challenge =  Response.status(400)
                    .entity("{\"error\":\"invalid_request\",\"error_description\":\"User Not Found\"}")
                    .header("Content-Type", "application/json")
                    .build();
            authenticationFlowContext.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }

        // capture usermodel, means user is exist
        UserModel userModel = userModels.get(0);

        // capture password and dont forget to html-decode the content (im using a string replacement for this example)
        String password = authenticationFlowContext.getHttpRequest().getFormParameters().getFirst("password").trim();
        password = password.replace("%3D", "=");

        // decrypt the password
        password = EncryptionHelper.decrypt(password);

        // password is incorrect
        PasswordUserCredentialModel credentialInput = UserCredentialModel.password(password);
        boolean valid = authenticationFlowContext.getSession().userCredentialManager().isValid(authenticationFlowContext.getRealm(),
                                                                                                userModel,
                                                                                                new PasswordUserCredentialModel[]{credentialInput} );
        if( !valid ) {
            Response challenge =  Response.status(400)
                    .entity("{\"error\":\"invalid_request\",\"error_description\":\"User Not Found\"}")
                    .header("Content-Type", "application/json")
                    .build();
            authenticationFlowContext.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
            return;
        }

        // set user
        authenticationFlowContext.setUser(userModel);

        // all validation success
        authenticationFlowContext.success();
    }

    public void action(AuthenticationFlowContext authenticationFlowContext) {
        authenticationFlowContext.success();
    }

    public boolean requiresUser() {
        return false;
    }

    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    public void close() {

    }
}
