package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping("/login")
    public ResponseDto login(@RequestBody String body) {
        loginAction.setJson(body);
        loginAction.execute();

        return loginAction.getResponseDto();
    }

    @ResponseBody
    @PostMapping("/logout")
    public ResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
        logoutAction.setRequest(request);
        logoutAction.setResponse(response);
        logoutAction.execute();

        return loginAction.getResponseDto();
    }
}
