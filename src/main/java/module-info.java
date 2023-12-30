module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;

    opens com.gmb.inventory to javafx.fxml;
    exports com.gmb.inventory;
    exports com.gmb.inventory.controllers;
    exports com.gmb.inventory.controllers.admin;
    exports com.gmb.inventory.controllers.client;
    exports com.gmb.inventory.models;
    exports com.gmb.inventory.views;


}