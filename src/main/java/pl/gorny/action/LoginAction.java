package pl.gorny.action;

import com.google.gson.Gson;
import com.mysql.cj.api.log.Log;
import org.slf4j.LoggerFactory;
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

    private boolean loggedIn;

    public LoginAction() {
        loggedIn = false;
        responseDto = new ResponseDto();
        logger = LoggerFactory.getLogger(LoginAction.class);
    }

    @Override
    public void execute() {
        try {
            parseJsonToObject();
            loginUser();
            setUsernameInResponseBodyIfSuccess();
            responseDto.success = true;
        } catch(Exception e) {
            responseDto.success = false;
            logger.error(e.getMessage());
        }
    }

    private void parseJsonToObject() {
        Gson gson = new Gson();
        dto = gson.fromJson(json, CredentialsDto.class);
    }

    private void loginUser() {
        loggedIn = securityService.login(dto.getUsername(), dto.getPassword());
    }

    private void setUsernameInResponseBodyIfSuccess() {
        if(loggedIn) {
            UsernameDto usernameDto = new UsernameDto();
            usernameDto.setUsername(securityService.findLoggedInUsername());
            usernameDto.setRole(securityService.findLoggedInRole());

            responseDto.body = new Gson().toJson(usernameDto);
        }
    }
}

