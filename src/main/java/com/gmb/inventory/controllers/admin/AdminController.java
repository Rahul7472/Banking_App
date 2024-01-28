package com.gmb.inventory.controllers.admin;

import com.gmb.inventory.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.gmb.inventory.views.AdminMenuOptions.CLIENTS;
import static com.gmb.inventory.views.AdminMenuOptions.CREATE_CLIENT;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            // Add switch statements
            if (newVal.equals(CLIENTS)) {
                admin_parent.setCenter(Model.getInstance().getViewFactory().getClientsView());
            } else if(newVal.equals(CREATE_CLIENT)) {
                admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            } else {
                admin_parent.setCenter(Model.getInstance().getViewFactory().getDepositView());
            }
        });
    }
}
