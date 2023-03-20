import com.mysql.cj.jdbc.Driver;
import java.sql.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Random;

public class New_weapon {

    public static String id_item = ""; //auto count
    public static String name = ""; //setter
    public static String id_item_type = "";
    public static String damage = "";
    public static String id_dmg_type = "";
    public static String description = ""; //setter
    public static String gold_cost = "";

    public static Connection connection;
    public static int num_traits = 0;
    //traits all will be setters
    public static String trait_1 = "";
    public static String trait_2 = "";
    public static String trait_3 = "";

    public static String trait_1_data = "";
    public static String trait_2_data = "";
    public static String trait_3_data = "";

    public static String[] sql_colums = {"N/A", "item_ammunition", "item_finesse", "item_versatile", "item_light", "item_loading", "item_range", "item_reach", "item_magic"
            ,"item_thrown", "item_improvised", "item_cosmetic", "items_legendary" };

    public New_weapon(String item_type_data, String damage_data, String dmg_type_data, String gp_data, int num_data_traits){

        id_item_type = item_type_data;
        damage = damage_data;
        id_dmg_type = dmg_type_data;
        gold_cost = gp_data;
        num_traits = num_data_traits;
    }

    public void Set_connection(Connection X){
        connection = X;
        id_item = Get_IDNUM();

    }

    public void Set_Name(String name_data){
        name = name_data;

    }

    public void Set_Descp(String Desc_data){
        description = Desc_data;
    }

    public void Set_Traits_names(String data_1, String data_2, String data_3){
        trait_1 = data_1;
        trait_2 = data_2;
        trait_3 = data_3;

    }

    public void Set_Traits_data(String data_1, String data_2, String data_3){
        trait_1_data = data_1;
        trait_2_data = data_2;
        trait_3_data = data_3;
    }

    public String Get_IDNUM(){

        String id_data = "0";
        try {
            String sql_id = "select Count(*) from items";
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql_id);

            while(results.next()){
                id_data = results.getString("Count(*)");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int final_id = Integer.valueOf(id_data) + 1;

        return String.valueOf(final_id);
    }

    public void Insert(){


        PreparedStatement statement  = null;
        PreparedStatement statement2 = null;

        String sql = "INSERT INTO items(id_item, name, id_item_type, damage, id_dmg_type, description, gold_cost)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ";
        String sql_2 = "";


        try {

            if(num_traits == 1)
            {
                sql_2 = "insert into " + sql_colums[Integer.valueOf(id_item_type)] + "(id_item, name, id_item_type, damage, id_dmg_type, " +
                        trait_1 + ", description, gold_cost)"
                        + "Values (?, ?, ?, ?, ?, ?, ?, ?)";

                statement2 = connection.prepareStatement(sql_2);
                statement2.setString(6, trait_1_data);
                statement2.setString(7, description);
                statement2.setString(8, gold_cost);
            }
            else if(num_traits == 2)
            {
                sql_2 = "insert into " + sql_colums[Integer.valueOf(id_item_type)] + "(id_item, name, id_item_type, damage, id_dmg_type, " +
                        trait_1 +  ", " + trait_2 + ", description, gold_cost)"
                        + "Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                statement2 = connection.prepareStatement(sql_2);
                statement2.setString(6, trait_1_data);
                statement2.setString(7, trait_2_data);
                statement2.setString(8, description);
                statement2.setString(9, gold_cost);
            }
            else if(num_traits == 3)
            {
                sql_2 = "insert into " + sql_colums[Integer.valueOf(id_item_type)] + "(id_item, name, id_item_type, damage, id_dmg_type, " +
                        trait_1 +  ", " + trait_2 + ", " + trait_3 + ", description, gold_cost)"
                        + "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                statement2 = connection.prepareStatement(sql_2);
                statement2.setString(6, trait_1_data);
                statement2.setString(7, trait_2_data);
                statement2.setString(8, trait_3_data);
                statement2.setString(9, description);
                statement2.setString(10, gold_cost);
            }
            else{
                System.out.println("THERE WAS AN ERROR!!");
            }

            statement = connection.prepareStatement(sql);
            statement.setString(1, id_item);
            statement.setString(2, name);
            statement.setString(3, id_item_type);
            statement.setString(4, damage);
            statement.setString(5, id_dmg_type);
            statement.setString(6, description);
            statement.setString(7, gold_cost);

            statement2.setString(1, id_item);
            statement2.setString(2, name);
            statement2.setString(3, id_item_type);
            statement2.setString(4, damage);
            statement2.setString(5, id_dmg_type);




            int rows = statement.executeUpdate();
            if(rows > 0){
                System.out.println("rows beeen inserted");
            }
            int rows2 = statement2.executeUpdate();
            if(rows2 > 0){
                System.out.println("rows beeen inserted");
            }

            statement.close();
            statement2.close();
            id_item = Get_IDNUM();

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }





}
