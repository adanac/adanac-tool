package com.adanac.tool.j2se.des;

@SuppressWarnings("restriction")
public abstract class Coder {

	/**
	 * BASE64
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {

		// return (new BASE64Decoder()).decodeBuffer(key);
		return null;
	}

	/**
	 * BASE64
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		// return (new BASE64Encoder()).encodeBuffer(key);
		return null;
	}

}
