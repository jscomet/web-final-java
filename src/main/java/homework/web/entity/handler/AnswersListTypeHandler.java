package homework.web.entity.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import homework.web.entity.dto.TestRecordCommitParam;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-11 17:13
 */
public class AnswersListTypeHandler extends ListTypeHandler<TestRecordCommitParam.Answer>{
    @Override
    protected TypeReference<TestRecordCommitParam.Answer> typeReference() {
        return new TypeReference<>() {
        };
    }
}
