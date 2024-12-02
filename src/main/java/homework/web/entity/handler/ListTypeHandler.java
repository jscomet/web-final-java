package homework.web.entity.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedTypes({List.class})
public class ListTypeHandler<T> extends BaseTypeHandler<List<T>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<T> o, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(o));
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String json = resultSet.getString(s);
        if (resultSet.wasNull()) {
            return null;
        }
        return parse(json);
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String json = resultSet.getString(i);
        if (resultSet.wasNull()) {
            return null;
        }
        return parse(json);
    }

    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String json = callableStatement.getString(i);
        if (callableStatement.wasNull()) {
            return null;
        }
        return parse(json);
    }

    private List<T> parse(String json) throws SQLException {
        try {
            TypeReference<T> typeReference = this.typeReference();
            Type type = typeReference != null ? typeReference.getType() : Object.class;
            return JSON.parseArray(json,type);
        } catch (JSONException e) {
            throw new SQLException("JSON 转换 List 失败", e);
        }
    }

    protected TypeReference<T> typeReference() {
        return null;
    }
}
