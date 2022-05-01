import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws IOException {
		String host = "localhost";
		int port = 8989;

		Socket clientSocket = new Socket(host, port);
		DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
		DataInputStream in = new DataInputStream(clientSocket.getInputStream());

		out.writeUTF("развитие");

		boolean readAnswer = true;
		while (readAnswer) {
			String response = in.readUTF();
			if (response.equals("end")) {
				readAnswer = false;
				continue;
			}
			System.out.println(response);
		}

	}
}
