package view;

import Helper.Helper;
import Helper.Config;
import com.patikadev.Main.Operator;
import com.patikadev.Main.User;
import java.awt.*;

import javax.swing.*;

public class LoginGui extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JButton btn_login;

    public LoginGui(){
        add(wrapper);
        setSize(600,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_uname)|| Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");
            }else{
                User u = User.getFetch(fld_user_uname.getText(),fld_user_pass.getText());
                if(u == null){
                    Helper.showMsg("Kullanıcı Bulunamadı");
                }else{
                    switch (u.getType()){
                        case "Operator":
                            OperatorGui opGui = new OperatorGui((Operator) u);
                            break;
                        case "Educator":
                            EducatorGui eduGui = new EducatorGui();
                            break;
                        case "Student":
                            StudentGui stGui = new StudentGui();
                            break;
                    }
                    dispose();
                }
            }

        });
    }
    public static void main(String[] args){
        LoginGui login = new LoginGui();

    }
}
