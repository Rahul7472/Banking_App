package com.gmb.inventory.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection conn;
    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Client Section
     */
    public ResultSet getClientData(String pAddress, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+pAddress+"' AND Password='"+password+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Admin section
     */

    public ResultSet getAdminData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE Username='"+username+"' AND Password='"+password+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    /**
     * Utility methods
     */

    public ResultSet getCheckingAccountData(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner='"+pAddress+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet getSavingsAccountData(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public int getLastClientsID() {
        Statement statement;
        ResultSet resultSet;
        int id = 0;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
            id = resultSet.getInt("seq");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void createClient(String fName, String lName, String pAddress, String password, LocalDate date) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO "+
            "Clients (FirstName, LastName, PayeeAddress, Password, Date)" +
             "VALUES('"+fName+"','"+lName+"','"+pAddress+"','"+password+"','"+date.toString()+"');");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void createCheckingAccount(String owner, String accountNumber, double transactionLimit, double balance) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "CheckingAccounts (Owner, AccountNumber, TransactionLimit, Balance)" +
                    "VALUES('"+owner+"','"+accountNumber+"',"+transactionLimit+","+balance+");");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void createSavingsAccount(String owner, String accountNumber, double wLimit, double balance) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "SavingsAccounts (Owner, AccountNumber, WithdrawalLimit, Balance)" +
                    "VALUES('"+owner+"','"+accountNumber+"',"+wLimit+","+balance+");");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllClients() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet searchClient(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+pAddress+"'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void depositSavings(String pAddress, double amount) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE SavingsAccounts SET BALANCE="+amount+" WHERE Owner='"+pAddress+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTransactions(String pAddress, int limit) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Transactions WHERE Sender='"+pAddress+"' OR Receiver='"+pAddress+"' LIMIT "+limit+";");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
