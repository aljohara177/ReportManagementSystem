package com.example.reportmanagmentsystem.service;

import com.example.reportmanagmentsystem.exceptions.ItemNotFoundException;
import com.example.reportmanagmentsystem.model.Group;
import com.example.reportmanagmentsystem.model.User;
import com.example.reportmanagmentsystem.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class GroupServiceImpl implements GroupService{


    @Autowired
    GroupRepository groupRepository;

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Group group) {
        Optional<Group> groupFromDb = groupRepository.findById(group.getId());

        if(groupFromDb.isPresent()) {
            Group groupUpdated = groupFromDb.get();
            groupUpdated.setId(group.getId());
            groupUpdated.setNameEn(group.getNameEn());
            groupUpdated.setNameAr(group.getNameAr());
            groupUpdated.setDescription(group.getDescription());
            //groupUpdated.setNumberOfAssignedUsers(group.getNumberOfAssignedUsers());
            //groupUpdated.setCreatedAt(group.getCreatedAt());
            groupRepository.save(groupUpdated);
            return groupUpdated;
        } else {
            throw new ItemNotFoundException(("Group does not exist"));
        }
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroupById(Long groupId) {
        Optional<Group> groupFromDb = groupRepository.findById(groupId);

        if (groupFromDb.isPresent()) {
            return groupFromDb.get();
        } else {
            throw new ItemNotFoundException(("Group does not exist"));
        }
    }

    @Override
    public void deleteGroup(long groupId) {
        Optional<Group> groupFromDb = groupRepository.findById(groupId);

        if (groupFromDb.isPresent()) {
            this.groupRepository.delete(groupFromDb.get());
        } else {
            throw  new ItemNotFoundException(("Group does not exist"));
        }
    }
    @Override
    public Group getGroupByName(String groupName){
        Optional<Group> groupFromDb = groupRepository.getGroupByNameEn(groupName);

        if (groupFromDb.isPresent()) {
            return groupFromDb.get();
        } else {
            throw new ItemNotFoundException(("Group does not exist"));
        }
    }
}
