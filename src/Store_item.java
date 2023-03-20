import com.mysql.cj.jdbc.Driver;
import java.sql.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Random;

public class Store_item {

    public int component_type;
    public JLabel img;
    public JButton description = new JButton("Description");
    public JButton buy_button = new JButton("BUY COMPONENT");
    public JComboBox<String> levels;
    public JComboBox<String> types;
    public static Connection connection;

    public String details = "";
    public String[] Parts = {"Blade", "Claw", "Handle", "Orb", "Guard", "Gem", "Amulet", "Relic", "Rune", "Part", "Tip", "Enchantment", "Scroll", "Paint"};
    public String[] weapons = {"", "Firing", "Adjustable", "Heavy", "Light Weight", "Reloadable", "Distant", "Extended","Magical", "Throwable", "Unknown", "Charged", "Godly"};
    public String[] Dmgs = {"d2", "d4", "d6", "d8", "d10", "d12", "d20", "d100"};

    public static String[] Dmg_Sorting = {"N/A", "Bludgeoning" , "Slashing" , "Piercing" , "Acid" , "Cold" , "Fire" , "Force" , "Lightning" , "Necrotic" , "Poison" , "Psychic" , "Radiant" , "Thunder"};



    public Store_item(Connection X, int element, String img_x, String[] level_x, String[] type_x, String text){

        component_type = element;
        img = new JLabel(new ImageIcon(img_x));
        levels = new JComboBox<>(level_x);
        types = new JComboBox<>(type_x);
        details = text;
        connection = X;

    }

    public JLabel getImg(){
        return img;
    }

    public JButton getDescription(){

        description.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), details);
            }
        });

        return description;
    }

    public JButton getBuy_button(){

        buy_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Random random = new Random();
                int Id_next = 0;


                int level_amount = levels.getSelectedIndex();
                int item_type = types.getSelectedIndex();
                String Name = "";
                String componenet_table = "";
                String Damage_amount = "";
                String powerlevel_M = String.valueOf(level_amount);
                String id_type = String.valueOf(component_type);
                String sql = "";
                boolean weapon_random = false;

                if(id_type.equals("0")){
                    id_type = String.valueOf(random.nextInt(13) + 1);
                    weapon_random = true;
                }

                Damage_amount = String.valueOf(random.nextInt(level_amount + 1) + 1) + Dmgs[random.nextInt( 2 ) + level_amount];
                if(level_amount == 6)
                {
                    powerlevel_M = "MAX";
                }

                if(item_type != 0)
                {
                    int CX = random.nextInt(2);
                    if(CX == 0 || weapon_random == true){
                        componenet_table = "componet_weapon";
                        id_type = String.valueOf(item_type);
                        Name = weapons[item_type] + " " + Parts[random.nextInt(13)];
                        sql = "Insert into " + componenet_table + "(id_weapon, name, damage, id_item_type, power_level)\n" +
                        "values(?,?,?,?,?)";
                    }
                    else{
                        componenet_table = "componet_element";
                        Name = Dmg_Sorting[Integer.parseInt(id_type)] + " " + Parts[random.nextInt(13)];
                        sql = "Insert into " + componenet_table + "(id_elements, name, damage, id_dmg_type, power_level)\n" +
                                "values(?,?,?,?,?)";
                    }
                }
                else
                {
                    componenet_table = "componet_element";
                    Name = Dmg_Sorting[Integer.parseInt(id_type)] + " " +Parts[random.nextInt(13)];
                    sql = "Insert into " + componenet_table + "(id_elements, name, damage, id_dmg_type, power_level)\n" +
                            "values(?,?,?,?,?)";
                }

                try {
                    String sql_id = "select Count(*) from " + componenet_table;
                    Statement statement = null;
                    statement = connection.createStatement();
                    ResultSet results = statement.executeQuery(sql_id);
                    String id_data = "0";

                    while(results.next()){
                        id_data = results.getString("Count(*)");
                        Id_next = random.nextInt(Integer.valueOf(id_data) * 2) + random.nextInt(9999);
                        if(Integer.valueOf(Id_next) > 9999){
                            Id_next = (Id_next - 500) - random.nextInt(1000);
                        }
                    }
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



                PreparedStatement statement  = null;
                try {

                    statement = connection.prepareStatement(sql);
                    statement.setString(1, String.valueOf(Id_next));
                    statement.setString(2, Name);
                    statement.setString(3, Damage_amount);
                    statement.setString(4, id_type);
                    statement.setString(5, powerlevel_M);

                    int rows = statement.executeUpdate();
                    if(rows > 0){
                        System.out.println("rows beeen inserted");
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                int x = JOptionPane.showConfirmDialog(null, "You have bought a: " + Name +"!\n" +
                                "ID: " + String.valueOf(Id_next) + "\n" +
                                "Damage: " + Damage_amount + "\n" +
                                "Power: " + powerlevel_M ,
                        "STORE",
                        JOptionPane.CLOSED_OPTION, JOptionPane.OK_OPTION, new ImageIcon(String.valueOf(img)));


            }
        });

        return buy_button;
    }

    public JComboBox<String> getLevels(){
        return levels;
    }

    public JComboBox<String> getTypes(){
        return types;
    }

}
