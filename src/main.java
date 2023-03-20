import com.mysql.cj.jdbc.Driver;

import javax.print.attribute.standard.JobKOctets;
import javax.print.attribute.standard.RequestingUserName;
import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;


public class main

{
    public static String[] Sorting = {"N/A", "Name", "Weapon","Type", "Dmg", "GP" };
    public static String[] Type_Sorting = {"N/A", "Weapon Ammunition", "Finesse Weapon", "Versatile Weapon", "Light Weapon", "Loading Weapon", "Range Weapon", "Reach Weapon", "Magic Item", "Throwable", "Improvised Weapon", "Cosmetic Item", "Legendary"};
    public static String[] Dmg_Sorting = {"N/A", "Bludgeoning" , "Slashing" , "Piercing" , "Acid" , "Cold" , "Fire" , "Force" , "Lightning" , "Necrotic" , "Poison" , "Psychic" , "Radiant" , "Thunder"};
    public static String[] Level = {"Lv.0","Lv.1", "Lv.2", "Lv.3", "Lv.4", "Lv.5", "Lv.Max"};

    public static String URL = "jdbc:mysql://localhost:3306/dndarmory";
    public static String username = "JavaUser";
    public static String password = "Java";

    public static String[] sql_colums = {"N/A", "item_ammunition", "item_finesse", "item_versatile", "item_light", "item_loading", "item_range", "item_reach", "item_magic"
                                        ,"item_thrown", "item_improvised", "item_cosmetic", "items_legendary" };

    public static Connection connection;
    public static New_weapon insert_weapon = new New_weapon("", "", "","",0);


    public static void main(String[] args){

        try {
            connection = DriverManager.getConnection(URL, username, password);
            insert_weapon.Set_connection(connection);
            System.out.println("Connection established");

        } catch (SQLException throwables) {
            System.out.println("THE CODE COULD NOT CONNECT TO THE DATA BASE!");
            throwables.printStackTrace();
        }

        main_menu();
    }

