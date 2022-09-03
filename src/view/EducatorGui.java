package view;

import Helper.Helper;
import Helper.Config;

import javax.swing.*;

public class EducatorGui extends JFrame{
    private JPanel wrapper;

    public EducatorGui(){
        add(wrapper);
        setSize(600,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
    }
}
