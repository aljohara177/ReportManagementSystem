package com.example.reportmanagmentsystem.controller;


import com.example.reportmanagmentsystem.model.Group;
import com.example.reportmanagmentsystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/groups")
    public ModelAndView showGroupPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Group> groupsList = this.groupService.getAllGroups();
        modelAndView.addObject("groupsList",groupsList);
        modelAndView.setViewName("groups");
        return modelAndView;
    }

    @GetMapping("/groups/add")
    public ModelAndView showAddGroupPage(Group group, ModelAndView model) {
        model.setViewName("add-group");
        return model;
    }

    @GetMapping("/groups/{id}")
    public ModelAndView showUpdateGroupPage(@PathVariable ("id") Long id) {
            Group group = groupService.getGroupById(id);
            group.setId(id);
            ModelAndView model = new ModelAndView();
            model.addObject("group",group);
            model.setViewName("edit-group");
            return model;

    }


    @PostMapping("/groups")
    public ModelAndView createGroup(Group group, ModelAndView model) {
        //Group groupByName = new Group();
        //groupByName = groupService.getGroupByName(group.getNameEn());
        /*if (groupByName!=null) {
            System.out.println("BINDING RESULT ERROR");
            model.addObject("nameExist","Group name exist");
            model.setViewName("add-group");
        } else {*/
            Group newGroupCreated = new Group();
            newGroupCreated.setNameEn(group.getNameEn());
            newGroupCreated.setNameAr(group.getNameAr());
            newGroupCreated.setDescription(group.getDescription());
            newGroupCreated.setNumberOfAssignedUsers((long) 0);
            groupService.createGroup(newGroupCreated);
            model.setViewName("redirect:/groups");

        //}

        return model;
    }

   @PostMapping("/groups/edit/{id}")
    public ModelAndView updateGroup(@PathVariable  long id, @Validated Group group, ModelAndView model) {

       Group updateGroup = groupService.updateGroup(group);
       if (group == null) {
           model.addObject("error" , "An error occurred when updating group");
           model.setViewName("/groups/{id}");
           return model;
       } else {
           model.addObject("groupsList", groupService.getAllGroups());
           model.setViewName("redirect:/groups");
           return model;
       }
    }

    @PostMapping ("/groups/{id}")
    public ModelAndView deleteGroup(@PathVariable("id") Long id) {
        Group groupToBeDeleted = groupService.getGroupById(id);
        groupService.deleteGroup(groupToBeDeleted.getId());
        ModelAndView model = new ModelAndView();
        model.addObject("notification","Group Deleted Successfully");
        model.setViewName("redirect:/groups");
        return model;
    }
}
