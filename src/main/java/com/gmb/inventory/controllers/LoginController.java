package com.gmb.inventory.controllers;

import com.gmb.inventory.models.Model;
import com.gmb.inventory.views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label email_address_lbl;
    public TextField email_address_field;
    public Label password_lbl;
    public TextField password_field;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT,AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));
        login_btn.setOnAction(event -> onLogin());
        acc_selector.valueProperty().addListener(observable -> setAccountSelector());
    }

    private void onLogin(){
        Stage stage = (Stage)error_lbl.getScene().getWindow();
        if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
            //Evaluate login credentials
            Model.getInstance().evaluateClientCred(email_address_field.getText(),password_field.getText());
            if(Model.getInstance().isClientLoginSuccessFlag()) {
                Model.getInstance().getViewFactory().showClientWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                email_address_field.setText("");
                password_field.setText("");
                error_lbl.setText("No Such Login Credentials.");
            }
        } else {
            // Evaluate admin login credentials
            Model.getInstance().evaluateAdminCred(email_address_field.getText(),password_field.getText());
            if(Model.getInstance().getAdminLoginSuccessFlag()) {
                Model.getInstance().getViewFactory().showAdminWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                email_address_field.setText("");
                password_field.setText("");
                error_lbl.setText("No Such Login Credentials.");
            }

        }
    }

    private void setAccountSelector() {
        Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue());
        if(acc_selector.getValue().equals(AccountType.ADMIN)) {
            email_address_lbl.setText("Username: ");
        } else {
            email_address_lbl.setText("Email Address: ");
        }
    }
}
