module com.example.himchistka {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires itextpdf;


    opens com.example.himchistka to javafx.fxml;
    exports com.example.himchistka;
    exports com.example.himchistka.controller;
    opens com.example.himchistka.controller to javafx.fxml;
    //exports com.example.himchistka.controller.TablesSpravochnici.CustomerType;
    //opens com.example.himchistka.controller.TablesSpravochnici.CustomerType to javafx.fxml;
    exports com.example.himchistka.models;
    opens com.example.himchistka.models to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.Customer;
    opens com.example.himchistka.controller.TablesData.Customer to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.Contract;
    opens com.example.himchistka.controller.TablesData.Contract to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.Orders;
    opens com.example.himchistka.controller.TablesData.Orders to javafx.fxml;
    //exports com.example.himchistka.controller.TablesSpravochnici.ContractStatus;
    //opens com.example.himchistka.controller.TablesSpravochnici.ContractStatus to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.ProductOrder;
    opens com.example.himchistka.controller.TablesData.ProductOrder to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.Payment;
    opens com.example.himchistka.controller.TablesData.Payment to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.ProductProcessing;
    opens com.example.himchistka.controller.TablesData.ProductProcessing to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Supplier;
    opens com.example.himchistka.controller.TablesSpravochnici.Supplier to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.Supply;
    opens com.example.himchistka.controller.TablesData.Supply to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.SupplyLine;
    opens com.example.himchistka.controller.TablesData.SupplyLine to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.Storage;
    opens com.example.himchistka.controller.TablesData.Storage to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.PriceList;
    opens com.example.himchistka.controller.TablesSpravochnici.PriceList to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.ProductType;
    opens com.example.himchistka.controller.TablesSpravochnici.ProductType to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Service;
    opens com.example.himchistka.controller.TablesSpravochnici.Service to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.Employer;
    opens com.example.himchistka.controller.TablesData.Employer to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.ProductAccessories;
    opens com.example.himchistka.controller.TablesData.ProductAccessories to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.ProductDefect;
    opens com.example.himchistka.controller.TablesData.ProductDefect to javafx.fxml;
    exports com.example.himchistka.controller.TablesData.ProductPollution;
    opens com.example.himchistka.controller.TablesData.ProductPollution to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Accessories;
    opens com.example.himchistka.controller.TablesSpravochnici.Accessories to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Defect;
    opens com.example.himchistka.controller.TablesSpravochnici.Defect to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Pollution;
    opens com.example.himchistka.controller.TablesSpravochnici.Pollution to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Colour;
    opens com.example.himchistka.controller.TablesSpravochnici.Colour to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Marking;
    opens com.example.himchistka.controller.TablesSpravochnici.Marking to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Material;
    opens com.example.himchistka.controller.TablesSpravochnici.Material to javafx.fxml;
    //exports com.example.himchistka.controller.TablesSpravochnici.OrderStatus;
    //opens com.example.himchistka.controller.TablesSpravochnici.OrderStatus to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.Urgency;
    opens com.example.himchistka.controller.TablesSpravochnici.Urgency to javafx.fxml;
    exports com.example.himchistka.controller.TablesSpravochnici.ReceivingIssuingPoint;
    opens com.example.himchistka.controller.TablesSpravochnici.ReceivingIssuingPoint to javafx.fxml;
}