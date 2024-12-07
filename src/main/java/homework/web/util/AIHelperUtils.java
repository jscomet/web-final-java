package homework.web.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AIHelperUtils {
    
    private static final String ACCESS_TOKEN = "24.eb417cb37ffa093ff69f8fec8ef6bf82.2592000.1735657327.282335-116474815";
    
    /**
     * AI分析评论内容
     * @param content 需要分析的评论内容
     * @return AI分析结果
     */
    public static String aiAnalyse(String content) {
        // 构建请求URL
        String apiUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-3.5-128k-preview";
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("access_token", ACCESS_TOKEN)
                .build()
                .toUri();

        // 构建请求体
        String requestBody = "{\"messages\": [{\"role\": \"user\", \"content\": \"" + 
                           "你是一个聪明的ai，如果你是教师，请你从这段话中提炼要点,不需要分段。" +
                           content + "\"}]}";

        // 封装参数
        HttpRequestUtils.Params params = new HttpRequestUtils.Params();
        params.setUrl(uri.toString());
        params.setContentType("application/json");
        params.setDataEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        params.setHeaders(List.of(new BasicHeader("Content-Type", "application/json")));

        try {
            // 发送请求
            HttpResponse response = HttpRequestUtils.request(HttpMethod.POST, params);
            String responseBody = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

            // 解析 JSON 响应
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("result").asText();
        } catch (IOException e) {
            System.out.println("AI分析调用异常: " + e.getMessage());
            return "AI分析失败";
        }
    }
}
