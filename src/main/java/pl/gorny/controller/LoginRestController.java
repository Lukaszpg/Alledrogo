package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gorny.action.LoginAction;
import pl.gorny.action.LogoutAction;
import pl.gorny.dto.ResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginRestController {

    @Autowired
    private LoginAction loginAction;

    @Autowired
    private LogoutAction logoutAction;

    @ResponseBody
    @RequestMapping("/login")
    public ResponseDto login(@RequestBody String body) {
        loginAction.setJson(body);
        loginAction.execute();

        return loginAction.getResponseDto();
    }

    @ResponseBody
    @RequestMapping("/logout")
    public ResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
        logoutAction.setRequest(request);
        logoutAction.setResponse(response);
        logoutAction.execute();

        return loginAction.getResponseDto();
    }
}
