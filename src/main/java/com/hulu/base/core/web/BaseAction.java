package com.hulu.base.core.web;

import com.hulu.base.springmvc.editors.DoubleEditor;
import com.hulu.base.springmvc.editors.FloatEditor;
import com.hulu.base.springmvc.editors.IntegerEditor;
import com.hulu.base.springmvc.editors.LongEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huangyiwei on 15/10/15.
 */
public class BaseAction {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(float.class, new FloatEditor());
    }

//    @ExceptionHandler
    protected String exp(HttpServletRequest request, Exception ex) throws Exception{

        request.setAttribute("ex", ex);

        logger.error("服务器内部发生错误！", ex);

        throw ex;
    }
}
