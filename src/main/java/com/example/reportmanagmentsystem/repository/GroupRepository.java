package com.example.reportmanagmentsystem.repository;

import com.example.reportmanagmentsystem.model.Group;
import com.example.reportmanagmentsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> getGroupByNameEn(String nameEn);

}