    public static void main_menu() {
        JFrame Main;
        GUI Window = new GUI("Stay Unseen", 800, 850, Color.orange);
        Main = Window.Create_GUI();

        GridBagLayout a = new GridBagLayout();
        Main.setLayout(a);
        GBC gbc = new GBC();
        JButton Armory_button = new JButton("Armory");
        JButton Store_button = new JButton("Store");
        JButton Craft_button = new JButton("Craft");
        JButton Logout_button = new JButton("Logout");

        JLabel Title = new JLabel(new ImageIcon("pics/Title.png"));
        Main.add(Title);
        Main.add(Armory_button, gbc.gbc(0, 1, GridBagConstraints.BOTH, 300, 100, 0, 1));
        Main.add(Store_button, gbc.gbc(0, 2, GridBagConstraints.BOTH, 200, 100, 0, 2));
        Main.add(Craft_button, gbc.gbc(0, 3, GridBagConstraints.BOTH, 200, 100, 0, 2));
        Main.add(Logout_button, gbc.gbc(0, 4, GridBagConstraints.BOTH, 200, 100, 0, 2));

        JLabel Aurther = new JLabel("Created By: Orion G.");
        Aurther.setHorizontalAlignment(SwingConstants.RIGHT);
        Aurther.setVerticalAlignment(SwingConstants.TOP);
        Main.add(Aurther, gbc.gbc(0, 6, GridBagConstraints.BOTH, 200, 100, 0, 2));


        Armory_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.dispose();
                armory_screen();
            }
        });

        Store_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.dispose();
                store_screen();
            }
        });

        Craft_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.dispose();
                craft_screen();
            }
        });

        Main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main.setLocationRelativeTo(null); //set window in center of screen
        Main.setVisible(true);

    }
    //always shown
    public static Colum id = new Colum("~Item ID~", 0, "items");
    public static Colum name = new Colum("~Name~", 0, "items");
    public static Colum Damage = new Colum("~Dice~", 0, "items");
    public static Colum Dmg_Type = new Colum("~Dmg~", 0, "items");
    public static Colum Item_type = new Colum("~Category~", 0, "items");
    public static Colum GP = new Colum("~Gold Cost~", 0, "items");
    public static Colum Description = new Colum("~Description~" , 0, "items");

    public static Colum Weapon_ammo = new Colum("~Weapon Ammo~", 1, "item_ammunition"); //ammonition weapon
    public static Colum Finesse_stat = new Colum("~Finesses Stat~", 2, "item_finesse"); // Finness weapon
    public static Colum Crit_bonus = new Colum("~Crit Bonus~", 3, "item_versatile"); //Versatile
    public static Colum Weight = new Colum("~Weight (lbs)~", 3, "item_versatile"); //versatile
    public static Colum Atk_bonus = new Colum( "~Attack Bonus~", 4, "item_light"); //Light weapon
    public static Colum Ranges_Loading = new Colum("~Range~", 5, "item_loading"); //loading weapon
    public static Colum Reload_time = new Colum("~Reload Time~", 5, "item_loading"); //loading weapon
    public static Colum Ammo_type_L = new Colum("~Ammo Type~", 5, "item_loading"); //loading weapon
    public static Colum Ranges_range = new Colum("~Range~", 6, "item_range"); //range weapons
    public static Colum Ammo_type_R = new Colum("~Ammo Type~", 6, "item_range"); //range weapon
    public static Colum Reach_B = new Colum("~Reach Bonus~", 7, "item_reach"); //Reach WEapon
    public static Colum Throw_range = new Colum("~Thrown Range~", 7, "item_reach"); //reach weapon
    public static Colum Radius = new Colum("~Radius~", 8, "item_magic"); //magic weapon
    public static Colum Charges = new Colum("~Charges~", 8, "item_magic"); //magic weapon
    public static Colum Ranges_thrown = new Colum("~Range~", 9, "item_thrown"); //Throwable
    public static Colum Misc_object = new Colum("~Misc Object~", 10, "item_improvised"); //Improv weapon
    public static Colum Misc_bonus = new Colum("~Misc Bonus~", 10, "item_improvised");// Improve Weapon
    public static Colum Misc_breakable = new Colum("~Misc Breakable~", 10, "item_improvised"); //Improve WEapon
    public static Colum Action = new Colum("~Action~", 11, "item_cosmetic"); // cosmetic weapon
    public static Colum Legend = new Colum("~Legendary Action~", 12, "item_legendary"); //Legendary

    public static Colum[] ALL = {id, name, Damage, Dmg_Type, Item_type, Weapon_ammo, Finesse_stat, Crit_bonus, Weight, Atk_bonus, Ranges_Loading
            ,Reload_time, Ammo_type_L, Ranges_range, Ammo_type_R, Reach_B, Throw_range, Radius, Charges, Ranges_thrown, Misc_object, Misc_bonus
            , Misc_breakable, Action, Legend, GP, Description};

    public static void armory_screen(){
        JFrame Arm;
        GUI Window = new GUI("Stay Unseen", 1200, 850, Color.orange);
        Arm = Window.Create_GUI();

        GridBagLayout a = new GridBagLayout();
        Arm.setLayout(a);
        GBC gbc = new GBC();


        //Change these with loops from mysql

        JLabel Title = new JLabel(new ImageIcon("pics/Armory.png"));
        JButton Back = new JButton("Return");

        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        JScrollPane scroll = new JScrollPane(bottom);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        bottom.setBackground(Color.orange);

        JSplitPane Screen = new JSplitPane(SwingConstants.HORIZONTAL, top, scroll);
        top.setLayout(a);
        bottom.setLayout(a);

        JButton Search = new JButton("Search");
        JComboBox<String> Sort1 = new JComboBox<>(Sorting);
        JComboBox<String> Sort2 = new JComboBox<>(Type_Sorting);
        JComboBox<String> Sort3 = new JComboBox<>(Dmg_Sorting);
        JButton Cheats = new JButton("Cheats");

        Arm.add(Title, gbc.gbc(0, 1, GridBagConstraints.BOTH, 0, 0, 1, 0));
        Arm.add(Screen, gbc.gbc(0, 2, GridBagConstraints.BOTH, 0, 0, 1, 1));

        Insets spacer = new Insets(5, 10, 5, 10);
        top.add(Back, gbc.gbc(0, 1, GridBagConstraints.NONE, 50, 25, 0, 0, spacer));
        top.add(Search, gbc.gbc(1, 1, GridBagConstraints.HORIZONTAL, 75, 25, 0, 0, spacer));
        top.add(Sort1, gbc.gbc(2, 1, GridBagConstraints.HORIZONTAL, 100, 25, 0, 0, spacer));
        top.add(Sort2, gbc.gbc(3, 1, GridBagConstraints.HORIZONTAL, 100, 25, 0, 0, spacer));
        top.add(Sort3, gbc.gbc(4, 1, GridBagConstraints.HORIZONTAL, 100, 25, 0, 0, spacer));
        top.add(Cheats, gbc.gbc(5, 1, GridBagConstraints.HORIZONTAL, 100, 25, 0, 0, spacer));


        for(int x = 0; x < ALL.length; x++){
            bottom.add(ALL[x].getCol(), gbc.gbc(x, 0, GridBagConstraints.BOTH, 0, 0, 1, 1));
        }
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Arm.dispose();
                main_menu();
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for(int x = 0; x < ALL.length; x++){
                    ALL[x].Clean();
                }
                int r = Sort2.getSelectedIndex();
                int r2 = Sort3.getSelectedIndex();
                Search(r, r2);
                for(int x = 0; x < ALL.length; x++){
                    ALL[x].Update(r);
                }

            }
        });

        Cheats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passcode = JOptionPane.showInputDialog(Arm, "Please enter the password!");
                if(passcode.equals("Java24!")) {//passcode is Java24!
                    Cheat_insert();
                }
                else {
                    JOptionPane.showMessageDialog(Arm, "INCORRECT PASSCODE", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        Arm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Arm.setLocationRelativeTo(null); //set window in center of screen
        Arm.setVisible(true);
    }

    public static void Search(int item_type_sort, int item_dmg_sort){


        try {

            String SQL_Where = "";
            String SQL_And = "";
            String SQL_Where_2 = "";

            if(item_type_sort != 0){
                SQL_Where =  "where i.id_item_type = " + String.valueOf(item_type_sort);
                if(item_dmg_sort != 0){
                    SQL_And = " And ";
                    SQL_Where_2 = "i.id_dmg_type = " + String.valueOf(item_dmg_sort);
                }
            }

            if(item_dmg_sort != 0){
                SQL_Where =  "where i.id_dmg_type = " + String.valueOf(item_dmg_sort);
                if(item_type_sort != 0){
                    SQL_And = " And ";
                    SQL_Where_2 = "i.id_item_type = " + String.valueOf(item_type_sort);
                }
            }


            String sql = "select * \n" +
                    "from items i\n" +
                    "join dmg_type dmg on dmg.id_dmg_type = i.id_dmg_type\n" +
                    "join item_type ty on ty.id_item_type = i.id_item_type\n " +
                    SQL_Where + SQL_And + SQL_Where_2 + "\n" +
                    ";"; //order by function later!!!!!!!!!!!!!!!
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            while(results.next())
            {
                String id_data = results.getString("id_item"); //ID
                String name_data = results.getString("name"); // NAME
                String dmg_data = results.getString("damage"); // DICE
                String dmg_type_data = results.getString("damage_element"); // DMG TYPE
                String item_category_data = results.getString("item_category"); //item type
                String description_data = results.getString("description"); // description
                String gp_data = results.getString("gold_cost");

                id.Append(id_data);
                name.Append(name_data);
                Damage.Append(dmg_data);
                Dmg_Type.Append(dmg_type_data);
                Item_type.Append(item_category_data);
                Description.Append(description_data);
                GP.Append(gp_data);

            }

            statement.close();

            String Join = "";


            if(item_type_sort != 0) {

                String extra_sql = "select * from items i\n" +
                        "join " + sql_colums[item_type_sort] + " x on x.id_item = i.id_item;";
                Statement statement_item = connection.createStatement();
                ResultSet results_item = statement_item.executeQuery(extra_sql);




                while(results_item.next()){
                    String dmg_type_data = results_item.getString("id_dmg_type");
                    if(item_dmg_sort == Integer.valueOf(dmg_type_data) || item_dmg_sort == 0)
                    {
                    switch (item_type_sort) {
                        case (1):
                            String weapon_ammo_data = results_item.getString("weapon_ammo");
                            Weapon_ammo.Append(weapon_ammo_data);
                            break;
                        case (2):
                            String finesse_data = results_item.getString("finesse_stats");
                            Finesse_stat.Append(finesse_data);
                            break;
                        case (3):
                            String crit_data = results_item.getString("crit_boost");
                            String weight_data = results_item.getString("weight_lbs");
                            Crit_bonus.Append(crit_data);
                            Weight.Append(weight_data);
                            break;
                        case (4):
                            String atk_data = results_item.getString("atk_bonus");
                            Atk_bonus.Append(atk_data);
                            break;
                        case (5):
                            String ranges_data = results_item.getString("ranges");
                            String reload_data = results_item.getString("reload_time");
                            String ammo_data = results_item.getString("ammo_type");
                            Ranges_Loading.Append(ranges_data);
                            Reload_time.Append(reload_data);
                            Ammo_type_L.Append(ammo_data);
                            break;
                        case (6):
                            String ranges_data_R = results_item.getString("ranges");
                            String ammo_data_R = results_item.getString("ammo_type");
                            Ranges_range.Append(ranges_data_R);
                            Ammo_type_R.Append(ammo_data_R);
                            break;
                        case (7):
                            String reach_data = results_item.getString("reach_bonus");
                            String throw_data = results_item.getString("throw_range");
                            Reach_B.Append(reach_data);
                            Throw_range.Append(throw_data);
                            break;
                        case (8):
                            String radius_data = results_item.getString("radius");
                            String charge_data = results_item.getString("charges");
                            Radius.Append(radius_data);
                            Charges.Append(charge_data);
                            break;
                        case (9):
                            String ranges_T = results_item.getString("ranges");
                            Ranges_thrown.Append(ranges_T);
                            break;
                        case (10):
                            String object_data = results_item.getString("misc_object");
                            String bonus_data = results_item.getString("misc_bonus");
                            String break_data = results_item.getString("misc_breakable");
                            Misc_object.Append(object_data);
                            Misc_bonus.Append(object_data);
                            Misc_breakable.Append(break_data);
                            break;
                        case (11):
                            String action_data = results_item.getString("action");
                            Action.Append(action_data);
                            break;
                        case (12):
                            String L_data = results_item.getString("legend_action");
                            Legend.Append(L_data);
                            break;
                        }
                    }
                }

                statement_item.close();
            }

        } catch (SQLException throwables) {
            System.out.println("THE MYSQL CODE HAS AN ERROR");
            throwables.printStackTrace();
        }

    }

    public static void Cheat_insert(){
        JFrame Cheat;
        GUI Window = new GUI("Stay Unseen", 400, 800, Color.orange);
        Cheat = Window.Create_GUI();

        GridBagLayout a = new GridBagLayout();
        Cheat.setLayout(a);
        GBC gbc = new GBC();

        int Id_next = 0;

        try {

            String sql = "select id_item from items ";
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            while(results.next()){
                String id_data = results.getString("id_item");
                Id_next = Integer.valueOf(id_data) + 1;
            }
            statement.close();

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int finalId_next = Id_next;
        JButton insert = new JButton("Insert"); //insert method
        JButton cont = new JButton("Continue"); //takes the item type selected and will allow user to add more stuff
        JTextField name = new JTextField("name");
        JLabel N = new JLabel("Weapon Name: ");
        JComboBox<String> Dmg_type = new JComboBox<>(Dmg_Sorting);
        JTextField dmg = new JTextField("xd5");
        JLabel D = new JLabel("Damage Dice: ");
        JLabel D2 = new JLabel("Damage Type: ");
        JComboBox<String> Id_type = new JComboBox<>(Type_Sorting);
        JLabel T = new JLabel("Weapon Type: ");
        JTextField description = new JTextField("hello");
        JLabel Dp = new JLabel("Description: ");
        JTextField GP = new JTextField("60gp");
        JLabel G = new JLabel("Gold Cost: ");

        Cheat.add(N, gbc.gbc(0, 0, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(name, gbc.gbc(1, 0, GridBagConstraints.NONE, 200, 15, 1, 1));
        Cheat.add(D, gbc.gbc(0, 1, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(dmg, gbc.gbc(1, 1, GridBagConstraints.NONE, 50, 15, 1, 1));
        Cheat.add(D2, gbc.gbc(0, 2, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(Dmg_type, gbc.gbc(1, 2, GridBagConstraints.NONE, 100, 15, 1, 1));
        Cheat.add(T, gbc.gbc(0, 3, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(Id_type, gbc.gbc(1, 3, GridBagConstraints.NONE, 100, 15, 1, 1));
        Cheat.add(Dp, gbc.gbc(0, 4, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(description, gbc.gbc(1, 4, GridBagConstraints.NONE, 200, 15, 1, 1));
        Cheat.add(G, gbc.gbc(0, 5, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(GP, gbc.gbc(1, 5, GridBagConstraints.NONE, 50, 15, 1, 1));

        Cheat.add(cont,gbc.gbc(0, 6, GridBagConstraints.NONE, 100, 20, 0, 1));

        JLabel first_t = new JLabel("");
        JLabel second_t = new JLabel("");
        JLabel third_t = new JLabel("");
        JTextField one = new JTextField("");
        JTextField two = new JTextField("");
        JTextField three = new JTextField("");
        final int[] amount_command = {0};

        Cheat.add(first_t, gbc.gbc(0, 7, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(one, gbc.gbc(1, 7, GridBagConstraints.NONE, 50, 15, 1, 1));
        Cheat.add(second_t, gbc.gbc(0, 8, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(two, gbc.gbc(1, 8, GridBagConstraints.NONE, 50, 15, 1, 1));
        Cheat.add(third_t, gbc.gbc(0, 9, GridBagConstraints.NONE, 0, 0, 1, 1));
        Cheat.add(three, gbc.gbc(1, 9, GridBagConstraints.NONE, 50, 15, 1, 1));

        first_t.setVisible(false);
        second_t.setVisible(false);
        third_t.setVisible(false);
        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        insert.setVisible(false);

        Cheat.add(insert, gbc.gbc(0, 10, GridBagConstraints.NONE, 50, 15, 1, 1));

        cont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(Dmg_type.getSelectedIndex() != 0) {
                    first_t.setText("");
                    second_t.setText("");
                    third_t.setText("");
                    one.setText("");
                    two.setText("");
                    three.setText("");
                    first_t.setVisible(false);
                    second_t.setVisible(false);
                    third_t.setVisible(false);
                    one.setVisible(false);
                    two.setVisible(false);
                    three.setVisible(false);
                    insert.setVisible(false);

                    switch (Id_type.getSelectedIndex()) {
                        case (1):
                            first_t.setText("weapon_ammo"); //weapon_ammo
                            one.setText("Any Bow");
                            break;
                        case (2):
                            first_t.setText("finesse_stats");
                            one.setText("DEX/STR");
                            break;
                        case (3):
                            first_t.setText("crit_boost");
                            second_t.setText("weight_lbs");
                            one.setText("+2");
                            two.setText("20");
                            break;
                        case (4):
                            first_t.setText("atk_bonus");
                            one.setText("+1 atk");
                            break;
                        case (5):
                            first_t.setText("ranges");
                            second_t.setText("reload_time");
                            third_t.setText("ammo_type");
                            one.setText("60dt/70ft");
                            two.setText("Action");
                            three.setText("Bolts");
                            break;
                        case (6):
                            first_t.setText("ranges");
                            second_t.setText("ammo_type");
                            one.setText("60ft/70ft");
                            two.setText("Arrows");
                            break;
                        case (7):
                            first_t.setText("reach_bonus");
                            second_t.setText("throw_range");
                            one.setText("10ft");
                            two.setText("40ft");
                            break;
                        case (8):
                            first_t.setText("radius");
                            second_t.setText("charges");
                            one.setText("15ft");
                            two.setText("3");
                            break;
                        case (9):
                            first_t.setText("ranges");
                            one.setText("50ft/60ft");
                            break;
                        case (10):
                            first_t.setText("misc_object");
                            second_t.setText("misc_bonus");
                            third_t.setText("misc_breakable");
                            one.setText("Chair or somethig");
                            two.setText("+1d4");
                            three.setText("2 turns");
                            break;
                        case (11):
                            first_t.setText("action");
                            one.setText("bonus action");
                            break;
                        case (12):
                            first_t.setText("legend_action");
                            one.setText("Desciption");
                            break;
                    }

                    if (first_t.getText().equals("") != true) {
                        first_t.setVisible(true);
                        one.setVisible(true);
                        insert.setVisible(true);
                        amount_command[0] = 1;
                    }
                    if (second_t.getText().equals("") != true) {
                        second_t.setVisible(true);
                        two.setVisible(true);
                        insert.setVisible(true);
                        amount_command[0] = 2;
                    }
                    if (third_t.getText().equals("") != true) {
                        third_t.setVisible(true);
                        three.setVisible(true);
                        insert.setVisible(true);
                        amount_command[0] = 3;
                    }

                }
            }
        });

        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item_fin = ("ID = " + String.valueOf(finalId_next) + "\n" +
                        "Name = " + name.getText() + "\n" +
                        "ID_item_type = " + String.valueOf(Id_type.getSelectedIndex()) + "\n" +
                        "Damage = " + dmg.getText() + "\n" +
                        "ID_dmg_type = " + String.valueOf(Dmg_type.getSelectedIndex()) + "\n" +
                        "Descrption = " + description.getText() + "\n" +
                        "Gold = " + GP.getText() + "\n" +
                        first_t.getText() + " = " + one.getText() + "\n" +
                        second_t.getText() + " = " + two.getText() + "\n" +
                        third_t.getText() + " = " + three.getText() + "\n");
                int choice = JOptionPane.showConfirmDialog(Cheat, "Does this look right to you? \n\n " + item_fin, "Confirm", JOptionPane.YES_NO_OPTION);

                if(choice == JOptionPane.YES_OPTION){

                    System.out.println(amount_command[0]);
                    PreparedStatement statement  = null;
                    PreparedStatement statement2 = null;

                    String sql = "INSERT INTO items(id_item, name, id_item_type, damage, id_dmg_type, description, gold_cost)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?) ";
                    String sql_2 = "";



                    try {

                        if(amount_command[0] == 1)
                        {
                            sql_2 = "insert into " + sql_colums[Id_type.getSelectedIndex()] + "(id_item, name, id_item_type, damage, id_dmg_type, " +
                                    first_t.getText() + ", description, gold_cost)"
                            + "Values (?, ?, ?, ?, ?, ?, ?, ?)";

                            statement2 = connection.prepareStatement(sql_2);
                            statement2.setString(6, one.getText());
                            statement2.setString(7, description.getText());
                            statement2.setString(8, GP.getText());
                        }
                        else if(amount_command[0] == 2)
                        {
                            sql_2 = "insert into " + sql_colums[Id_type.getSelectedIndex()] + "(id_item, name, id_item_type, damage, id_dmg_type, " +
                                    first_t.getText() +  ", " + second_t.getText() + ", description, gold_cost)"
                                    + "Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                            statement2 = connection.prepareStatement(sql_2);
                            statement2.setString(6, one.getText());
                            statement2.setString(7, two.getText());
                            statement2.setString(8, description.getText());
                            statement2.setString(9, GP.getText());
                        }
                        else if(amount_command[0] == 3)
                        {
                            sql_2 = "insert into " + sql_colums[Id_type.getSelectedIndex()] + "(id_item, name, id_item_type, damage, id_dmg_type, " +
                                    first_t.getText() +  ", " + second_t.getText() + ", " + third_t.getText() + ", description, gold_cost)"
                                    + "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                            statement2 = connection.prepareStatement(sql_2);
                            statement2.setString(6, one.getText());
                            statement2.setString(7, two.getText());
                            statement2.setString(8, three.getText());
                            statement2.setString(9, description.getText());
                            statement2.setString(10, GP.getText());
                        }
                        else{
                            System.out.println("THERE WAS AN ERROR!!");
                        }

                        statement = connection.prepareStatement(sql);
                        statement.setString(1, String.valueOf(finalId_next));
                        statement.setString(2, name.getText());
                        statement.setString(3, String.valueOf(Id_type.getSelectedIndex()));
                        statement.setString(4, dmg.getText());
                        statement.setString(5, String.valueOf(Dmg_type.getSelectedIndex()));
                        statement.setString(6, description.getText());
                        statement.setString(7, GP.getText());

                        statement2.setString(1, String.valueOf(finalId_next));
                        statement2.setString(2, name.getText());
                        statement2.setString(3, String.valueOf(Id_type.getSelectedIndex()));
                        statement2.setString(4, dmg.getText());
                        statement2.setString(5, String.valueOf(Dmg_type.getSelectedIndex()));




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

                    }
                    catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }

                Cheat.dispose();


            }
        });

        Cheat.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Cheat.setLocationRelativeTo(null); //set window in center of screen
        Cheat.setVisible(true);
    }

    public static void store_screen(){
        JFrame Sto;
        GUI Window = new GUI("Stay Unseen", 700, 850, Color.orange);
        Sto = Window.Create_GUI();

        GridBagLayout a = new GridBagLayout();
        Sto.setLayout(a);
        GBC gbc = new GBC();


        //Change these with loops from mysql

        JLabel Title = new JLabel(new ImageIcon("pics/title.png"));

        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        top.setLayout(a);
        bottom.setLayout(a);
        bottom.setBackground(Color.orange);

        JScrollPane scroll = new JScrollPane(bottom);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JSplitPane Screen = new JSplitPane(SwingConstants.HORIZONTAL, top, scroll);

        JButton Return = new JButton("EXIT");
        JLabel user = new JLabel("<html>USER: ORION <br/> GOLD: 1004gp</html>");
        JButton Inventory = new JButton("Your Inventory");
        JButton cheat = new JButton("CHEATS");

        Store_item Acid = new Store_item( connection, 4, "pics/acid.png", Level, Type_Sorting, "Acid: Not a poison, but more of a chemical burn, This damage type is pretty much even effectiveness to all creatures that don't use acid themselves.");
        Store_item Bluge = new Store_item(connection, 1, "pics/blugeoning.png", Level, Type_Sorting, "Bludgeoning: One of the three common type of Damages out there. These are usually weapons that are made up of heavy surfaces, hammers, maces, or any blunt weapon.");
        Store_item Cold = new Store_item(connection, 5,"pics/cold.png", Level, Type_Sorting, "Cold: The power of Ice is on your side, however, the effectiveness on fire type creatures can be limited depending on the type of fire.");
        Store_item Fire = new Store_item(connection, 6,"pics/fire.png", Level, Type_Sorting, "Fire: Fire Power is real power! The basic but most destructive possiblity for dmg to all creatures, that of course if they aren't immune to fire itself.");
        Store_item Force = new Store_item(connection,7,"pics/force.png", Level, Type_Sorting, "Force: Unlike Bludgeoning, this power hits where it hurts most, from the inside out. thick of it as a magical punch that's invisable.");
        Store_item Lightning = new Store_item(connection,8,"pics/lightning.png", Level, Type_Sorting, "Lightning: The Sky sends down a powerful strike of energy that can possibly paralyze your target, Now that power is in your hands.");
        Store_item Necrot = new Store_item(connection,9,"pics/necrotic.png", Level, Type_Sorting, "Necrotic: Rotting and Killing are the only two main words to describe this damage. Just make sure what ever you are killing is alive.");
        Store_item Pirce = new Store_item(connection,3,"pics/pircing.png", Level, Type_Sorting, "Piercing: One very common Damages out there aswell, if you don't like swinging left or right or hitting them with a lot of power, just go striaght and go through them. ");
        Store_item Poison = new Store_item(connection,10,"pics/poision.png", Level, Type_Sorting, "Poison: Slowly sicken your enemies or allies, your choice. Just make sure not to cut yourself with this damage or you'll feel your own power.");
        Store_item Pysci = new Store_item(connection,11,"pics/pyschic.png", Level, Type_Sorting, "Pyschic: Forget hurting them physcially, go in their brain and hit them mentally. The most painful attack is an attack to the heart... or make them brain dead.");
        Store_item Radiant = new Store_item(connection,12,"pics/radiant.png", Level, Type_Sorting, "Radiant: The power of both god and light are usually on your side with this damage, why heal them with radiant when you can destory them with the light from the sun.");
        Store_item Rando = new Store_item(connection,0,"pics/random.png", Level, Type_Sorting, "Random: Buy a random componet or weapon part from us for cheaper. Usually we put a bunch of unbought components that we don't care about in here.");
        Store_item Slash = new Store_item(connection,2,"pics/slash.png", Level, Type_Sorting, "Slashing: The most common type of damage out of everything. You can't go wrong with wanting to swing and cut everything. Fruit, trees, or even people!");
        Store_item Thund = new Store_item(connection, 13,"pics/Thunder.png", Level, Type_Sorting, "Thunder: Harness the power of sound in your weapon. This damage can rumble the ground or even blow someone's eardrums out. ");

        Store_item[] all = {Bluge, Slash, Pirce, Acid, Cold, Fire, Force, Lightning, Necrot, Poison, Pysci, Radiant, Thund, Rando};

        Insets spacer = new Insets(5, 10, 5, 10);

        Sto.add(Title, gbc.gbc(0, 1, GridBagConstraints.BOTH, 0, 0, 1, 0));
        Sto.add(Screen, gbc.gbc(0, 2, GridBagConstraints.BOTH, 0, 0, 1, 1));

        top.add(Return, gbc.gbc(0, 0, GridBagConstraints.HORIZONTAL, 15, 25, 1, 1, spacer));
        top.add(user, gbc.gbc(1, 0, GridBagConstraints.NONE, 0, 0, 1, 1, spacer));
        top.add(Inventory,gbc.gbc(2, 0, GridBagConstraints.HORIZONTAL, 25, 25, 1, 1, spacer));
        top.add(cheat, gbc.gbc(3, 0, GridBagConstraints.HORIZONTAL, 25, 25, 1, 1, spacer));

        for(int x = 0; x < all.length; x++){
            bottom.add(all[x].getImg(), gbc.gbc(0, x, GridBagConstraints.NONE, 0, 0, 0, 0));
            bottom.add(all[x].getDescription(), gbc.gbc(1, x, GridBagConstraints.NONE, 0, 0, 0, 0));
            bottom.add(all[x].getLevels(), gbc.gbc(2, x, GridBagConstraints.NONE, 0, 0, 0, 0));
            bottom.add(all[x].getTypes(), gbc.gbc(3, x, GridBagConstraints.NONE, 0, 0, 0, 0));
            bottom.add(all[x].getBuy_button(), gbc.gbc(4, x, GridBagConstraints.NONE, 0, 0, 0, 0));
        }

        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sto.dispose();
                main_menu();
            }
        });




        Sto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Sto.setLocationRelativeTo(null); //set window in center of screen
        Sto.setVisible(true);

    }

    public static void craft_screen(){
        JFrame craft;
        GUI Window = new GUI("Stay Unseen", 800, 700, Color.orange);
        craft = Window.Create_GUI();

        GridBagLayout a = new GridBagLayout();
        craft.setLayout(a);
        GBC gbc = new GBC();

        JPanel left = new JPanel(); //weapon
        JPanel right = new JPanel(); //elements

        JLabel Title = new JLabel(new ImageIcon("pics/title.png"));
        left.setLayout(a);
        right.setLayout(a);

        JSplitPane Screen = new JSplitPane(SwingConstants.VERTICAL, left, right);
        Screen.setDividerLocation(400);

        JButton left_W = new JButton("Select Weapon");
        JButton right_E = new JButton("Select Element");

        JLabel Left_text = new JLabel("", SwingConstants.CENTER);
        JLabel Right_text = new JLabel("", SwingConstants.CENTER);

        Left_text.setFont(new Font("Verdana", Font.CENTER_BASELINE, 20));
        Right_text.setFont(new Font("Verdana", Font.CENTER_BASELINE, 20));

        JButton Crafting = new JButton("Craft Item");
        JButton Return = new JButton("Return");

        craft.add(Title, gbc.gbc(0, 0, GridBagConstraints.BOTH, 0, 0, 1, 0));
        craft.add(Return, gbc.gbc(0, 1, GridBagConstraints.NONE, 180, 20, 1, 0, new Insets(5,0,5, 0)));
        craft.add(Screen, gbc.gbc(0, 2, GridBagConstraints.BOTH, 0, 0, 1, 1));
        craft.add(Crafting, gbc.gbc(0, 3, GridBagConstraints.NONE, 250, 20, 1, 0, new Insets(25,0,25, 0)));

        left.add(left_W, gbc.gbc(0, 0, GridBagConstraints.NONE, 150, 20, 1, 0));
        left.add(Left_text, gbc.gbc(0, 1, GridBagConstraints.BOTH, 0, 0, 1, 0));

        right.add(right_E, gbc.gbc(0, 0, GridBagConstraints.NONE, 150, 20, 1, 0));
        right.add(Right_text, gbc.gbc(0, 1, GridBagConstraints.BOTH, 0, 0, 1, 0));

        final Componet[] Weapon_componet = {new Componet("0", "1d6", "2", "random",0)};
        final Componet[] Element_componet = {new Componet("0", "1d6", "2", "random", 0)};

        left_W.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int amount_items = 0;
                Statement statement = null;
                try {

                    String sql = "select Count(*) from componet_weapon;";
                    statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql);

                    while(results.next())
                    {
                        String num = results.getString("Count(*)");
                        amount_items = Integer.valueOf(num);
                    }
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                ButtonGroup ITEMS = new ButtonGroup();
                JCheckBox[] inventory = new JCheckBox[amount_items];
                JPanel INVO = new JPanel(new GridLayout(30,0));
                String[] IDs = new String[amount_items];

                Statement statement2 = null;
                try {

                    String sql = "select * from componet_weapon w\n" +
                            "join item_type i on i.id_item_type = w.id_item_type;";
                    statement2 = connection.createStatement();
                    ResultSet results = statement2.executeQuery(sql);
                    int count = 0;
                    while(results.next())
                    {
                        String ID = results.getString("id_weapon");
                        String Name = results.getString("name");
                        String damage = results.getString("damage");
                        String item_type = results.getString("item_category");
                        String power = results.getString("power_level");
                        inventory[count] = new JCheckBox(ID + ": " + Name + " (" + item_type + ") LV." + power);
                        ITEMS.add(inventory[count]);
                        INVO.add(inventory[count]);
                        IDs[count] = ID;
                        count++;

                    }
                    statement2.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                int x = JOptionPane.showConfirmDialog(craft, INVO,
                        "YOUR INVENTORY",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, new ImageIcon("pics/random.png") );

                for(int i = 0; i < inventory.length; i++){
                    if(inventory[i].isSelected() == true)
                    {
                        x = i;
                    }
                }

                Statement statement3 = null;
                try {

                    String sql = "select * from componet_weapon w\n" +
                            "join item_type i on i.id_item_type = w.id_item_type\n" +
                            "where id_weapon = " + IDs[x] + ";";
                    statement3 = connection.createStatement();
                    ResultSet results = statement3.executeQuery(sql);
                    StringBuilder text = new StringBuilder();

                    while(results.next())
                    {

                        String ID = results.getString("id_weapon");
                        String Name = results.getString("name");
                        String damage = results.getString("damage");
                        String item_type = results.getString("item_category");
                        String id_type = results.getString("id_item_type");
                        String power = results.getString("power_level");


                        text.append("<html>");
                        text.append("ID: " + ID);
                        text.append("<br/>");
                        text.append("Name: " + Name);
                        text.append("<br/>");
                        text.append("Damage: " + damage);
                        text.append("<br/>");
                        text.append("Item_type: " + item_type);
                        text.append("<br/>");
                        text.append("Power: LV." + power);
                        text.append("</html>");

                        if(power.equals("MAX"))
                            power = "6";

                        Weapon_componet[0] = new Componet(ID, damage, id_type, item_type ,Integer.valueOf(power));

                    }
                    statement3.close();
                    Left_text.setText(text.toString());


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        right_E.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int amount_items = 0;
                Statement statement = null;
                try {

                    String sql = "select Count(*) from componet_element;";
                    statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql);

                    while(results.next())
                    {
                        String num = results.getString("Count(*)");
                        amount_items = Integer.valueOf(num);
                    }
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                ButtonGroup ITEMS = new ButtonGroup();
                JCheckBox[] inventory = new JCheckBox[amount_items];
                JPanel INVO = new JPanel(new GridLayout(30,0));
                String[] IDs = new String[amount_items];

                Statement statement2 = null;
                try {

                    String sql = "select * from componet_element e\n" +
                            "join dmg_type d on d.id_dmg_type = e.id_dmg_type;";
                    statement2 = connection.createStatement();
                    ResultSet results = statement2.executeQuery(sql);
                    int count = 0;
                    while(results.next())
                    {
                        String ID = results.getString("id_elements");
                        String Name = results.getString("name");
                        String damage = results.getString("damage");
                        String item_type = results.getString("damage_element");
                        String power = results.getString("power_level");
                        inventory[count] = new JCheckBox(ID + ": " + Name + " (" + item_type + ") LV." + power);
                        ITEMS.add(inventory[count]);
                        INVO.add(inventory[count]);
                        IDs[count] = ID;
                        count++;

                    }
                    statement2.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                int x = JOptionPane.showConfirmDialog(craft, INVO,
                        "YOUR INVENTORY",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, new ImageIcon("pics/random.png"));

                for(int i = 0; i < inventory.length; i++){
                    if(inventory[i].isSelected() == true)
                    {
                        x = i;
                    }
                }

                Statement statement3 = null;
                try {

                    String sql = "select * from componet_element e\n" +
                            "join dmg_type d on d.id_dmg_type = e.id_dmg_type\n" +
                            "where id_elements = " + IDs[x] + ";";
                    statement3 = connection.createStatement();
                    ResultSet results = statement3.executeQuery(sql);
                    StringBuilder text = new StringBuilder();

                    while(results.next())
                    {

                        String ID = results.getString("id_elements");
                        String Name = results.getString("name");
                        String damage = results.getString("damage");
                        String item_type = results.getString("damage_element");
                        String id_type = results.getString("id_dmg_type");
                        String power = results.getString("power_level");

                        text.append("<html>");
                        text.append("ID: " + ID);
                        text.append("<br/>");
                        text.append("Name: " + Name);
                        text.append("<br/>");
                        text.append("Damage: " + damage);
                        text.append("<br/>");
                        text.append("Dmg_type: " + item_type);
                        text.append("<br/>");
                        text.append("Power: LV." + power);
                        text.append("</html>");

                        if(power.equals("MAX"))
                            power = "6";

                        Element_componet[0] = new Componet(ID, damage, item_type, id_type,Integer.valueOf(power));

                    }
                    statement3.close();
                    Right_text.setText(text.toString());


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        Crafting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(Left_text.getText().equals("") != true && Right_text.getText().equals("") != true) {
                    insert_weapon = new New_weapon("", "", "", "", 0);
                    System.out.println(insert_weapon.Get_IDNUM());

                    JPanel Jpan = new JPanel();
                    JLabel title = new JLabel("~Pre-Craft Blueprint~");
                    JLabel name_t = new JLabel("Name: ");
                    JTextField name_F = new JTextField();
                    JLabel desc_t = new JLabel("Description: ");
                    JTextField desc_F = new JTextField();
                    JLabel Item_Craft = new JLabel(Combine_craft(Weapon_componet[0], Element_componet[0]));
                    JButton Craft = new JButton("Redo");

                    Jpan.setLayout(a);
                    Jpan.setPreferredSize(new Dimension(850, 650));
                    Jpan.add(Craft, gbc.gbc(0, 0, GridBagConstraints.NONE, 0, 0, 0, 0));
                    Jpan.add(title, gbc.gbc(0, 1, GridBagConstraints.NONE, 0, 0, 0, 0));
                    Jpan.add(name_t, gbc.gbc(0, 2, GridBagConstraints.NONE, 0, 0, 0, 0));
                    Jpan.add(name_F, gbc.gbc(1, 2, GridBagConstraints.NONE, 150, 0, 0, 0));
                    Jpan.add(Item_Craft, gbc.gbc(0, 3, GridBagConstraints.NONE, 0, 0, 0, 0));
                    Jpan.add(desc_t, gbc.gbc(0, 4, GridBagConstraints.NONE, 0, 0, 0, 0));
                    Jpan.add(desc_F, gbc.gbc(1, 4, GridBagConstraints.NONE, 150, 0, 0, 0));

                    Craft.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Item_Craft.setText(Combine_craft(Weapon_componet[0], Element_componet[0]));

                        }
                    });

                    int x = JOptionPane.showConfirmDialog(null, Jpan,
                            "YOUR INVENTORY",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.OK_CANCEL_OPTION, new ImageIcon("pics/random.png"));

                    if(x == JOptionPane.OK_OPTION)
                    {

                        String NAME_DATA = "";
                        String DESC_DATA = "";
                        if(name_F.getText().isBlank())
                            NAME_DATA = "Unknown Name";
                        else
                            NAME_DATA = name_F.getText();

                        if(desc_F.getText().isBlank())
                            DESC_DATA = "Mysterious Description?";
                        else
                            DESC_DATA = desc_F.getText();

                        insert_weapon.Set_Name(NAME_DATA);
                        insert_weapon.Set_Descp(DESC_DATA);
                        insert_weapon.Insert();

                        String sql_W = "DELETE FROM componet_weapon WHERE id_weapon = " + Weapon_componet[0].ID;
                        String sql_E = "DELETE FROM componet_element WHERE id_elements = " + Element_componet[0].ID;
                        Statement delete_weapon = null;
                        Statement delete_element = null;
                        try {
                            delete_weapon = connection.createStatement();
                            delete_weapon.executeUpdate(sql_W);
                            delete_element = connection.createStatement();
                            delete_element.executeUpdate(sql_E);

                            delete_element.close();
                            delete_weapon.close();

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        Left_text.setText("");
                        Right_text.setText("");

                    }

                }
                else{
                    int x = JOptionPane.showConfirmDialog(null, "ERROR: Please select two components to combine",
                            "ERROR",
                            JOptionPane.CLOSED_OPTION, JOptionPane.OK_OPTION, null);
                }

            }
        });

        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                craft.dispose();
                main_menu();
            }
        });





        craft.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        craft.setLocationRelativeTo(null); //set window in center of screen
        craft.setVisible(true);

    }




    public static String Combine_craft(Componet Weapon, Componet Element){

        Random random = new Random();
        StringBuilder Final = new StringBuilder();
        String id_item_type = Weapon.Type_name;
        String Damage = "";
        String id_dmg_type = Element.Type_name;
        String Gold_cost = "";

        String Trait_1 = "";
        String Trait_2 = "";
        String Trait_3 = "";

        String T1_name = "";
        String T2_name = "";
        String T3_name = "";

        if(Weapon.Power_Lv > Element.Power_Lv)
        {
            Damage = Weapon.Dmg;
        }
        else if(Element.Power_Lv > Weapon.Power_Lv)
        {
            Damage = Element.Dmg;
        }
        else{
            int C = random.nextInt(2);
            if(C == 0){
                Damage = Weapon.Dmg;
            }
            else{
                Damage = Element.Dmg;
            }
        }

        Gold_cost = String.valueOf( (random.nextInt( (Weapon.Power_Lv + Element.Power_Lv +1) ) + 1) * 10 + (random.nextInt(3)) * 5 ) + "gp";


        String[] objects = {"Chair", "Lamp", "Table", "Instrument", "Scrap", "Wood Scrap"};
        String[] bonus = {"+1d2", "+2d4", "+1d6", "+2d6", "+1d8", "+2d8"};
        String R[] = {"Bow", "CrossBow", "Range", "Slingshot", "Bow/CrossBow"};
        String A[] = {"Arrows", "Ball Bearings", "Bolts", "Any"};
        String stats[] = {"STR", "DEX", "CON", "INT", "WIS", "CHA"};
        String[] ACT = {"Reaction", "Bonus Action", "Action", "Any Action"};
        String[] L = {"Summon Element", "Summon Monster", "Godly Aid"};
        String[] L2 = {"Control Element", "Mind Control", "Godly Resistance"};
        String[] L3 = {"Power Will", "Speed Boost", "Master Skill"};
        int feet = (random.nextInt(10) + 1 )* 10;
        int Amount_trait = 0;

        switch (Integer.valueOf(Weapon.ID_type)){
            case(1): //Weapon Ammo
                Trait_1 = R[random.nextInt(R.length)]; //weapon_ammo
                T1_name = "weapon_ammo";
                Amount_trait = 1;
                break;
            case(2): // Finesse (1)
                String first_stat = stats[random.nextInt(stats.length)];
                String second_stat = stats[random.nextInt(stats.length)];
                if(first_stat.equals(second_stat))
                    Trait_1 = first_stat;
                else
                    Trait_1 = first_stat + "/" + second_stat; //finesse_stats
                T1_name = "finesse_stats";
                Amount_trait = 1;
                break;
            case(3): //Versatile
                Trait_1 = "+" + String.valueOf(random.nextInt(5) + 1); //crit_boost
                Trait_2 = String.valueOf( (random.nextInt(5) + 3) * 5 ); // weight_lbs
                T1_name = "crit_boost";
                T2_name = "weight_lbs";
                Amount_trait = 2;
                break;
            case(4): //Light
                Trait_1 =  "+" + String.valueOf(random.nextInt(5) +1) + " atk"; //atk_bonus
                T1_name = "atk_bonus";
                Amount_trait = 1;
                break;
            case(5): //Loading
                Trait_1 = String.valueOf(feet) + "ft/" + String.valueOf(feet + 10) + "ft"; //ranges
                Trait_2 = ACT[random.nextInt(ACT.length)]; //reload_time
                Trait_3 = A[random.nextInt(A.length)]; //ammo_type
                T1_name = "ranges";
                T2_name = "reload_time";
                T3_name = "ammo_type";
                Amount_trait = 3;
                break;
            case(6): //Range
                Trait_1 = String.valueOf(feet) + "ft/" + String.valueOf(feet + 10) + "ft"; //ranges
                Trait_2 = A[random.nextInt(A.length)]; //ammo_type
                T1_name = "ranges";
                T2_name = "ammo_type";
                Amount_trait = 2;
                break;
            case(7): //Reach
                Trait_1 = String.valueOf(random.nextInt(3) * 5) + "ft"; //reach_bonus
                Trait_2 = String.valueOf(feet) + "ft"; //throw_range
                T1_name = "reach_bonus";
                T2_name = "throw_range";
                Amount_trait = 2;
                break;
            case(8): //Magic
                int radius = (random.nextInt(10) * 5); //radius
                if(radius == 0)
                    Trait_1 = "N/A";
                else
                    Trait_1 = String.valueOf(radius);
                Trait_2 = String.valueOf(random.nextInt(10) + 1); //charges
                T1_name = "radius";
                T2_name = "charges";
                Amount_trait = 2;
                break;
            case(9): //Throwm
                Trait_1 = String.valueOf(feet) + "ft/" + String.valueOf(feet + 10) + "ft"; //ranges
                T1_name = "ranges";
                Amount_trait = 1;
                break;
            case(10): //improve
                Trait_1 = objects[random.nextInt(objects.length)]; //misc_object
                Trait_2 = bonus[random.nextInt(bonus.length)]; //misc_bonus
                Trait_3 = String.valueOf(random.nextInt(6) +2) + " turns"; //misc_breakable
                T1_name = "misc_object";
                T2_name = "misc_bonus";
                T3_name = "misc_breakable";
                Amount_trait = 3;
                break;
            case(11): //Cosmetic
                Trait_1 = ACT[random.nextInt(ACT.length)]; //action
                T1_name = "action";
                Amount_trait = 1;
                break;
            case(12): //Legend
                Trait_1 = L[random.nextInt(L.length)] + ", " + L2[random.nextInt(L2.length)] + ", "+ L3[random.nextInt(L3.length)]; //legend_action
                T1_name = "legend_action";
                Amount_trait = 1;
                break;
        }


        Final.append("<html>");
        Final.append("Item ID: " + insert_weapon.Get_IDNUM());
        Final.append("<br/>");
        Final.append("Item Type: " + id_item_type);
        Final.append("<br/>");
        Final.append("Damage: " + Damage);
        Final.append("<br/>");
        Final.append("Dmg Type: " + Dmg_Sorting[Integer.valueOf(id_dmg_type)]);
        Final.append("<br/>");
        Final.append(T1_name + ": " + Trait_1);
        Final.append("<br/>");
        Final.append(T2_name + ": " + Trait_2);
        Final.append("<br/>");
        Final.append(T3_name + ": " + Trait_3);
        Final.append("<br/>");
        Final.append("Gold Cost: " + Gold_cost);
        Final.append("<br/>");
        Final.append("</html>");

        insert_weapon = new New_weapon(Weapon.ID_type, Damage, id_dmg_type, Gold_cost, Amount_trait);
        insert_weapon.Set_Traits_names(T1_name, T2_name, T3_name);
        insert_weapon.Set_Traits_data(Trait_1, Trait_2, Trait_3);

        return Final.toString();
    }





}
