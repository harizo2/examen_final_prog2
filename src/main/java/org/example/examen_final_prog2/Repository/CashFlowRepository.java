package main.java.org.example.examen_final_prog2.Repository;

import main.java.org.example.examen_final_prog2.Request.BalanceResponse;
import main.java.org.example.examen_final_prog2.Request.CreateExpenseRequest;
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

    public List<CashFlow> findByUserId(String userId) {
        List<CashFlow> cashFlows = new ArrayList<>();

        String sql = "SELECT id, created_at, amount, type, comment, reason, frequency " +
                "FROM cash_flows WHERE user_id = ?";

        ResultSet resultSet = dataBaseConnection.connectionDB(sql, userId);

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
    public BalanceResponse getBalance() {
        BigDecimal totalDonations = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        String sql = "SELECT type, COALESCE(SUM(amount), 0) AS total " +
                "FROM cash_flows GROUP BY type";

        ResultSet resultSet = dataBaseConnection.connectionDB(sql);

        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    BigDecimal total = resultSet.getBigDecimal("total");

                    if ("donation".equalsIgnoreCase(type)) {
                        totalDonations = total;
                    } else if ("expense".equalsIgnoreCase(type)) {
                        totalExpenses = total;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul du solde : " + e.getMessage());
        }

        BigDecimal balance = totalDonations.subtract(totalExpenses);
        return new BalanceResponse(totalDonations, totalExpenses, balance);
    }

    public Expense save(CreateExpenseRequest request) {
        String id = java.util.UUID.randomUUID().toString();
        String frequency = request.getFrequency() != null
                ? request.getFrequency().name()
                : Frequency.NONE.name();

        String sql = "INSERT INTO cash_flows (id, created_at, amount, type, user_id, reason, frequency) " +
                "VALUES (?, now(), ?, 'expense', ?, ?, ?) " +
                "RETURNING id, created_at, amount, reason, frequency";

        ResultSet resultSet = dataBaseConnection.connectionDB(
                sql,
                id,
                request.getAmount(),
                request.getUserId(),
                request.getReason(),
                frequency
        );

        try {
            if (resultSet != null && resultSet.next()) {
                return new Expense(
                        resultSet.getString("id"),
                        resultSet.getTimestamp("created_at").toInstant(),
                        resultSet.getBigDecimal("amount"),
                        Frequency.valueOf(resultSet.getString("frequency")),
                        resultSet.getString("reason")
                                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la dépense : " + e.getMessage());
        }

        return null;
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
