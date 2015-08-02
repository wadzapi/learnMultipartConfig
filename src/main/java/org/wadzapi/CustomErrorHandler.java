package org.wadzapi;

import org.apache.myfaces.shared.context.ExceptionHandlerImpl;

import javax.faces.FacesException;

public class CustomErrorHandler extends ExceptionHandlerImpl {

    @Override
    public void handle() throws FacesException {
        try {
            super.handle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
