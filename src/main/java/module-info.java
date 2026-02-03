module org.example.guifakeuber {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires jakarta.json.bind;
    requires jakarta.xml.bind;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires java.management;

    opens controllers to javafx.fxml;
    opens files;
    opens utils;
    opens model.entity;
    opens model.entity.ENUM;
    opens model.entity.interfaces;
    opens model.entity.vehicles;
    opens model.entity.XML;
    opens model.entity.JSON;
    opens model.entity.exceptions;

    exports controllers;
    exports app;
    opens app to javafx.fxml;
}

