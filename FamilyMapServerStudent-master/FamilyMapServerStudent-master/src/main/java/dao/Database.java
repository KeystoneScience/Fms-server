package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection conn;

    public Database() {
    }

    public Connection openConnection() throws DataAccessException {
        try {
            String CONNECTION_URL = "jdbc:sqlite:familymap.db";
            this.conn = DriverManager.getConnection("jdbc:sqlite:familymap.db");
            this.conn.setAutoCommit(false);
        } catch (SQLException var2) {
            var2.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return this.conn;
    }

    public Connection getConnection() throws DataAccessException {
        return this.conn == null ? this.openConnection() : this.conn;
    }

    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                this.conn.commit();
            } else {
                this.conn.rollback();
            }

            this.conn.close();
            this.conn = null;
        } catch (SQLException var3) {
            var3.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void clearTables() throws DataAccessException {
        try {
            Statement stmt = this.conn.createStatement();

            try {
                String sql = "DELETE FROM event";
                stmt.executeUpdate(sql);
                sql = "DELETE FROM user";
                stmt.executeUpdate(sql);
                sql = "DELETE FROM person";
                stmt.executeUpdate(sql);


            } catch (Throwable var5) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Throwable var4) {
                        var5.addSuppressed(var4);
                    }
                }

                throw var5;
            }

            if (stmt != null) {
                stmt.close();
            }

        } catch (SQLException var6) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
