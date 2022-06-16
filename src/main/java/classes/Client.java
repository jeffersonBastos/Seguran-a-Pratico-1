package classes;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.util.Scanner;

import org.apache.commons.codec.DecoderException;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

public class Client {

	private static final PBKDF2UtilBCFIPS pbkdf2UtilBCFIPS = new PBKDF2UtilBCFIPS();
	private  static final Server server = new Server();
	Scanner s = new Scanner(System.in);



	public void saveUser() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException, DecoderException {
		// Instanciar um novo Security provider
		int addProvider;
		addProvider = Security.addProvider(new BouncyCastleFipsProvider());



		System.out.println("Digite o nome do usuário:");
		String username = s.nextLine().trim();
		System.out.println("Digite a senha do usuário");
		String password = s.nextLine().trim();
//		String salt= "53efb4b1157fccdb9902676329debc52";
//
//		System.out.println("Digite a senha do mestre");
//		String masterPassword = s.nextLine().trim();
		String salt = "";
		try{
			salt = pbkdf2UtilBCFIPS.getSalt();
		}catch (NoSuchAlgorithmException e) {
			System.out.println("Erro na geração do salt");

		}
		String token = pbkdf2UtilBCFIPS.generateDerivedKey(password, salt, 1000);

		//		System.out.println("Senha usada:");
//		System.out.println(password);
//		System.out.println("salt usado:");
//		System.out.println(salt);
//
//		System.out.println("senha do mestre usado:");
//		System.out.println(masterPassword);


//		System.out.println("token gerado:");
//		System.out.println(token);

//		String masterPasswordToken = pbkdf2UtilBCFIPS.generateDerivedKey(password, salt, 100);
//		System.out.println("masterPasswordToken gerado:");
//		System.out.println(masterPasswordToken);

		UserAuth userAuth  = new UserAuth(username, token, LocalDate.now(), salt, null);

		server.saveUser(userAuth);


	}

	public void login() {
		// Instanciar um novo Security provider
		int addProvider;
		addProvider = Security.addProvider(new BouncyCastleFipsProvider());



		System.out.println("Digite o nome do usuário:");
		String username = s.nextLine().trim();
		System.out.println("Digite o nome do usuário");
		String password = s.nextLine().trim();

		UserLogin userLogin  = new UserLogin(username, password);

		server.login(userLogin);


	}

	public String receive2FaCode(){

		System.out.println("Digite o código recebido:");
		return s.nextLine().trim();
	}

}