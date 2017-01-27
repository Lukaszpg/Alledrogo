package pl.gorny.action;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.CredentialsDto;
import pl.gorny.dto.ResponseDto;
import pl.gorny.dto.UsernameDto;
import pl.gorny.service.SecurityService;

@Component("LoginAction")
public class LoginAction extends AbstractAction<CredentialsDto> {

    @Autowired
    private SecurityService securityService;

    @Override
    public void execute() {
        parseJsonToObject();
        loginUser();
        setUsernameInResponseBodyIfSuccess();
    }

    private void parseJsonToObject() {
        Gson gson = new Gson();
        dto = gson.fromJson(json, CredentialsDto.class);
    }

    private void loginUser() {
        responseDto = new ResponseDto();
        responseDto.success = securityService.login(dto.getUsername(), dto.getPassword());
    }

    private void setUsernameInResponseBodyIfSuccess() {
        if(responseDto.success) {
            UsernameDto usernameDto = new UsernameDto();
            usernameDto.setUsername(securityService.findLoggedInUsername());
            usernameDto.setRole(securityService.findLoggedInRole());

            responseDto.body = new Gson().toJson(usernameDto);
        }
    }
}

