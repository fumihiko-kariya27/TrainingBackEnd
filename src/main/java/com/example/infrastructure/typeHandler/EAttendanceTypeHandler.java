package com.example.infrastructure.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.example.domain.training.course.EAttendance;

@MappedTypes(EAttendance.class)
public class EAttendanceTypeHandler extends BaseTypeHandler<EAttendance> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, EAttendance parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getCode());
	}

	@Override
	public EAttendance getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return EAttendance.getByCode(rs.getString(columnName));
	}

	@Override
	public EAttendance getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return EAttendance.getByCode(rs.getString(columnIndex));
	}

	@Override
	public EAttendance getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return EAttendance.getByCode(cs.getString(columnIndex));
	}

}
