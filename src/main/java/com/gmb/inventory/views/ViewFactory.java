package com.gmb.inventory.views;

import com.gmb.inventory.controllers.admin.AdminController;
import com.gmb.inventory.controllers.client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private AccountType loginAccountType;
    //Client views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;

    private AnchorPane dashboardView;
    private AnchorPane transactionsView;

    private AnchorPane accountsView;

    //Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane createClientView;

    public ViewFactory(){
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty getClientSelectedMenuItem() {
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


    public void showClientWindow() {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }
    //Admin views section
    public ObjectProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }
    public void showAdminWindow() {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/admin/admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    public void showLoginWindow() {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        createStage(loader);
    }

    public AnchorPane getCreateClientView() {
        if(createClientView == null) {
            try{
                createClientView = new FXMLLoader(getClass().getResource("/fxml/admin/CreateClient.fxml")).load();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createClientView;
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
    public AccountType getLoginAccountType() {
        return loginAccountType;
    }
    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }
}
