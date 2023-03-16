package task3itra;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class GeneratingKeyHMACTest {


    @Test
    public void hmacWithJava() throws NoSuchAlgorithmException, InvalidKeyException {
        String key = "NB7E6XTGMDSOPKV5YIDM32IFIZ9J4D44";
        String algorithm = "HmacSHA256";
        String data = "veil";
        String value = "2e74263c98d396f43ae342f29b78c6dad6c14a7565dc45bb65098d18485313d4";
        assertEquals(value, GeneratingKeyHMAC.hmacWithJava(algorithm, data, key));

    }
}