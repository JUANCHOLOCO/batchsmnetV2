package com.millicom.gtc.batchfit.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class TestPlanPreparedStatementSetter implements ItemPreparedStatementSetter<String>{

	@Override
    public void setValues(String item, PreparedStatement ps) throws SQLException {
        ps.setString(1, item);
    }
}
