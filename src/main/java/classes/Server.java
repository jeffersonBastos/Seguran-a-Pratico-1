package classes;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Hex;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyStore;


import sun.nio.cs.US_ASCII;

public class Server {
	ScryptExample obj = new ScryptExample();
	Example2fa example2fa = new Example2fa();
	private static final Client client = new Client();


	List<UserAuth> users = new ArrayList();
//boucen castle
	static byte[] derivedKeyFromScrypt;

	public Server(){

	}
	public void saveUser(UserAuth userAuth) throws KeyStoreException, NoSuchProviderException, CertificateException, IOException, NoSuchAlgorithmException, DecoderException {

		int addProvider;
		addProvider = Security.addProvider(new BouncyCastleFipsProvider());
		if (Security.getProvider("BCFIPS") == null) {
			System.out.println("Bouncy Castle provider NAO disponivel");
		} else {
			System.out.println("Bouncy Castle provider esta disponivel");
		}

		byte[] salt= null;
		try{
			salt = obj.getSalt();
		}catch (NoSuchAlgorithmException e) {
			System.out.println("Erro na geração do salt");

		}
		int costParameter = 2048; // exemplo: 2048 (afeta uso de memória e CPU)

		int blocksize = 8; // exemplo: 8

		int parallelizationParam = 1; // exemplo: 1


		derivedKeyFromScrypt = SCRYPT.useScryptKDF(userAuth.getToken().toCharArray(), salt,
				costParameter,
				blocksize, parallelizationParam);


		userAuth.setToken(Hex.encodeHexString(derivedKeyFromScrypt));
		userAuth.setSaltScrypt(salt);
		users.add(userAuth);
	}
	public void login(UserLogin userLogin)  {
		UserAuth userAuth = null;
		for (UserAuth user: users) {
			if(userLogin.getName().equals(user.getName())){
				userAuth = user;
				break;
			}
		}

		int addProvider;
		addProvider = Security.addProvider(new BouncyCastleFipsProvider());
		if (Security.getProvider("BCFIPS") == null) {
			System.out.println("Bouncy Castle provider NAO disponivel");
		} else {
			System.out.println("Bouncy Castle provider esta disponivel");
		}

		PBKDF2UtilBCFIPS pbkdf2UtilBCFIPS = new PBKDF2UtilBCFIPS();

		String token = pbkdf2UtilBCFIPS.generateDerivedKey(userLogin.getPassword(), userAuth.getSaltPBKDF2(), 1000);

		int costParameter = 2048; // exemplo: 2048 (afeta uso de memória e CPU)

		int blocksize = 8; // exemplo: 8

		int parallelizationParam = 1; // exemplo: 1

		byte[] derivedKeyFromScrypt;
		derivedKeyFromScrypt = SCRYPT.useScryptKDF(token.toCharArray(), userAuth.getSaltScrypt(),
				costParameter,
				blocksize, parallelizationParam);


		boolean chaveIgual = userAuth.getToken().equals(Hex.encodeHexString(derivedKeyFromScrypt));
		if(chaveIgual){

			try {

				// Cria chave secreta simétrica
				String secret = example2fa.generateSecretKey();
				System.out.println("Chave = " + secret);
				String TOTPcode = example2fa.getTOTPCode(secret);
				System.out.println("TOTP Code = " + TOTPcode);

				/**
				 // String secret = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";
				 String lastCode = null;
				 while (true) {
				 String code = getTOTPCode(secret);
				 if (!code.equals(lastCode)) {
				 System.out.println(code);
				 }
				 lastCode = code;
				 try {
				 Thread.sleep(1000);
				 } catch (InterruptedException e) {
				 };
				 }
				 * */

				String email = "email@gmail.com";
				String companyName = "Empresa";
				String barCodeUrl =  example2fa.getGoogleAuthenticatorBarCode(secret, email, companyName);
				System.out.println("Bar Code URL = " + barCodeUrl);

				int width = 246;
				int height = 246;

				// Fica no diretório do projeto.
				example2fa.createQRCode(barCodeUrl, "matrixURL.png", height, width);

				System.out.println("Procure o arquivo matrixCode.png no diretorio do projeto e leia o QR code para digitar o código");
				example2fa.createQRCode(TOTPcode, "matrixCode.png", height, width);

			String code = client.receive2FaCode();

				if (code.equals( example2fa.getTOTPCode(secret))) {
					String chaveSimetrica = pbkdf2UtilBCFIPS.generateDerivedKey(userLogin.getPassword(), code,1000);

					System.out.println("Logged in successfully");
				} else {
					System.out.println("Invalid 2FA Code");
				}
			} catch (Exception ex) {
				Logger.getLogger(Example2fa.class.getName()).log(Level.SEVERE, null, ex);
			}
		}


	}


//	public void TrocaMensagem( String code) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
//		int addProvider;
//		addProvider = Security.addProvider(new BouncyCastleFipsProvider());
//		String key;
//		String salt = "3cc7d30b346e71d4de4fcb0c62a90fba";
////		try{
////			salt = pbkdf2UtilBCFIPS.getSalt();
////		}catch (NoSuchAlgorithmException e) {
////			System.out.println("Erro na geração do salt");
////
////		}
//		String token = pbkdf2UtilBCFIPS.generateDerivedKey("1311", salt, 1000);
//
//
//
//
//		CryptoMessage cryptoMessage = new CryptoMessage();
//		try {
//			String msgcripto  = cryptoMessage.criptografa(token, "9313225f88406e5a55909c5aff5269a","", false);
//
//			String msgdecripto = cryptoMessage.criptografa(token, "9313225f88406e5a55909c5aff5269a",msgcripto, true);
//		} catch (DecoderException e) {
//			e.printStackTrace();
//		} catch (InvalidAlgorithmParameterException e) {
//			e.printStackTrace();
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//		} catch (BadPaddingException e) {
//			e.printStackTrace();
//		} catch (IllegalBlockSizeException e) {
//			e.printStackTrace();
//		}
//	}



}
