import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Colum {


    public JLabel col = new JLabel();
    public StringBuilder text = new StringBuilder();
    public StringBuilder temp = new StringBuilder();

    public String title;
    public int type_value; //always show is 0 which is N/A (get place of Array)
    public String sql_name;


    public Colum(String title_x, int type_value_x, String name_x){

        title = title_x;
        type_value = type_value_x;
        col.setFont(new Font("Verdana", Font.PLAIN, 16));
        sql_name = name_x;
    }

    public JLabel getCol(){

        return col;
    }

    public void Update(int search_value){
        text = new StringBuilder();
        text.append("<html>");
        text.append(title);
        text.append("<br/>");
        text.append(temp.toString());
        text.append("</html>");
        col.setText(text.toString());

        if(search_value == type_value){
            col.setVisible(true);
        }
        else{
            col.setVisible(false);
            if(type_value == 0){
                col.setVisible(true);
            }
        }

    }

    public void Clean(){
        temp = new StringBuilder();
    }

    public void Append(String stuff){
        temp.append("<br/>");
        temp.append(stuff);
        temp.append("<br/>");



    }


}
