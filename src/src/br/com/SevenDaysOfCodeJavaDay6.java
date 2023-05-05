package src.br.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SevenDaysOfCodeJavaDay6 {

	public static void main(String[] args) throws Exception {

		System.out.println("Chamando API");
		String apiKey = "k_8t0a3y0z";
		ImdbApiClient apiClient = new ImdbApiClient(apiKey);
		String json = apiClient.getBody();
		System.out.println(json);
		
		System.out.println("Parsing do JSON");
		JsonParser jsonParser = new ImdbMovieJsonParser(json);
		List<? extends Content> contentList = jsonParser.parse();
	
		System.out.println("Gerando HTML");
		PrintWriter writer = new PrintWriter("content.html");
		new HtmlGenerator(writer).generate(contentList);
		writer.close();
	}
}

//interfaces

interface Content {
	String title();
	String urlImage();
	String rating();
	String year();
}

interface JsonParser {
	List<? extends Content> parse();
}

interface ApiClient {
	String getBody();
}

//modelo implementando Content

record Movie(String title, String urlImage, String rating, String year) implements Content {
	
	@Override
	public int compareTo(Content c) {
		return this.rating().compareTo(c.rating());
	}
}

//ImdbMovieJsonParser implementado a nova interface


