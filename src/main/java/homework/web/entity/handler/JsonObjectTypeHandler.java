package homework.web.entity.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonObjectTypeHandler<T> extends BaseTypeHandler<T> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T o, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(o));
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String json = resultSet.getString(s);
        if (resultSet.wasNull()) {
            return null;
        }
        return parse(json);
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String json = resultSet.getString(i);
        if (resultSet.wasNull()) {
            return null;
        }
        return parse(json);
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String json = callableStatement.getString(i);
        if (callableStatement.wasNull()) {
            return null;
        }
        return parse(json);
    }

    private T parse(String json) throws SQLException {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        Type type = Object.class;
        try {
            TypeReference<T> typeReference = this.typeReference();
            if (typeReference != null) {
                type = typeReference.getType();
            }
            return JSON.parseObject(json, type);
        } catch (JSONException e) {
            throw new SQLException("JSON 转换" + type.getTypeName() + "失败", e);
        }
    }

    protected TypeReference<T> typeReference() {
        return null;
    }
}
