import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        List<Post> posts = getObjectsFromHttp(REMOTE_SERVICE_URI);
        if (!posts.isEmpty()) {
            List<Post> postsFilteredByVotes = filterByVotes(posts);
            if (!postsFilteredByVotes.isEmpty()) {
                printUpvoteNotNull(postsFilteredByVotes);
            } else System.out.println("Нет фактов, у которых upvotes не равно null");
        } else System.out.println("Нет фактов");

    }

    private static List<Post> getObjectsFromHttp(String httpUrl) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        // создание объекта запроса
        HttpGet request = new HttpGet(httpUrl);
        try {
            // отправка запроса
            CloseableHttpResponse response = httpClient.execute(request);
            //получаем все объекты, преобразования json в java
            List<Post> posts = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Post>>() {
            });
            return posts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static List<Post> filterByVotes(List<Post> posts) {
        //фильтруем по кол-ву голосов
        return posts.stream()
                .filter((x) -> x.getUpvotes() > 0)
                .collect(Collectors.toList());
    }

    private static void printUpvoteNotNull(List<Post> posts) {
        posts.stream().forEach(System.out::println);
    }
}
