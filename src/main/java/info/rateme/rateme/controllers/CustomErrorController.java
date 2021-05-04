package info.rateme.rateme.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = "Sorry, We probably shouldn't be here.";
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                message = "Sorry! we could not find this page";
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value())
                message = "You are not allowed on this page";
        }
        model.addAttribute("message", message);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
