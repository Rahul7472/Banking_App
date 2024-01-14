package com.gmb.inventory.controllers.client;

import com.gmb.inventory.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.gmb.inventory.views.ClientMenuOptions.ACCOUNTS;
import static com.gmb.inventory.views.ClientMenuOptions.TRANSACTIONS;

public class ClientController implements Initializable {

    public BorderPane client_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            if (newVal.equals(TRANSACTIONS)) {
                client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
            } else if (newVal.equals(ACCOUNTS)) {
                client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
            } else {
                client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
