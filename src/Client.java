import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.time.LocalDate;
import java.util.Scanner;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;

public class Client {

	private static final PBKDF2UtilBCFIPS pbkdf2UtilBCFIPS = new PBKDF2UtilBCFIPS();
	private Server server = new Server();
	public void saveUsuario() {
		Scanner s = new Scanner(System.in);

		System.out.println("Digite o nome do usuário:");
		String username = s.nextLine().trim();
		System.out.println("Digite o nome do usuário");
		String password = s.nextLine().trim();

	}

	public void saveUser() {
		Scanner s = new Scanner(System.in);
		// Instanciar um novo Security provider
		int addProvider;
		addProvider = Security.addProvider(new BouncyCastleFipsProvider());



		System.out.println("Digite o nome do usuário:");
		String username = s.nextLine().trim();
		System.out.println("Digite o nome do usuário");
		String password = s.nextLine().trim();
		String salt= "";
		try{
			salt = pbkdf2UtilBCFIPS.getSalt();
		}catch (NoSuchAlgorithmException e) {
			System.out.println("Erro na geração do salt");

		}
		String token = pbkdf2UtilBCFIPS.generateDerivedKey(password, salt, 100);
		UserAuth userAuth  = new UserAuth(username, token, LocalDate.now());

		server.login(userAuth);


	}
}