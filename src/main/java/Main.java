import org.apache.commons.codec.DecoderException;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class Main {
	public static void main(String[] a) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException, DecoderException {
		Scanner s = new Scanner(System.in);
Client client = new Client();

		System.out.println("Para cadastrar um novo usuário digite - 1");
		System.out.println("Para acessar uma conta digite - 2");

		int option = s.nextInt();
		switch (option){
		case 1:
			client.saveUser();
			System.out.println("teste");
		case 2:
			client.login();
			System.out.println("teste 2");
		}



	}
}
