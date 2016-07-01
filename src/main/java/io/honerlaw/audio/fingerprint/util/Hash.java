package io.honerlaw.audio.fingerprint.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A utility class used to generate the SHA1 hash for a given string
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public class Hash {
	
	/**
	 * The message digest to hash the values
	 */
	public static MessageDigest SHA1;
	
	/**
	 * Initialize the message digest
	 */
	static {
		try {
			SHA1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the hash of an input stream
	 * 
	 * @param in The input stream to hash
	 * 
	 * @return The byte array containing the hash
	 * 
	 * @throws IOException
	 */
	public static final synchronized byte[] calculate(InputStream in) throws IOException {
		byte[] temp = new byte[in.available()];
		in.read(temp, 0, temp.length);
		SHA1.reset();
		SHA1.update(temp);
		return SHA1.digest();
	}

	/**
	 * Calculates the hash for a given value
	 * 
	 * @param value The value to hash
	 * 
	 * @return The byte array containing the hashed value
	 */
	public static final synchronized byte[] calculate(String value) {
		SHA1.reset();
		SHA1.update(value.getBytes());
		return SHA1.digest();
	}
	
	/**
	 * Converts a byte array to a hex string
	 * 
	 * @param block The byte array to convert
	 * 
	 * @return The string representing the byte array
	 */
	public static final String toHex(byte[] block) {
		StringBuffer buf = new StringBuffer();
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len = block.length;
		int high = 0;
		int low = 0;
		for (int i = 0; i < len; i++) {
			high = ((block[i] & 0xf0) >> 4);
			low = (block[i] & 0x0f);
			buf.append(hexChars[high]);
			buf.append(hexChars[low]);
		}
		return buf.toString();
	}
	
}
