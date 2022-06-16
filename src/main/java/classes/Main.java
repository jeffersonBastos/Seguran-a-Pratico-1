package classes;


import org.apache.commons.codec.DecoderException;

import java.io.IOException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class Main {
	public static void main(String[] a) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException, DecoderException {
		Scanner s = new Scanner(System.in);
		Client client = new Client();

		boolean encerra = false;
		int option = 1;
		while (option != 0) {
			System.out.println("Para cadastrar um novo usuário digite - 1");
			System.out.println("Para acessar uma conta digite - 2");

			option = s.nextInt();
			switch (option) {
			case 1:
				client.saveUser();
				break;
			case 2:
				client.login();
				break;
			default:
				option = 0;
			}
		}


	}
}
