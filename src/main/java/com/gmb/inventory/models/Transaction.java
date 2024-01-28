package com.gmb.inventory.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transaction {
    private final StringProperty sender;
    private final StringProperty receiver;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;

    public Transaction(String sender, String receiver, double amount, LocalDate date, String message) {
        this.sender = new SimpleStringProperty(this, "sender", sender);
        this.receiver = new SimpleStringProperty(this, "receiver", receiver);
        this.amount = new SimpleDoubleProperty(this,"amount", amount);
        this.date = new SimpleObjectProperty<>(this, "Date", date);
        this.message = new SimpleStringProperty(this, "Message", message);
    }
    public StringProperty senderProperty() {
        return sender;
    }

    public StringProperty receiverProperty() {
        return receiver;
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public StringProperty messageProperty() {
        return message;
    }
}
