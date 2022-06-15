import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

public class Server {
	ScryptExample obj = new ScryptExample();

	public Server(){

	}
	public void saveUser(UserAuth userAuth)  {

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


		byte[] derivedKeyFromScrypt;
		derivedKeyFromScrypt = SCRYPT.useScryptKDF(userAuth.getToken().toCharArray(), salt,
				costParameter,
				blocksize, parallelizationParam);


		System.out.println("Chave derivada usando scrypt: ");
		System.out.println(Hex.encodeHexString(derivedKeyFromScrypt));

	}
	public void login(UserAuth userAuth)  {

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


		byte[] derivedKeyFromScrypt;
		derivedKeyFromScrypt = SCRYPT.useScryptKDF(userAuth.getToken().toCharArray(), salt,
				costParameter,
				blocksize, parallelizationParam);


		System.out.println("Chave derivada usando scrypt: ");
		System.out.println(Hex.encodeHexString(derivedKeyFromScrypt));

	}
}
