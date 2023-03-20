import javax.swing.*;
import java.awt.*;

public class GBC
{

    private static GridBagConstraints gbc = new GridBagConstraints();

    public GridBagConstraints gbc(int gridx, int gridy, int fill, int ipadx, int ipady, int weightx, int weighty)
    {
        this.gbc.gridx = gridx;
        this.gbc.gridy = gridy;
        this.gbc.fill = fill;
        this.gbc.ipadx = ipadx;
        this.gbc.ipady = ipady;
        this.gbc.weightx = weightx;
        this.gbc.weighty = weighty;

        return this.gbc;
    }

    public GridBagConstraints gbc(int gridx, int gridy, int fill, int ipadx, int ipady, int weightx, int weighty, int gridwidth, int gridheight)
    {
        this.gbc.gridx = gridx;
        this.gbc.gridy = gridy;
        this.gbc.fill = fill;
        this.gbc.ipadx = ipadx;
        this.gbc.ipady = ipady;
        this.gbc.weightx = weightx;
        this.gbc.weighty = weighty;
        this.gbc.gridwidth = gridwidth;
        this.gbc.gridheight = gridheight;

        return this.gbc;
    }

    public GridBagConstraints gbc(int gridx, int gridy, int fill, int ipadx, int ipady, int weightx, int weighty, Insets spacer)
    {
        this.gbc.gridx = gridx;
        this.gbc.gridy = gridy;
        this.gbc.fill = fill;
        this.gbc.ipadx = ipadx;
        this.gbc.ipady = ipady;
        this.gbc.weightx = weightx;
        this.gbc.weighty = weighty;
        //this.gbc.gridwidth = gridwidth;
       // this.gbc.gridheight = gridheight;
        this.gbc.insets = spacer;

        return this.gbc;
    }
}

