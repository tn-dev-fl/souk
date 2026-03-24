package souk.demo.converter;

import org.springframework.stereotype.Component;
import souk.demo.dto.UserDTO;
import souk.demo.model.UserModel;

@Component
public class UserConverter extends GenericConverter<UserModel, UserDTO> {

    public UserConverter() {
        super(

                user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()),

                dto -> {
                    UserModel user = new UserModel();
                    user.setUsername(dto.getUsername());
                    user.setEmail(dto.getEmail());
                    return user;
                });
    }
}