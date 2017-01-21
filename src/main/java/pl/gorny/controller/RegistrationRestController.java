package pl.gorny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gorny.action.RegisterAction;
import pl.gorny.dto.ResponseDto;

@RestController
public class RegistrationRestController {

    @Autowired
    private RegisterAction registerAction;

    @ResponseBody
    @PostMapping("/save-user")
    public ResponseDto register(@RequestBody String body) {
        registerAction.setJson(body);
        registerAction.execute();

        return registerAction.getResponseDto();
    }
}
