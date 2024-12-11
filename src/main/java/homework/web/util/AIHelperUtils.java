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
                           "你是一个聪明的ai，如果你是教师，作为一个专业的评论员，值给出你的分数，比如0-100，不需要任何东西" +
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
    /**
     * AI分析评论内容
     * @param content 需要分析的评论内容
     * @return AI分析结果
     */
    public static String replay(String content) {
        // 构建请求URL
        String apiUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-3.5-128k-preview";
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("access_token", ACCESS_TOKEN)
                .build()
                .toUri();

        // 构建请求体
        String requestBody = "{\"messages\": [{\"role\": \"user\", \"content\": \"" +
                "你是一个聪明的ai，你是一个讨论区的氛围机器人，你要针对别人的评论做出活跃气氛的回复。对于别人的评论，给出你的回复。注意！不要让人知道你的ai。注意！不要让人知道你的ai。注意！不要让人知道你的ai。" +
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
