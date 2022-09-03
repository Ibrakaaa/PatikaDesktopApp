package Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static int screenCenterPoint(String axis, Dimension size){
        int point;
        switch (axis){
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default:
                point = 0;
        }
        return point;

    }
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }
    public static void showMsg(String str){
        String msg;
        String title;
        optionPageTr();

        switch (str){
            case "fill":
                msg = "Lütfen Tüm Alanları Doldurunuz!";
                title = "Hata";
                break;
            case "Done":
                msg = "İşlem Başarılı";
                title = "Sonuç";
                break;
            case "Error":
                msg = "Bir Hata Oluştu";
                title = "Hata";
                break;
            default:
                msg = str;
                title = "Mesaj";

        }


        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);

    }


    public static boolean confirm(String str){
        optionPageTr();
        String msg;
        switch (str){
            case "sure" :
                msg = "Siilmek İstediğinize Emin Misiniz?";
                break;
            default:
                msg = str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Silinsin Mi?",JOptionPane.YES_NO_OPTION)==0;

    }
    public static void optionPageTr(){

        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }
}
