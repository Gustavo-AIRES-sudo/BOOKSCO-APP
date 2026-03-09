package Book.demo.User.Service;

import Book.demo.User.Entity.UserDTO;
import Book.demo.User.Entity.UserModel;
import Book.demo.User.Mapper.UserMapper;
import Book.demo.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers(){
        List<UserModel> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(Long id){
        Optional<UserModel> user = userRepository.findById(id);
        return user.map(userMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteUser(Long id){
        if (!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
    }

    public UserDTO alterUserInfo(Long id, UserDTO userDTO){
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        UserModel updatingUser = user.get();
        userMapper.updateUserFromDTO(userDTO, updatingUser);
        UserModel updatedUser = userRepository.save(updatingUser);
        return userMapper.map(updatedUser);
    }

    public UserDTO addUser(UserDTO userDTO){
        UserModel newUser = userMapper.map(userDTO);

        String password = passwordEncoder.encode(newUser.getUserPassword());
        newUser.setUserPassword(password);

        newUser = userRepository.save(newUser);

        return userMapper.map(newUser);
    }

}