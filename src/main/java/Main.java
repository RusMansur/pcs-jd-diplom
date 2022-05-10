import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
	public static final String PATH = "/Users/rusimac/Documents/IdeaProjects/pcs-jd-diplom/pdfs";

	public static void main(String[] args) {
		int port = 8989;
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			boolean runServer = true;
			while (runServer) {
				try (Socket connection = serverSocket.accept();
					 BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					 PrintWriter out = new PrintWriter(connection.getOutputStream(), true)) {

					String word = in.readLine();
					BooleanSearchEngine engine = new BooleanSearchEngine(new File(PATH));
					List<PageEntry> pageEntryList = engine.search(word);
					for (PageEntry pageEntry : pageEntryList) {
						GsonBuilder gsonBuilder = new GsonBuilder();
						Gson gson = gsonBuilder.setPrettyPrinting().create();
						out.println(gson.toJson(pageEntry));
					}
					out.println("end");
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}