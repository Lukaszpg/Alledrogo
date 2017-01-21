package pl.gorny.action;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.CredentialsDto;
import pl.gorny.dto.UsernameDto;
import pl.gorny.service.SecurityService;

@Component("LoginAction")
public class LoginAction extends AbstractAction {

    @Autowired
    private SecurityService securityService;

    private CredentialsDto credentialsDto;

    @Override
    public void execute() {
        parseJsonToObject();
        loginUser();
        setUsernameInResponseBodyIfSuccess();
    }

    private void parseJsonToObject() {
        Gson gson = new Gson();
        credentialsDto = gson.fromJson(json, CredentialsDto.class);
    }

    private void loginUser() {
        responseDto.success = securityService.login(credentialsDto.getLogin(), credentialsDto.getPassword());
    }

    private void setUsernameInResponseBodyIfSuccess() {
        if(responseDto.success) {
            UsernameDto usernameDto = new UsernameDto();
            usernameDto.setUsername(securityService.findLoggedInUsername());

            responseDto.body = new Gson().toJson(usernameDto);
        }
    }
}

