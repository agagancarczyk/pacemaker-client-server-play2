package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class TokenGenerator {

   /*
    * Method allows to generate a token for a user.
	*/
	public static String nextToken() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
}