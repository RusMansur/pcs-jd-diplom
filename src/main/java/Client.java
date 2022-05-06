import java.io.*;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 8989;

		try (Socket clientSocket = new Socket(host, port)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

			out.println("бизнес");
			boolean readAnswer = true;
			while (readAnswer) {
				String response = in.readLine();
				if (response.equals("end")) {
					readAnswer = false;
					continue;
				}
				System.out.println(response);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
