package com.gmb.inventory.controllers.client;

import com.gmb.inventory.models.Model;
import com.gmb.inventory.views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView transactions_list_view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAllTransactionsList();
        transactions_list_view.setItems(Model.getInstance().getAllTransactions());
        transactions_list_view.setCellFactory(e -> new TransactionCellFactory());
    }

    private void initAllTransactionsList() {
        if(Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
    }
}
