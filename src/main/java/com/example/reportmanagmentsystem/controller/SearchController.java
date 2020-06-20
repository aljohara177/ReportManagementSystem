package com.example.reportmanagmentsystem.controller;

import com.example.reportmanagmentsystem.model.Group;
import com.example.reportmanagmentsystem.model.Report;
import com.example.reportmanagmentsystem.model.Role;
import com.example.reportmanagmentsystem.model.User;
import com.example.reportmanagmentsystem.repository.ReportRepository;
import com.example.reportmanagmentsystem.service.GroupService;
import com.example.reportmanagmentsystem.service.ReportService;
import com.example.reportmanagmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/search")
    public ModelAndView showSearchPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("search");
        return model;
    }

    @PostMapping("/search")
    public ModelAndView searchForReport(String searchQuery, String searchBy, ModelAndView model, Principal loggedUser) {
        List<Report> searchResult = new ArrayList<Report>();
        System.out.println(searchBy);
        System.out.println(searchQuery);
        User currentUser = userService.findUserByUsername(loggedUser.getName());
        List<Group> assignedGroups = currentUser.getGroups();
        switch (searchBy) {
            case "ReportName":
                if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
                    searchResult = reportService.findReportByReportName(searchQuery);
                } else {
                    for (int i = 0; i < assignedGroups.size(); i++) {
                        List<Report> assignedGroupReports = reportService.findReportByGroupName(assignedGroups.get(i).getNameEn());
                        searchResult.addAll(assignedGroupReports);
                    }
                }
                searchResult = searchResult.stream().filter(r -> r.getReportName().equals(searchQuery)).collect(Collectors.toList());
                break;
            case "Content":
                if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
                    searchResult = reportService.findReportByContent(searchQuery);
                } else {
                    for (int i = 0; i < assignedGroups.size(); i++) {
                        List<Report> assignedGroupReports = reportService.findReportByGroupName(assignedGroups.get(i).getNameEn());
                        searchResult.addAll(assignedGroupReports);
                    }
                }
                searchResult = searchResult.stream().filter(r -> r.getContent().equals(searchQuery)).collect(Collectors.toList());
                break;
            case "Group":
                if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
                    searchResult = reportService.findReportByGroupName(searchQuery);
                } else {
                    for (int i = 0; i < assignedGroups.size(); i++) {
                        List<Report> assignedGroupReports = reportService.findReportByGroupName(assignedGroups.get(i).getNameEn());
                        searchResult.addAll(assignedGroupReports);
                    }
                }
                searchResult = searchResult.stream().filter(r -> r.getGroup().getNameEn().equals(searchQuery)).collect(Collectors.toList());
                break;
            case "Tag":
                if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
                        searchResult = reportService.findReportByTag(searchQuery);
                } else {

                    for (int i = 0; i < assignedGroups.size(); i++) {
                        List<Report> assignedGroupReports = reportService.findReportByGroupName(assignedGroups.get(i).getNameEn());
                        searchResult.addAll(assignedGroupReports);
                    }
                }
                searchResult = searchResult.stream().filter(r -> r.getTag().equals(searchQuery)).collect(Collectors.toList());
                break;
            case "CreatedBy":
                if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
                    searchResult = reportService.findReportByCreatedBy(searchQuery);
                } else {
                    for (int i = 0; i < assignedGroups.size(); i++) {
                        List<Report> assignedGroupReports = reportService.findReportByGroupName(assignedGroups.get(i).getNameEn());
                        searchResult.addAll(assignedGroupReports);
                    }
                }
                searchResult = searchResult.stream().filter(r -> r.getCreatedBy().equals(searchQuery)).collect(Collectors.toList());
                break;

            case "LastUpdateBy":
                if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
                    searchResult = reportService.findReportByLastUpdateBy(searchQuery);
                } else {
                    for (int i = 0; i < assignedGroups.size(); i++) {
                        List<Report> assignedGroupReports = reportService.findReportByGroupName(assignedGroups.get(i).getNameEn());
                        searchResult.addAll(assignedGroupReports);
                    }
                }
                searchResult = searchResult.stream().filter(r -> r.getLastUpdateBy().equals(searchQuery)).collect(Collectors.toList());
                break;

        }
        model.addObject("reportsList",searchResult);
        model.setViewName("search-results");
        return model;
    }
}
