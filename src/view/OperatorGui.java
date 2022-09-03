package view;

import Helper.Helper;
import Helper.Config;
import com.patikadev.Main.Course;
import com.patikadev.Main.Operator;
import com.patikadev.Main.Patika;
import com.patikadev.Main.User;
import Helper.Item;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class OperatorGui extends JFrame {
    private JPanel wrapper;
    private JTabbedPane pnl_user_list;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JLabel fld_uname;
    private JTextField textfld_uname;
    private JLabel lbl_pass;
    private JPasswordField fld_pass;
    private JLabel lbl_type;
    private JComboBox comboBox1_type;
    private JButton btn_user_add;
    private JLabel user_id_label;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_srch_user_name;
    private JTextField fld_srch_username;
    private JComboBox cmb_srch_user_type;
    private JButton btn_user_srch;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_add;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField textField1;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    private final Operator operator;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    public OperatorGui(Operator operator){
        this.operator = operator;
        add(wrapper);
        setSize(600,500);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin "+operator.getName());
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object [] col_user_list = {"ID","Ad Soyad","Kullanıcı Adı","Şifre","Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list= new Object[col_user_list.length];

        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                fld_user_id.setText(select_user_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        });
        tbl_user_list.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String user_uname =  tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String user_pass =  tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String user_type =  tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();

                if(User.upDate(user_id,user_name ,user_uname,user_pass,user_type)){
                    Helper.showMsg("Done");
                    loadUserModel();
                    loadEducatorCombo();
                    loadCourseModel();

                }else{
                    Helper.showMsg("error");
                }
            }

        });

        //PatikaList
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGui UpdateGui = new UpdatePatikaGui(Patika.getFetch(select_id));
            UpdateGui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });

        });
        deleteMenu.addActionListener(e -> {
            if(Helper.confirm("sure")){
                int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                if(Patika.delete(select_id)){
                    Helper.showMsg("Done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();

                }else{
                    Helper.showMsg("error");
                }

            }

        });

        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();
        loadPatikaCombo();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(80);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        //PatikaList

        //CourseList
        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID","Ders Adı","Program Dili","Patika","Eğitmeni"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();
        
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(80);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadPatikaCombo();
        loadEducatorCombo();
        //CourseList



        btn_user_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_name)||Helper.isFieldEmpty(textfld_uname)||Helper.isFieldEmpty(fld_pass)){
                Helper.showMsg("fill");
            }else{
                String name = fld_user_name.getText();
                String username = textfld_uname.getText();
                String pass = fld_pass.getText();
                String type = comboBox1_type.getSelectedItem().toString();
                if(User.add(name,username,pass,type)){
                    Helper.showMsg("Done");
                    loadUserModel();
                    loadEducatorCombo();
                    fld_user_name.setText(null);
                    textfld_uname.setText(null);
                    fld_pass.setText(null);
                }


            }

        });
        btn_user_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_id)){
                Helper.showMsg("fill");
            }else{
               if(Helper.confirm("sure")){
                   int user_id = Integer.parseInt(fld_user_id.getText());
                   if(User.delete(user_id)) {
                       Helper.showMsg("Done");
                       fld_user_id.setText(null);
                       loadUserModel();
                       loadEducatorCombo();
                       loadCourseModel();
                   }else{
                       Helper.showMsg("error");
                   }
               }
            }
        });
        btn_user_srch.addActionListener(e -> {
            String name = fld_srch_user_name.getText();
            String username = fld_srch_username.getText();
            String type = cmb_srch_user_type.getSelectedItem().toString();
            String Query = User.searchQuery(name,username,type);
            ArrayList<User> searchingUser = User.searchUserList(Query);
            loadUserModel(User.searchUserList(Query));
            loadUserModel(searchingUser);

        });
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGui login = new LoginGui();

        });
        btn_patika_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_patika_add)){
                Helper.showMsg("fill");
            }else{
                if(Patika.add(fld_patika_add.getText())){
                    Helper.showMsg("Done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patika_add.setText(null);
                }else{
                    Helper.showMsg("error");
                }

            }

        });
        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if(Helper.isFieldEmpty(fld_course_name)|| Helper.isFieldEmpty(fld_course_lang)){
                Helper.showMsg("fill");
            }else{
                if(Course.add(userItem.getKey(),patikaItem.getKey(),fld_course_name.getText(),fld_course_lang.getText())){
                    Helper.showMsg("Done");
                    loadCourseModel();
                }else{
                    Helper.showMsg("error");
                }

            }

        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        for(Course obj: Course.getList()){
            row_course_list[0]=obj.getId();
            row_course_list[1]=obj.getLang();
            row_course_list[2]=obj.getName();
            row_course_list[3]=obj.getPatika().getName();
            row_course_list[4]=obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);



        }
    }
    //Patika Section

    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        for(Patika obj : Patika.getList()){
            row_patika_list[0] = obj.getId();
            row_patika_list[1] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    //Patika Section

    public void loadUserModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for(User obj : User.getList()){
            row_user_list[0] = obj.getId();
            row_user_list[1] = obj.getName();
            row_user_list[2] = obj.getUsername();
            row_user_list[3] = obj.getPass();
            row_user_list[4] = obj.getType();
            mdl_user_list.addRow(row_user_list);

        }

    }
    public void loadUserModel(ArrayList<User> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for(User obj : list){
            row_user_list[0] = obj.getId();
            row_user_list[1] = obj.getName();
            row_user_list[2] = obj.getUsername();
            row_user_list[3] = obj.getPass();
            row_user_list[4] = obj.getType();
            mdl_user_list.addRow(row_user_list);

        }

    }
    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();
        for(Patika obj : Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(),obj.getName()));
        }
    }
    public void loadEducatorCombo(){
        cmb_course_user.removeAllItems();
        for(User obj : User.getList()){
            if(obj.getType().equals("Educator")){
                cmb_course_user.addItem(new Item(obj.getId(),obj.getName()));
            }
        }
    }


}
