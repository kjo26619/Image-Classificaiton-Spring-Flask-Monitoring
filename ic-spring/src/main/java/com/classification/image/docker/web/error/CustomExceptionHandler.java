package com.classification.image.docker.web.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (WrongExtensionException.class)
    public String wrongExtension(Exception ex, Model model) {

        model.addAttribute("error_code", "400");

        model.addAttribute("error_subtitle", "Bad Extension");

        model.addAttribute("error_description", ex.getMessage());

        return "error";
    }

    @ExceptionHandler (EntityEmptyException.class)
    public String entityEmpty(Exception ex, Model model) {
        model.addAttribute("error_code", "400");

        model.addAttribute("error_subtitle", "Entity Empty");

        model.addAttribute("error_description", ex.getMessage());

        return "error";
    }


}
