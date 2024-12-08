package homework.web.util;

import static org.junit.jupiter.api.Assertions.*;

class AIHelperUtilsTest {
    @org.junit.jupiter.api.Test
    void getAiMove() {
        String result = AIHelperUtils.aiAnalyse(
                "这句话描述了一场桌球比赛的结果，一方以 3:0的比分完胜另一方，" +
                        "其中获胜的一方在这场比赛中被称为“我”(假设)，而失败的一方是 bob。" +
                        "说明在这场桌球比赛中，“我”占据了绝对优势以较大的比分差距战胜了 bob。"
        );
        System.out.println(result);
    }
}