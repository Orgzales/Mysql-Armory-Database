import java.util.Random;



public class Componet {

    public String ID = "";
    public String Dmg = "";
    public String ID_type = ""; //based on what it is on
    public String Type_name = "";
    public int Power_Lv = 0;


    public Componet(String id, String dmg, String id_type, String type_name,int power){

        ID = id;
        Dmg = dmg;
        ID_type = id_type;
        Power_Lv = power;
        Type_name = type_name;

    }

}
