package Book.demo.User.Controller;

import Book.demo.User.Entity.DTO.TokenResponse;
import Book.demo.User.Entity.UserDTO;
import Book.demo.User.Entity.DTO.UserLoginRequest;
import Book.demo.User.Entity.UserModel;
import Book.demo.User.Service.TokenService;
import Book.demo.User.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final TokenService tokenService;

    @GetMapping("/welcome")
    @Operation(summary = "Welcome message to website", description = "this route basically sends a message to the new user in the web site.")
    public String welcome(){
        return "Welcome to this aplication";
    }

    @GetMapping("/all")
    @Operation(summary = "List all users of the database", description = "this route show all users of database, returning the informations of the users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "body of all users")
    })
    public ResponseEntity<List<UserDTO>> showAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).
                body(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "List an user by id", description = "this route expects an id as a parameter. When the request is valid, returns the user body JSON.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "body")
    })
    public ResponseEntity<UserDTO> showUserById(
            @Parameter(description = "ID of the user to be retrieved")
            @PathVariable Long id
    ){
        UserDTO user = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete route", description = "deletes an user when the parameter is valid. To the parameter be valid, the id of user needs to be in the database. Returns a string message if the operation is valid. If is not, returns an error.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operation of delete user was a sucess"),
            @ApiResponse(responseCode = "404", description = "id not found")
    })
    public ResponseEntity<Map<String, String>> deleteUser(
            @Parameter(description = "ID of the user to be deleted")
            @PathVariable Long id
    ){
        userService.deleteUser(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted");

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/auth/alter/{id}")
    @Operation(summary = "Alter user infos route", description = "this route alters the information of user, provided the request is valid. To be valid, needs an existing id and a valid request body. returns the new body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operation of alter user infos was a sucess"),
            @ApiResponse(responseCode = "404", description = "id not found")
    })
    public ResponseEntity<UserDTO> alterUserInfo(
            @Parameter(description = "ID of the user to be updated")
            @PathVariable Long id,
            @RequestBody UserDTO userDTO
    ){
        UserDTO user = userService.alterUserInfo(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @Operation(summary = "Add user route", description = "this route adds an user in the database, provided the user fill in the body request.")
    @PostMapping("/auth/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "creates an user, returning a string message 'cause the sucess of operation."),
            @ApiResponse(responseCode = "400", description = "error in the request of user, like: syntax, corrupted data, corrupted cookies, cache, etc.")
    })
    public ResponseEntity<?> addUser(
            @Parameter(description = "JSON body")
            @RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created with sucess.");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> userLogin(@RequestBody UserLoginRequest login){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(login.userGmail(), login.userPassword());
        Authentication authenticate = authenticationManager.authenticate(userAndPass);

        UserModel user = ((UserModel) authenticate.getPrincipal());
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new TokenResponse(token));
    }
}