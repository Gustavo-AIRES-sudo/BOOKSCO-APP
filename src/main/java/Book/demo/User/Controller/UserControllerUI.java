package Book.demo.User.Controller;

import Book.demo.User.Entity.UserDTO;
import Book.demo.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users/ui")
@RequiredArgsConstructor
public class UserControllerUI {

    private final UserService userService;

    @GetMapping("/all")
    public String showAllUsers(Model model){
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "listUsers";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users/ui/all";
    }

    @GetMapping("/find/{id}")
    public String showUserById(@PathVariable Long id, Model model) {
        UserDTO user =  userService.findUserById(id);
        if (user !=null) {
            model.addAttribute("user", user);
            return "userInfo";//file html name
        } else {
            model.addAttribute("message", "User not found");
            return "listUsers";//file html name
        }
    }

    @GetMapping("/add")
    public String addUser(Model model){
       model.addAttribute("user", new UserDTO());
       return "addUser";
    }

    @PostMapping("/save")
    public String saveInfoOfNinja(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes){
        userService.addUser(userDTO);
        redirectAttributes.addFlashAttribute("message", "User created.");
        return "redirect:/users/ui/all";
    }
}
