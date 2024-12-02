package homework.web.entity.handler;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonObjectTypeHandler extends BaseTypeHandler<JSONObject> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONObject o, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(o));
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String json = resultSet.getString(s);
        return parse(json);
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String json = resultSet.getString(i);
        return parse(json);
    }

    @Override
    public JSONObject getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String json = callableStatement.getString(i);
        return parse(json);
    }

    private JSONObject parse(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json);
    }
}
