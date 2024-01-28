package com.gmb.inventory.models;

import com.gmb.inventory.views.AccountType;
import com.gmb.inventory.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class Model {
    private final ViewFactory viewFactory;

    private static Model model;

    private final DatabaseDriver databaseDriver;

    //Client data section
    private Client client;

    private boolean clientLoginSuccessFlag;

    private boolean adminLoginSuccessFlag;


    private final ObservableList<Client> clients;

    private final ObservableList<Transaction> latestTransactions;

    private final ObservableList<Transaction> allTransactions;

    //Admin Data Section
    private Model() {
        this.databaseDriver = new DatabaseDriver();
        this.viewFactory = new ViewFactory();
        //Client data section
        this.clientLoginSuccessFlag = false;
        this.adminLoginSuccessFlag = false;
        this.client = new Client("","","", null, null, null);
        //Admin data section
        this.clients = FXCollections.observableArrayList();
        this.latestTransactions = FXCollections.observableArrayList();
        this.allTransactions = FXCollections.observableArrayList();

    }
    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    /**
     * Client method section
     */
    public boolean isClientLoginSuccessFlag() {
        return clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean clientLoginSuccessFlag) {
        this.clientLoginSuccessFlag = clientLoginSuccessFlag;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void evaluateClientCred(String pAddress, String password) {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getClientData(pAddress, password);
        try{
            if(resultSet.isBeforeFirst()) {
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]),Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.client.dateCreatedProperty().set(date);
                this.setClientLoginSuccessFlag(true);
                checkingAccount = getCheckingAccount(pAddress);
                savingsAccount = getSavingsAccount(pAddress);
                this.client.checkingAccountProperty().set(checkingAccount);
                this.client.savingsAccountProperty().set(savingsAccount);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Admin method section
     */
    public boolean getAdminLoginSuccessFlag() { return adminLoginSuccessFlag; }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public void evaluateAdminCred(String username, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(username, password);
        try{
            if(resultSet.isBeforeFirst()) {
                this.setAdminLoginSuccessFlag(true);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients() {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getAllClients();
        try {
            while(resultSet.next()) {
                String fName = resultSet.getString("FirstName");
                String lName = resultSet.getString("LastName");
                String pAddress = resultSet.getString("PayeeAddress");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                checkingAccount = getCheckingAccount(pAddress);
                savingsAccount = getSavingsAccount(pAddress);
                clients.add(new Client(fName, lName, pAddress,checkingAccount, savingsAccount, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility methods
     */
    public CheckingAccount getCheckingAccount(String pAddress) {
        CheckingAccount account = null;
        ResultSet resultSet = databaseDriver.getCheckingAccountData(pAddress);
        try {
            String num = resultSet.getString("AccountNumber");
            int tLimit = (int) resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            account = new CheckingAccount(pAddress, num, balance, tLimit);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public SavingsAccount getSavingsAccount(String pAddress) {
        SavingsAccount account = null;
        ResultSet resultSet = databaseDriver.getSavingsAccountData(pAddress);
        try {
            String num = resultSet.getString("AccountNumber");
            int wLimit = (int) resultSet.getDouble("WithdrawalLimit");
            double balance = resultSet.getDouble("Balance");
            account = new SavingsAccount(pAddress, num, balance, wLimit);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public ObservableList<Client> searchClient(String pAddress) {
        ObservableList<Client> searchResults = FXCollections.observableArrayList();
        ResultSet resultSet = databaseDriver.searchClient(pAddress);
        try {
            CheckingAccount checkingAccount;
            SavingsAccount savingsAccount;
            String fName = resultSet.getString("FirstName");
            String lName = resultSet.getString("LastName");
            String[] dateParts = resultSet.getString("Date").split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
            checkingAccount = getCheckingAccount(pAddress);
            savingsAccount = getSavingsAccount(pAddress);
            searchResults.add(new Client(fName, lName, pAddress,checkingAccount, savingsAccount, date));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    private void prepareTransactions(ObservableList<Transaction> transactions, int limit) {
        ResultSet resultSet = databaseDriver.getTransactions(this.client.payeeAddressProperty().get(), limit);
        try {
            while(resultSet.next()) {
                String sender = resultSet.getString("Sender");
                String receiver = resultSet.getString("Receiver");
                double amount = resultSet.getDouble("Amount");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                String message = resultSet.getString("Message");
                transactions.add(new Transaction(sender, receiver, amount, date, message));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setLatestTransactions() {
        prepareTransactions(this.latestTransactions, 4);
    }

    public ObservableList<Transaction> getLatestTransactions() {
        return latestTransactions;
    }

    public void setAllTransactions() {
        prepareTransactions(this.allTransactions, -1);
    }

    public ObservableList<Transaction> getAllTransactions() {
        return allTransactions;
    }
}
