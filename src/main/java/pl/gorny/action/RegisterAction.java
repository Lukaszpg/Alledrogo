package pl.gorny.action;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gorny.dto.UserDto;
import pl.gorny.model.Role;
import pl.gorny.service.RegistrationService;
import pl.gorny.model.User;

@Component("registerAction")
public class RegisterAction extends AbstractAction {

    private UserDto userDto;

    private User toPersistUser;

    @Autowired
    private RegistrationService registrationService;

    @Override
    public void execute() {
        parseJsonToObject();
        createUserFromDto();
        saveUser();
    }

    public void parseJsonToObject() {
        Gson gson = new Gson();
        userDto = gson.fromJson(json, UserDto.class);
    }

    public void createUserFromDto() {
        toPersistUser = new User();
        toPersistUser.setName(userDto.getName());
        toPersistUser.setSurname(userDto.getSurname());
        toPersistUser.setNickname(userDto.getNickname());
        toPersistUser.setAddress(userDto.getAddress());
        toPersistUser.setCity(userDto.getCity());
        toPersistUser.setZipcode(userDto.getZipcode());
        toPersistUser.setEmail(userDto.getEmail());
        toPersistUser.setPassword(userDto.getPassword());
        toPersistUser.setRole(Role.USER_ROLE);
    }

    public void saveUser() {
        registrationService.saveUser(toPersistUser);
        responseDto.success = true;
    }
}
