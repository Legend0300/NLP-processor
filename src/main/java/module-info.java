module com.example.dsa_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens com.example.dsa_project to javafx.fxml;
    exports com.example.dsa_project;
}