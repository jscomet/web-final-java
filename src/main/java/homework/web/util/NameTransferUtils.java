package homework.web.util;

/**
 * @author L.K.
 */
public class NameTransferUtils {
    public static String camelToSnake(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        // studentId
        // student_id
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                stringBuilder.append("_").append(Character.toLowerCase(c));
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static String snakeToCamel(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean nextUpper = false;
        for (char c : str.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
                continue;
            }
            if (nextUpper) {
                stringBuilder.append(Character.toUpperCase(c));
                nextUpper = false;
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
}
