import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Hex;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

import javax.crypto.spec.SecretKeySpec;
import java.security.KeyStore;

public class Server {
	ScryptExample obj = new ScryptExample();

	final static String storeFile = "store.bcfks";
//boucen castle
	static byte[] derivedKeyFromScrypt;

	public Server(){

	}
	public void saveUser(UserAuth userAuth, String masterPasswordToken) throws KeyStoreException, NoSuchProviderException, CertificateException, IOException, NoSuchAlgorithmException, DecoderException {

		// Instanciar um novo Security provider
		// Nesse exemplo eh usado o BC padrao
		int addProvider;
		addProvider = Security.addProvider(new BouncyCastleFipsProvider());
		if (Security.getProvider("BCFIPS") == null) {
			System.out.println("Bouncy Castle provider NAO disponivel");
		} else {
			System.out.println("Bouncy Castle provider esta disponivel");
		}

		 byte[] salt = null;
		 String valorSalt = "53efb4b1157fccdb9902676329debc52";

		 try {
		 salt = Hex.decodeHex(valorSalt.toCharArray());
		 } catch (DecoderException ex) {

		 }


//		byte[] salt= null;
//		try{
//			salt = obj.getSalt();
//		}catch (NoSuchAlgorithmException e) {
//			System.out.println("Erro na geração do salt");
//
//		}
		int costParameter = 2048; // exemplo: 2048 (afeta uso de memória e CPU)

		int blocksize = 8; // exemplo: 8

		int parallelizationParam = 1; // exemplo: 1


		derivedKeyFromScrypt = SCRYPT.useScryptKDF(userAuth.getToken().toCharArray(), salt,
				costParameter,
				blocksize, parallelizationParam);



		KeyStore ks = null;

		ks = KeyStore.getInstance("BCFKS", "BCFIPS");
		// Cria do zero o keystore
		ks.load(null, null);
		// Armazena a senha mestre do keystore
		ks.store(new FileOutputStream(storeFile), masterPasswordToken.toCharArray());

		Key keyUser = new SecretKeySpec(Hex.decodeHex(userAuth.getToken().toCharArray()), "AES");
		System.out.println("SecretKeySpec: "+keyUser);

		ks.load(new FileInputStream(storeFile), masterPasswordToken.toCharArray());
		ks.setKeyEntry(userAuth.getName(), keyUser, null,  null);
		ks.store(new FileOutputStream(storeFile), masterPasswordToken.toCharArray());

		System.out.println("Chave derivada usando scrypt: ");
		System.out.println(Hex.encodeHexString(derivedKeyFromScrypt));

	}
	public void login(UserLogin userLogin)  {
		String saltString = "53efb4b1157fccdb9902676329debc52";

		int addProvider;
		addProvider = Security.addProvider(new BouncyCastleFipsProvider());
		if (Security.getProvider("BCFIPS") == null) {
			System.out.println("Bouncy Castle provider NAO disponivel");
		} else {
			System.out.println("Bouncy Castle provider esta disponivel");
		}

		PBKDF2UtilBCFIPS pbkdf2UtilBCFIPS = new PBKDF2UtilBCFIPS();
//		try{
//			 // salt = pbkdf2UtilBCFIPS.getSalt().getBytes();
//		}catch (NoSuchAlgorithmException e) {
//			System.out.println("Erro na geração do salt");
//
//		}
		String token = pbkdf2UtilBCFIPS.generateDerivedKey(userLogin.getPassword(), saltString, 100);






		// buscar do arquivo de código
		byte[] salt = null;

		try {
			salt = Hex.decodeHex(saltString.toCharArray());
		} catch (DecoderException ex) {
		}


		//		byte[] salt= null;
		//		try{
		//			salt = obj.getSalt();
		//		}catch (NoSuchAlgorithmException e) {
		//			System.out.println("Erro na geração do salt");
		//
		//		}
		int costParameter = 2048; // exemplo: 2048 (afeta uso de memória e CPU)

		int blocksize = 8; // exemplo: 8

		int parallelizationParam = 1; // exemplo: 1


		String senhaSalva = "";
		byte[] derivedKeyFromScrypt;
		derivedKeyFromScrypt = SCRYPT.useScryptKDF(token.toCharArray(), salt,
				costParameter,
				blocksize, parallelizationParam);


		System.out.println("Chave derivada usando scrypt: ");
		System.out.println(Hex.encodeHexString(derivedKeyFromScrypt));

		System.out.println("Chave banco: ");
		System.out.println(Hex.encodeHexString(derivedKeyFromScrypt));
	}
}
