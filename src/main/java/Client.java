import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		String host = "localhost";
		int port = 8989;

		Socket clientSocket = new Socket(host, port);
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		String response = in.readLine();
		System.out.println(response);

		boolean connectEstablished = true;
		while (connectEstablished) {
			String request = input.nextLine();
			if (request.equals("#")) {
				out.println(request);
				System.out.println(in.readLine());
				connectEstablished = false;
				continue;
			}
			out.println(request);
			boolean readAnswer = true;
			while (readAnswer) {
				 response = in.readLine();
				if (response.equals("end")) {
					System.out.println("-Конец поиска-\nВведите ещё одно слово или '#', чтобы завершить");
					readAnswer = false;
					continue;
				}
				System.out.println(response);
			}
		}
		clientSocket.close();
	}
}
