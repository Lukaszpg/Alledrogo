package pl.gorny.action;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.ResponseDto;
import pl.gorny.dto.UserDto;
import pl.gorny.model.Role;
import pl.gorny.service.RegistrationService;
import pl.gorny.model.User;

@Component("registerAction")
public class RegisterAction extends AbstractAction<UserDto> {

    private User toPersistUser;

    @Autowired
    private RegistrationService registrationService;

    public RegisterAction() {
        responseDto = new ResponseDto();
        logger = LoggerFactory.getLogger(RegisterAction.class);
    }

    @Override
    public void execute() {
        try {
            parseJsonToObject();
            createUserFromDto();
            saveUser();
            responseDto.success = true;
        } catch(Exception e) {
            responseDto.success = false;
            logger.error(e.getMessage());
        }
    }

    public void parseJsonToObject() {
        Gson gson = new Gson();
        dto = gson.fromJson(json, UserDto.class);
    }

    public void createUserFromDto() {
        toPersistUser = new User();
        toPersistUser.setName(dto.getName());
        toPersistUser.setSurname(dto.getSurname());
        toPersistUser.setNickname(dto.getNickname());
        toPersistUser.setAddress(dto.getAddress());
        toPersistUser.setCity(dto.getCity());
        toPersistUser.setZipcode(dto.getZipcode());
        toPersistUser.setEmail(dto.getEmail());
        toPersistUser.setPassword(dto.getPassword());
        toPersistUser.setRole(Role.ROLE_USER);
    }

    public void saveUser() {
        registrationService.saveUser(toPersistUser);
    }
}
