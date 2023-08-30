package com.example.himchistka.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.himchistka.BD_connection;
import com.example.himchistka.OpenNewPanel;
import com.example.himchistka.controller.TablesData.Orders.OrdersController;
import com.example.himchistka.controller.TablesData.Payment.PaymentController;
import com.example.himchistka.controller.TablesData.ProductAccessories.ProductAccessoriesController;
import com.example.himchistka.controller.TablesData.ProductDefect.ProductDefectController;
import com.example.himchistka.controller.TablesData.ProductOrder.ProductOrderController;
import com.example.himchistka.controller.TablesData.ProductPollution.ProductPollutionController;
import com.example.himchistka.controller.TablesData.ProductProcessing.ProductProcessingController;
import com.example.himchistka.controller.TablesData.Storage.StorageController;
import com.example.himchistka.controller.TablesData.Supply.SupplyController;
import com.example.himchistka.controller.TablesData.SupplyLine.SupplyLineController;
import com.example.himchistka.controller.TablesSpravochnici.PriceList.PriceListController;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem Accessories_MenuItem;

    @FXML
    private AnchorPane Anchor;

    @FXML
    private MenuItem Colour_MenuItem;

    @FXML
    private MenuItem Contract_MenuItem;

    @FXML
    private MenuItem Contract_Report;

    @FXML
    private MenuItem Customer_MenuItem;

    @FXML
    private MenuItem Defect_MenuItem;

    @FXML
    private MenuItem Employer_MenuItem;

    @FXML
    private MenuItem Exit_MenuItem;

    @FXML
    private MenuItem Marking_MenuItem;

    @FXML
    private MenuItem Material_MenuItem;

    @FXML
    private MenuItem OrderUrgency_MenuItem;

    @FXML
    private MenuItem Orders_MenuItem;

    @FXML
    private MenuItem Orders_Report;

    @FXML
    private MenuItem Pollution_MenuItem;

    @FXML
    private MenuItem PriceList_MenuItem;

    @FXML
    private MenuItem ProductType_MenuItem;

    @FXML
    private MenuItem ReceivingIssuingPoint_MenuItem;

    @FXML
    private MenuItem Service_MenuItem;

    @FXML
    private MenuItem SignOut_MenuItem;

    @FXML
    private MenuItem Storage_MenuItem;

    @FXML
    private MenuItem Supplier_MenuItem;

    @FXML
    private MenuItem Supply_MenuItem;

    public static boolean admin;

    @FXML
    void initialize()
    {
        if(!MainController.admin)
        {
            Service_MenuItem.setVisible(false);
            ProductType_MenuItem.setVisible(false);
            Supplier_MenuItem.setVisible(false);
            Employer_MenuItem.setVisible(false);
            Supply_MenuItem.setVisible(false);
        }
        Accessories_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Accessories/Accessories-Panel.fxml",
                            "Фурнитура");
                }
        );

        Colour_MenuItem.setOnAction(event ->
            {
                OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Colour/Colour-Panel.fxml",
                        "Цвета");
            }
        );
        /*
        ContractStatus_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/ContractStatus/ContractStatus-Panel.fxml",
                            "Статус договора");
                }
        );
         */
        Contract_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Contract/Contract-Panel.fxml",
                            "Договор");
                }
        );
        /*
        CustomerType_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/CustomerType/CustomerType-Panel.fxml",
                            "Тип заказчика");
                }
        );
        */
        Customer_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Customer/Customer-Panel.fxml",
                            "Заказчики");
                }
        );

        Defect_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Defect/Defect-Panel.fxml",
                            "Дефекты");
                }
        );

        Employer_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Employer/Employer-Panel.fxml",
                            "Сотрудники");
                }
        );

        Marking_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Marking/Marking-Panel.fxml",
                            "Маркировка");
                }
        );

        Material_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Material/Material-Panel.fxml",
                            "Материал");
                }
        );
        /*
        OrderStatus_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/OrderStatus/OrderStatus-Panel.fxml",
                            "Статус заказа");
                }
        );
        */
        OrderUrgency_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Urgency/Urgency-Panel.fxml",
                            "Срочность заказа");
                }
        );

        Orders_MenuItem.setOnAction(event ->
                {
                    if (OrdersController.numbercontract>0)
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle(null);
                        alert.setContentText("Окно заказы уже открыто");
                        alert.showAndWait();
                        return;
                    }
                    OrdersController.numbercontract=-2;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Orders/Orders-Panel.fxml",
                            "Заказы");
                }
        );
        /*
        Payment_MenuItem.setOnAction(event ->
                {
                    PaymentController.numberorder=-1;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Payment/Payment-Panel.fxml",
                            "Платежи");
                }
        );
        */
        Pollution_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Pollution/Pollution-Panel.fxml",
                            "Загрязнения");
                }
        );

        PriceList_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/PriceList/PriceList-Panel.fxml",
                            "Прайс-лист");
                }
        );
        /*
        ProductAccessories_MenuItem.setOnAction(event ->
                {
                    ProductAccessoriesController.numberproduct=-1;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductAccessories/ProductAccessories-Panel.fxml",
                            "Фурнитура изделия");
                }
        );

        ProductDefect_MenuItem.setOnAction(event ->
                {
                    ProductDefectController.numberproduct=-1;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductDefect/ProductDefect-Panel.fxml",
                            "Дефекты изделия");
                }
        );

        ProductOrder_MenuItem.setOnAction(event ->
                {
                    ProductOrderController.numberorder=-1;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductOrder/ProductOrder-Panel.fxml",
                            "Изделия заказов");
                }
        );

        ProductPollution_MenuItem.setOnAction(event ->
                {
                    ProductPollutionController.numberproduct=-1;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductPollution/ProductPollution-Panel.fxml",
                            "Загрязнения изделия");
                }
        );

        ProductProcessing_MenuItem.setOnAction(event ->
                {
                    ProductProcessingController.numberproduct=-1;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/ProductProcessing/ProductProcessing-Panel.fxml",
                            "Обработка изделий");
                }
        );
        */
        ProductType_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/ProductType/ProductType-Panel.fxml",
                            "Виды изделий");
                }
        );

        ReceivingIssuingPoint_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/ReceivingIssuingPoint/ReceivingIssuingPoint-Panel.fxml",
                            "Пункты приема-выдачи заказов");
                }
        );

        Service_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Service/Service-Panel.fxml",
                            "Услуги");
                }
        );

        Storage_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Storage/Storage-Panel.fxml",
                            "Склад");
                }
        );

        Supplier_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesSpravochnici/Supplier/Supplier-Panel.fxml",
                            "Поставщики");
                }
        );
        /*
        SupplyLine_MenuItem.setOnAction(event ->
                {
                    SupplyLineController.numbersupply=-1;
                    OpenNewPanel.open("/com/example/himchistka/TablesData/SupplyLine/SupplyLine-Panel.fxml",
                            "Партия поставки");
                }
        );
        */
        Supply_MenuItem.setOnAction(event ->
                {
                    OpenNewPanel.open("/com/example/himchistka/TablesData/Supply/Supply-Panel.fxml",
                            "Поставка");
                }
        );

        Contract_Report.setOnAction(event ->
                {
                    int i, n;
                    int[] id_contract;
                    Document document = new Document();
                    try {
                        PdfWriter.getInstance(document, new FileOutputStream("E:\\Флешка\\" +
                                "Курсовая Бд\\Приложение\\himchistka\\src\\main\\resources\\Договора.pdf"));
                    } catch (DocumentException | FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();
                    document.addTitle("Отчет об активных договорах");
                    String FONT = "arial.ttf";
                    Font font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    Connection con;
                    try {
                        Paragraph paragraph=new Paragraph("Отчет об активных договорах\n\n", font);
                        paragraph.getFont().setSize(20);
                        document.add(paragraph);
                        paragraph.getFont().setSize(12);
                        con = BD_connection.get_connection();
                        Statement st = con.createStatement();
                        ResultSet res = st.executeQuery("select count(*) from contract " +
                                "where contract.id_status=1");
                        res.next();
                        n = res.getInt("count(*)");
                        id_contract = new int[n];
                        st = con.createStatement();
                        String s;
                        res = st.executeQuery("select contract.id_contract from " +
                                "contract where contract.id_status=1 order by id_contract asc");
                        for (i = 0; res.next(); i++)
                            id_contract[i] = res.getInt("id_contract");
                        for (i = 0; i < n; i++) {
                            s = "№ Договора: " + id_contract[i] + "\n";
                            st = con.createStatement();
                            res = st.executeQuery("select customer.id_type from customer, contract " +
                                    "where customer.id_customer=contract.id_customer and " +
                                    "contract.id_contract=" + id_contract[i]);
                            res.next();
                            if (res.getInt("id_type") == 1) {
                                st = con.createStatement();
                                res = st.executeQuery("select customer.organisation_name from customer, " +
                                        "contract where customer.id_customer=contract.id_customer and " +
                                        "contract.id_contract=" + id_contract[i]);
                                res.next();
                                s += "Заказчик: " + res.getString("organisation_name") + "\n";
                            } else {
                                st = con.createStatement();
                                res = st.executeQuery("select customer.surname, customer.name, " +
                                        "customer.otchestvo from customer, contract " +
                                        "where customer.id_customer=contract.id_customer and " +
                                        "contract.id_contract=" + id_contract[i]);
                                res.next();
                                s += "Заказчик: " + res.getString("surname") + " " + res.getString("name") + " " +
                                        res.getString("otchestvo") + "\n";
                            }

                            st = con.createStatement();
                            res = st.executeQuery("select contract_status.name from contract, " +
                                    "contract_status where contract.id_status=contract_status.id_status " +
                                    "and contract.id_contract=" + id_contract[i]);
                            res.next();
                            s += "Статус договора: " + res.getString("name") + "\n";

                            st = con.createStatement();
                            res = st.executeQuery("select contract.date_begin from contract where " +
                                    "contract.id_contract=" + id_contract[i]);
                            res.next();
                            s += "Дата начала договора: " + res.getString("date_begin") + "\n";

                            st = con.createStatement();
                            res = st.executeQuery("select contract.date_end from contract where " +
                                    "contract.id_contract=" + id_contract[i]);
                            res.next();
                            s += "Дата окончания договора: " + res.getString("date_end") + "\n";
                            s += "____________________________________";
                            paragraph = new Paragraph(s, font);
                            document.add(paragraph);
                        }
                        Date date=new Date();
                        SimpleDateFormat datenow = new SimpleDateFormat("yyyy.MM.dd");
                        s=datenow.format(date);
                        paragraph = new Paragraph(s, font);
                        document.add(paragraph);
                        document.close();
                        con.close();
                        Process p = Runtime
                                .getRuntime()
                                .exec("rundll32 url.dll,FileProtocolHandler E:\\Флешка\\" +
                        "Курсовая Бд\\Приложение\\himchistka\\src\\main\\resources\\Договора.pdf");
                    } catch (SQLException | DocumentException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Orders_Report.setOnAction(event ->
                {
                    int i, n;
                    int[] id_order;
                    Document document = new Document();
                    try {
                        PdfWriter.getInstance(document, new FileOutputStream("E:\\Флешка\\" +
                                "Курсовая Бд\\Приложение\\himchistka\\src\\main\\resources\\Заказы.pdf"));
                    } catch (DocumentException | FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();
                    document.addTitle("Отчет о заказах, заключенных за прошедший год");
                    String FONT = "arial.ttf";
                    Font font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    Connection con;
                    float sum,paid_sum,total_sum=0;
                    String s;
                    try {
                        Paragraph paragraph=new Paragraph("Отчет о заказах, заключенных за прошедший год\n\n", font);
                        paragraph.getFont().setSize(20);
                        document.add(paragraph);
                        paragraph.getFont().setSize(12);
                        con = BD_connection.get_connection();
                        Statement st = con.createStatement();
                        ResultSet res = st.executeQuery("select count(*) from orders " +
                                "where datediff(sysdate(),date_begin)<=365");
                        res.next();
                        n = res.getInt("count(*)");
                        id_order = new int[n];
                        st = con.createStatement();
                        res = st.executeQuery("select id_order from orders " +
                                "where datediff(sysdate(),date_begin)<=365 " +
                                "order by id_order asc");
                        for (i = 0; res.next(); i++)
                            id_order[i] = res.getInt("id_order");
                        for (i = 0; i < n; i++) {
                            s = "№ Заказа: " + id_order[i] + "\n";
                            st = con.createStatement();
                            res = st.executeQuery("select orders.id_contract, " +
                                    "employer.login as employer, urgency.name as urgency, orders.id_receiving_point, " +
                                    "orders.id_issuing_point, order_status.name as status, orders.sum, " +
                                    "orders.paid_sum, orders.date_begin, orders.date_ready, orders.date_issue " +
                                    "from orders, employer, urgency, order_status " +
                                    "where orders.tabel_number=employer.tabel_number and " +
                                    "orders.id_urgency=urgency.id_urgency and " +
                                    "orders.id_status=order_status.id_status and " +
                                    "orders.id_order="+id_order[i]);
                            res.next();
                            sum=res.getFloat("sum");
                            paid_sum=res.getFloat("paid_sum");
                            if(paid_sum>=sum)
                                total_sum+=sum;
                            else
                                total_sum+=paid_sum;
                            s+="№ Договора: "+res.getInt("id_contract")+"\n";
                            s+="Сотрудник: "+res.getString("employer")+"\n";
                            s+="Срочность: "+res.getString("urgency")+"\n";
                            s+="№ Пункта выдачи: "+res.getInt("id_receiving_point")+"\n";
                            s+="№ Пункта приема: "+res.getInt("id_issuing_point")+"\n";
                            s+="Статус: "+res.getString("status")+"\n";
                            s+="Сумма заказа: "+sum+"\n";
                            s+="Оплаченная сумма: "+paid_sum+"\n";
                            s+="Дата приема: "+res.getString("date_begin")+"\n";
                            s+="Дата готовности: "+res.getString("date_ready")+"\n";
                            s+="Дата выдачи: "+res.getString("date_issue")+"\n";

                            s += "____________________________________";
                            paragraph = new Paragraph(s, font);
                            document.add(paragraph);
                        }
                        s="Общий доход со всех заказов, заключенных за прошедший год: "+total_sum+" р.\n";
                        Date date=new Date();
                        SimpleDateFormat datenow = new SimpleDateFormat("yyyy.MM.dd");
                        s+=datenow.format(date);
                        paragraph = new Paragraph(s, font);
                        document.add(paragraph);
                        document.close();
                        con.close();
                        Process p = Runtime
                                .getRuntime()
                                .exec("rundll32 url.dll,FileProtocolHandler E:\\Флешка\\" +
                                        "Курсовая Бд\\Приложение\\himchistka\\src\\main\\resources\\Заказы.pdf");
                    } catch (SQLException | DocumentException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        SignOut_MenuItem.setOnAction(event ->
        {
            Anchor.getScene().getWindow().hide();
            OpenNewPanel.open("Login-Panel.fxml",
                    "Вход");
        }
        );

        Exit_MenuItem.setOnAction(event ->
        {
            Anchor.getScene().getWindow().hide();
        }
        );

    }

}