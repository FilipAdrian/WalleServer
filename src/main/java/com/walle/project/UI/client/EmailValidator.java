package com.walle.project.UI.client;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EmailValidator extends ValidatorBase {
    @Override
    protected void eval() {
        if (srcControl.get ( ) instanceof TextInputControl) {
            evalTextInputField ( );
        }
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        Boolean valid = org.apache.commons.validator.routines.EmailValidator.getInstance ().isValid (textField.getText ());
        if (textField.getText() == null || textField.getText().isEmpty() || valid == false) {
            hasErrors.set(true);
        } else {
            hasErrors.set(false);
        }
    }


}
