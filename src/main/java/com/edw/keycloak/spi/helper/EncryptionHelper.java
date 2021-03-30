package com.edw.keycloak.spi.helper;

import java.util.Base64;

/**
 * <pre>
 *     com.edw.keycloak.spi.helper.EncryptionHelper
 * </pre>
 *
 * Dont use this, this is a sample encryption only and not for production use.
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 30 Mar 2021 17:49
 */
public class EncryptionHelper {

    private static final String KEY = "12345678";

    /**
     * sample decryption, do not use it for production
     *
     * @param password
     * @return decrypted password
     */
    public static final String decrypt(String password) {
        try {
            return xorMessage(new String(Base64.getDecoder().decode(password.getBytes())));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return "";
        }
    }

    /**
     * sample encryption, do not use it for production
     *
     * @param password
     * @return encrypted password
     */
    public static final String encrypt(String password) {
        try {
            return Base64.getEncoder().encodeToString(xorMessage(password).getBytes());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return "";
        }
    }

    private static String xorMessage(String message) {
        try {
            if (message == null) return null;

            char[] keys = KEY.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char)(mesg[i] ^ keys[i % kl]);
            }
            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }

}
