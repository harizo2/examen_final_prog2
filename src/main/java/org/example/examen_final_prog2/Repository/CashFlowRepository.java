package main.java.org.example.examen_final_prog2.Repository;

import main.java.org.example.examen_final_prog2.modul.CashFlow;
import main.java.org.example.examen_final_prog2.modul.Donation;
import main.java.org.example.examen_final_prog2.modul.Expense;
import main.java.org.example.examen_final_prog2.modul.Frequency;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CashFlowRepository {
    private final DataBaseConnection dataBaseConnection;

    public CashFlowRepository(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    public List<CashFlow> findByType(String type) {
        List<CashFlow> cashFlows = new ArrayList<>();

        String sql = "SELECT id, created_at, amount, type, comment, reason, frequency " +
                "FROM cash_flows WHERE type = ?";

        ResultSet resultSet = dataBaseConnection.connectionDB(sql, type);

        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    cashFlows.add(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture du ResultSet : " + e.getMessage());
        }

        return cashFlows;
    }

    private CashFlow mapRow(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        Timestamp createdAtTs = resultSet.getTimestamp("created_at");
        var createdAt = createdAtTs != null ? createdAtTs.toInstant() : null;
        BigDecimal amount = resultSet.getBigDecimal("amount");
        String type = resultSet.getString("type");

        if ("donation".equalsIgnoreCase(type)) {
            String comment = resultSet.getString("comment");
            return new Donation(id, createdAt, amount, comment);
        } else if ("expense".equalsIgnoreCase(type)) {
            String reason = resultSet.getString("reason");
            String frequencyStr = resultSet.getString("frequency");
            Frequency frequency = frequencyStr != null
                    ? Frequency.valueOf(frequencyStr)
                    : Frequency.NONE;
            return new Expense(id, createdAt, amount,  frequency, reason);
        }

        throw new IllegalStateException("Type de cash-flow inconnu : " + type);
    }
}
