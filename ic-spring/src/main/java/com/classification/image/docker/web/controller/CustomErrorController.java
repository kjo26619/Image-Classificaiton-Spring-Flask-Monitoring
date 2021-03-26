package com.classification.image.docker.web.controller;

import com.classification.image.docker.web.error.WrongExtensionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@ControllerAdvice
public class CustomErrorController extends ResponseEntityExceptionHandler implements ErrorController {



    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request, Exception ex) {
        System.out.println(ex.getMessage());
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            model.addAttribute("error_code", statusCode);
        }
        else {
            model.addAttribute("error_code", "ERROR");
        }

        return "error";
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
