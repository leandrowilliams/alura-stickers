import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        //String url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
//        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";

        API api = API.IMDB_TOP_SERIES;

        String url = api.getUrl();
        ExtratorDeConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

//        URI endereco = URI.create(url);
//        var client = HttpClient.newHttpClient();
//        var request = HttpRequest.newBuilder(endereco).GET().build();
//        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
//        String body = response.body();

//        // extrair só os dados que interessam (titulo, poster, classificação)
//        var parser = new JsonParser();
//        List<Map<String, String>> listaDeConteudos = parser.parse(json);

        // exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();

        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.urlImagem()).openStream();
            String nomeArquivo = "saida/" + conteudo.titulo() + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.titulo());
            System.out.println();

        }
    }
}