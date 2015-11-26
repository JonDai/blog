package com.jondai.blog.util;

/**
 * Created by dongxg.
 * User: dongxg
 * Date: 2004-12-6
 * Time: 16:40:16
 */

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Encrypt {
    private PBEParameterSpec pbeParamSpec = null;
    private SecretKey pbeKey = null;
    private Cipher pbeCipher = null;

    public void init(String initParameter) throws Exception {                /*
                 * In order to use Password-Based Encryption (PBE) as defined in PKCS
                 * #5, we have to specify a salt and an iteration count. The same salt
                 * and iteration count that are used for encryption must be used for
                 * decryption:
                 */
        char[] passwd = {'1', '2', '3', '4', '5', '6'};
        pbeParamSpec = getParamSpec();
        pbeKey = createPBEKey(passwd);           // Create PBE Cipher
        pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
    }

    public byte[] Encrypt(byte[] enSource) throws Exception {
        // Initialize PBE Cipher with key and parameters
        pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
        // Encrypt the cleartext
        return pbeCipher.doFinal(enSource);
    }

    public byte[] Decrypt(byte[] deSource) throws Exception {
        // Initialize PBE Cipher with key and parameters
        pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

        // decrypt the cleartext
        return pbeCipher.doFinal(deSource);
    }

    /**
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private SecretKey createPBEKey(char [] passwd) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec pbeKeySpec;
        SecretKeyFactory keyFac;
        // Prompt user for encryption password.
        // Collect user password as char array (using the "readPasswd" method from above), and convert
        // it into a SecretKey object, using a PBE key
        // factory.
        pbeKeySpec = new PBEKeySpec(passwd);
        keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
        return pbeKey;
    }

    /**
     * @return
     */
    private PBEParameterSpec getParamSpec() {
        PBEParameterSpec pbeParamSpec;
        // Salt
        byte[] salt = {(byte) 0xc7, (byte) 0x73, (byte) 0x41, (byte) 0x8c, (byte) 0x7e, (byte) 0xc8, (byte) 0xea, (byte) 0x99};
        int count = 17;        // Iteration count
        // Create PBE parameter set
        pbeParamSpec = new PBEParameterSpec(salt, count);
        return pbeParamSpec;
    }
}
