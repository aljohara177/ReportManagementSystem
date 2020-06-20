package com.example.reportmanagmentsystem.controller;


import com.example.reportmanagmentsystem.model.Group;
import com.example.reportmanagmentsystem.model.User;
import com.example.reportmanagmentsystem.service.GroupService;
import com.example.reportmanagmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/users")
    public ModelAndView showUserPage(ModelAndView model) {
        List<User> usersList = this.userService.getAllUsers();
        model.addObject("usersList",usersList);
        model.setViewName("users");
        return model;
    }

    @RequestMapping("/users/add")
    public ModelAndView showAddUserForm(User user, ModelAndView model) {
        List<Group> groupList = groupService.getAllGroups();
        List<String> groupName = new ArrayList<>();
        for (int i = 0 ; i<groupList.size() ; i++) {
            groupName.add(groupList.get(i).getNameEn());
        }
        model.addObject("groupList", groupName);
        model.setViewName("add-user");
        return model;
    }
    @GetMapping("/users/edit/{id}")
    public ModelAndView showUpdateUserPage(@PathVariable ("id") Long id) {
        User user = userService.getUserById(id);
        user.setId(id);
        ModelAndView model = new ModelAndView();
        model.addObject("user",user);
        model.setViewName("edit-user");
        return model;

    }



    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/users")
    public ModelAndView createUser(User user, String[] groupName, ModelAndView model) {

        User userExistsByUsername = userService.findUserByUsername(user.getUsername());
        User userExistsByEmail = userService.findUserByEmail(user.getEmail());
        if (userExistsByUsername != null || userExistsByEmail!=null) {
            if (userExistsByUsername != null) {
                model.addObject("nameExist","Username exist");
            }
            if (userExistsByEmail!=null) {
                model.addObject("emailExist","Email exist");
            }
            List<Group> groupList = groupService.getAllGroups();
            List<String> groupNameList = new ArrayList<>();
            for (int i = 0 ; i<groupList.size() ; i++) {
                groupNameList.add(groupList.get(i).getNameEn());
            }
            model.addObject("groupList", groupNameList);
            model.setViewName("add-user");
        } else {
            Group groupObject = new Group();
            List<Group> groupAssigned = new ArrayList<Group>();
            if (groupName != null) {
                for (String group : groupName) {
                    groupObject = groupService.getGroupByName(group);
                    groupObject.setNumberOfAssignedUsers(groupObject.getNumberOfAssignedUsers() + 1);
                    groupService.updateGroup(groupObject);
                    groupAssigned.add(groupObject);
                }
            }
            user.setGroups(groupAssigned);
            userService.createUser(user);
            model.setViewName("redirect:/users");
        }
        return model;
    }

    @PostMapping("/users/edit/{id}")
    public ModelAndView updateUser(@PathVariable  long id, @Validated User user, ModelAndView model) {
        User userToBeUpdated = userService.getUserById(id);
        User userExistsByEmail = userService.findUserByEmail(user.getEmail());


        if ( userExistsByEmail!=null && userExistsByEmail.getId() != userToBeUpdated.getId()) {
            model.addObject("emailExist", "Email exist");
            model.addObject("user",userToBeUpdated);
            model.setViewName("edit-user");
        } else {
            user.setGroups(userToBeUpdated.getGroups());
            user.setRoles(userToBeUpdated.getRoles());
            User updateUser = this.userService.updateUser(user);
            model.addObject("usersList", userService.getAllUsers());
            model.setViewName("redirect:/users");


        }
        return model;

    }

    @PostMapping("/users/{id}")
    public ModelAndView deleteUser(@PathVariable long id) {
        User userToBeDeleted = userService.getUserById(id);
        List<Group> userGroups = userToBeDeleted.getGroups();
        for (int i = 0 ; i<userGroups.size() ; i++) {
            Group group = groupService.getGroupById(userGroups.get(i).getId());
            group.setNumberOfAssignedUsers(group.getNumberOfAssignedUsers()-1);
            groupService.updateGroup(group);
        }
        userService.deleteUser(userToBeDeleted.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }


}
