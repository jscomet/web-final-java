package homework.web.util;

import static org.junit.jupiter.api.Assertions.*;

class AIHelperUtilsTest {
    @org.junit.jupiter.api.Test
    void getAiMove() {
        String content = "这是一段需要评分的评论内容:Java比php好用，只需要一个分数";
        String result = AIHelperUtils.aiAnalyse(content);
        assertNotNull(result, "AI分析结果不应为空");
        System.out.println(result);
    }

    @org.junit.jupiter.api.Test
    void testReplay() {
        String content = "Java这个课程真的好好玩呀，哈哈哈";
        String result = AIHelperUtils.replay(content);
        assertNotNull(result);
        System.out.println(result);
    }
    @org.junit.jupiter.api.Test
    void testAddress() {
        String content = "有学习编程的课程推荐嘛";
        String result = AIHelperUtils.replay(content);
        assertNotNull(result);
        System.out.println(result);
    }
}