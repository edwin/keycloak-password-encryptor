package com.edw.keycloak.spi.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <pre>
 *     com.edw.keycloak.spi.helper.EncryptionHelperTest
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 30 Mar 2021 17:58
 */
public class EncryptionHelperTest {

    @Test
    public void encryptTest() {
        String password = "password";
        String passwordEncrypted = EncryptionHelper.encrypt(password);
        Assertions.assertNotNull(passwordEncrypted);
        Assertions.assertEquals("QVNAR0JZRVw=", passwordEncrypted);
    }

    @Test
    public void decryptTest() {
        String password = "QVNAR0JZRVw=";
        String passwordDecrypted = EncryptionHelper.decrypt(password);
        Assertions.assertNotNull(passwordDecrypted);
        Assertions.assertEquals("password", passwordDecrypted);
    }

}
