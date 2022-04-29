import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
	public static final String PATH = "/Users/rusimac/Documents/IdeaProjects/pcs-jd-diplom/pdfs";

	public static void main(String[] args) throws Exception {
		int port = 8989;
		System.out.println("Сервер запущен...");
		while (true) {
			ServerSocket serverSocket = new ServerSocket(port);
			Socket connection = serverSocket.accept();

			PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			out.println("Введите слово для поиска по документам:");
			boolean listenPort = true;
			while (listenPort) {
				final String word = in.readLine();
				if (word != null && word.equals("#")) {
					out.println("Всего доброго!");
					listenPort = false;
					continue;
				}
				BooleanSearchEngine engine = new BooleanSearchEngine(new File(PATH));
				List<PageEntry> pageEntryList = engine.search(word);
				if (!pageEntryList.isEmpty()) {
					for (PageEntry pageEntry : pageEntryList) {
						GsonBuilder gsonBuilder = new GsonBuilder();
						Gson gson = gsonBuilder.setPrettyPrinting().create();
						out.println(gson.toJson(pageEntry));
					}
				} else {
					out.println("Проверены все документы. Такого слова в них нет.");
				}
				out.println("end");
			}

			serverSocket.close();
		}
	}
}