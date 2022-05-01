import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
	public static final String PATH = "/Users/rusimac/Documents/IdeaProjects/pcs-jd-diplom/pdfs";

	public static void main(String[] args) throws Exception {
		int port = 8989;
		while (true) {
			ServerSocket serverSocket = new ServerSocket(port);
			Socket connection = serverSocket.accept();

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			DataInputStream in = new DataInputStream(connection.getInputStream());

			String word = in.readUTF();
			BooleanSearchEngine engine = new BooleanSearchEngine(new File(PATH));
			List<PageEntry> pageEntryList = engine.search(word);
			for (PageEntry pageEntry : pageEntryList) {
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.setPrettyPrinting().create();
				out.writeUTF(gson.toJson(pageEntry));
			}
			out.writeUTF("end");
			out.flush();
			in.close();
			out.close();
			connection.close();
			serverSocket.close();
		}
	}
}