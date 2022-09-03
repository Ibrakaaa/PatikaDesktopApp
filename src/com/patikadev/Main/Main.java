package com.patikadev.Main;

import view.OperatorGui;

public class Main {
    public static void main(String[] args){
        Operator op = new Operator();
        op.setId(1);
        op.setName("Ibrahim AKAR");
        op.setPass("1234");
        op.setUsername("Ibraka");
        op.setType("Student");
        OperatorGui opGui = new OperatorGui(op);



    }
}
