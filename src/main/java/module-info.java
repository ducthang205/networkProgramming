module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires jdk.internal.vm.compiler;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.client;
    exports com.example.demo.server;
    opens com.example.demo.server to javafx.fxml;
}