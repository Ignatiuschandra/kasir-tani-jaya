/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author ignas
 */
public class FocusTextField implements FocusListener {
    JTextField textField;
    public FocusTextField(JTextField tf){
        textField = tf;
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        textField.select(0, textField.getText().length());
    }

    @Override
    public void focusLost(FocusEvent e) {
        textField.select(0, 0);
    }

}
