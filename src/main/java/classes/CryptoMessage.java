package classes;


import java.io.BufferedReader;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import org.bouncycastle.util.Arrays;

public class CryptoMessage {

public String criptografa(String keyIn, String plainText, String textoCifrado, boolean isDecrypt)
		throws DecoderException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException,
		BadPaddingException, IllegalBlockSizeException {
	byte[] K = Hex.decodeHex(keyIn.toCharArray());
	byte[] P = Hex.decodeHex(plainText.toCharArray());
	byte[] N = Hex.decodeHex("cafebabefacedbaddecaf888".toCharArray());

	textoCifrado = textoCifrado + "b094dac5d93471bdec1a502270e3cc6c";
	byte[] C = org.apache.commons.codec.binary.Hex.decodeHex(textoCifrado.toCharArray());

	Key key;
	Cipher in, out;


	in = Cipher.getInstance("AES/GCM/NoPadding", "BCFIPS");
	out = Cipher.getInstance("AES/GCM/NoPadding", "BCFIPS");

	key = new SecretKeySpec(K, "AES");
	if(!isDecrypt) {
		in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(N));
		return  Hex.encodeHexString(in.doFinal(P));
	} else {
		out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(N));
		return Hex.encodeHexString(out.doFinal(C));
	}


}





}
