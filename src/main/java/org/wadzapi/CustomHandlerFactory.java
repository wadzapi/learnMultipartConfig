package org.wadzapi;


import org.apache.myfaces.context.MyFacesExceptionHandlerWrapperImpl;
import org.apache.myfaces.shared.context.AjaxExceptionHandlerImpl;
import org.apache.myfaces.shared.context.SwitchAjaxExceptionHandlerWrapperImpl;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CustomHandlerFactory extends ExceptionHandlerFactory {
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new SwitchAjaxExceptionHandlerWrapperImpl(
                new MyFacesExceptionHandlerWrapperImpl(new CustomErrorHandler()) ,
                new AjaxExceptionHandlerImpl());
    }
}
