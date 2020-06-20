package com.example.reportmanagmentsystem.service;

import com.example.reportmanagmentsystem.model.Group;

import java.util.List;

public interface GroupService {

    Group createGroup(Group group);

    Group updateGroup(Group group);

    List<Group> getAllGroups();

    Group getGroupById(Long groupId);

    Group getGroupByName(String name);

    void deleteGroup(long groupId);
}
