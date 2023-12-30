package com.gmb.inventory.views;

import com.gmb.inventory.controllers.client.ClientController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    //Client views
    private final StringProperty clientSelectedMenuItem;

    private AnchorPane dashboardView;
    private AnchorPane transactionsView;

    private AnchorPane accountsView;
    public ViewFactory(){
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getTransactionsView() {
        if(transactionsView == null) {
            try {
                transactionsView = new FXMLLoader(getClass().getResource("/fxml/client/Transactions.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return transactionsView;
    }

    public AnchorPane getAccountsView() {
        if(accountsView == null) {
            try {
                accountsView = new FXMLLoader(getClass().getResource("/fxml/client/Accounts.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return accountsView;
    }

    public AnchorPane getDashboardView() {
        if(dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/client/Dashboard.fxml")).load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return dashboardView;
    }

    public void showLoginWindow() {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        createStage(loader);
    }

    public void showClientWindow() {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Accounts");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
