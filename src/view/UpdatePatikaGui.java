package view;

import Helper.Helper;
import com.patikadev.Main.Patika;
import Helper.Config;
import javax.swing.*;


public class UpdatePatikaGui extends JFrame{
    private JPanel wrapper;
    private JTextField fld_patikaupdate_name;
    private JButton btn_update;
    private Patika patika;

    public UpdatePatikaGui(Patika patika){
        this.patika = patika;
        add(wrapper);
        setSize(300,150);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_patikaupdate_name.setText(patika.getName());

        btn_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_patikaupdate_name)){
                Helper.showMsg("fill");
            }else{
                if(Patika.update(patika.getId(),fld_patikaupdate_name.getText())){
                    Helper.showMsg("Done");
                }
                dispose();

            }

        });
    }

}
