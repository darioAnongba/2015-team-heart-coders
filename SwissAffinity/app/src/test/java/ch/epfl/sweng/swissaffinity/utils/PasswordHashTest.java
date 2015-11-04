package ch.epfl.sweng.swissaffinity.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class Password Hash
 *
 * Created by dario on 19.10.2015.
 */
public class PasswordHashTest {

    @Test
    public void testPasswordValidation() throws Exception{
        for(int i = 0; i < 100; i++)
        {
            String password = ""+i;
            String hash = PasswordHash.createHash(password);
            String secondHash = PasswordHash.createHash(password);
            if(hash.equals(secondHash)) {
                fail("TWO HASHES ARE EQUAL!");
            }
            String wrongPassword = ""+(i+1);
            if(PasswordHash.validatePassword(wrongPassword, hash)) {
                fail("WRONG PASSWORD ACCEPTED!");
            }
            if(!PasswordHash.validatePassword(password, hash)) {
                fail("GOOD PASSWORD NOT ACCEPTED!");
            }
        }
    }
}