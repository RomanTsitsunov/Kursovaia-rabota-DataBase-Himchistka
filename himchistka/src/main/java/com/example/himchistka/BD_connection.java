package com.example.himchistka;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;
public class BD_connection
{
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/химчистка";
    public static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
    //public static Statement st;//Запрос/выражение
    //public static ResultSet res;//Указатель на запись таблицы
    public static Connection get_connection() throws SQLException {

        try
        {
            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
            //Указываем где находится драйвер
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace(); // обработка ошибки  Class.forName

            //Alert alert=new Alert(Alert.AlertType.INFORMATION);
            //alert.setContentText("JDBC драйвер для СУБД не найден!");
            //alert.show();

            System.out.println("JDBC драйвер для СУБД не найден!");
        }
        Connection con = DriverManager.getConnection(DB_URL, "root", "19922099");
        //соединениесБД
        //Указываем джи ди би с потом субд потом - localhost:адрес хоста соединения с субд,
        // а затем название БД к которой подключаемся

        //Alert alert=new Alert(Alert.AlertType.INFORMATION);
        //alert.setContentText("Соединение с СУБД выполнено!");
        //alert.show();

        //System.out.println("Соединение с СУБД выполнено.");
        //st=con.createStatement();
        //st.execute("alter table Supply_line " +
        //"add primary key (id_supply, id_consum_material)");
            /*
            st.execute("insert into Klient values (1, 'Arkadiy', 7)");
            res=st.executeQuery("select * from Klient");
            for(;res.next();)
            {
                System.out.print(res.getInt(1));
                System.out.print(res.getString(2));
                System.out.print(res.getString(3));
                System.out.println();
            };
            System.out.print(res.getInt(1));
            System.out.print(res.getString(2));
            System.out.print(res.getInt(3));
            */
        //con.close();       // отключение от БД
        //System.out.println("Отключение от СУБД выполнено.");
        return con;
    }
}
