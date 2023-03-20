import java.awt.*;
import javax.swing.*;

public class GUI
{

    private static JFrame Q_GUI = new JFrame();
    private static String Q_Title;
    private static int Q_Width;
    private static int Q_Height;
    private static Color Q_option;

    public GUI(String Title, int Width, int Height, Color option)
    {
        this.Q_GUI = new JFrame();
        this.Q_Title = Title;
        this.Q_Width = Width;
        this.Q_Height = Height;
        this.Q_option = option;
    }

    public JFrame Create_GUI()
    {
        this.Q_GUI = new JFrame();
        this.Q_GUI.setTitle(this.Q_Title);
        this.Q_GUI.setSize(this.Q_Width,this.Q_Height);
        Container c = this.Q_GUI.getContentPane();
        c.setBackground(this.Q_option);

        return this.Q_GUI;
    }

}