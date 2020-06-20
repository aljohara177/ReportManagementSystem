package com.example.reportmanagmentsystem.controller;

import com.example.reportmanagmentsystem.model.*;
import com.example.reportmanagmentsystem.service.AttachmentService;
import com.example.reportmanagmentsystem.service.GroupService;
import com.example.reportmanagmentsystem.service.ReportService;
import com.example.reportmanagmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private UserService userService;

    @GetMapping("/reports")
    public ModelAndView showReportPage(Principal loggedUser, ModelAndView modelAndView) {
        User currentUser = userService.findUserByUsername(loggedUser.getName());
        if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
            List<Report> reportsList = new ArrayList<Report>();
            reportsList = reportService.getAllReports();
            modelAndView.addObject("reportsList",reportsList);

        } else {
            List<Group> assignedGroups = currentUser.getGroups();
            List<Report> reportsList = new ArrayList<Report>();
            for (int i=0 ; i<assignedGroups.size() ; i++) {
                List <Report> reports = reportService.findReportByGroupName(assignedGroups.get(i).getNameEn());
                reportsList.addAll(reports);
            }
            modelAndView.addObject("reportsList",reportsList);

        }
        modelAndView.setViewName("reports");
        return modelAndView;
    }

    @GetMapping("/reports/add")
    public ModelAndView showAddReportPage(ModelAndView modelAndView, Principal loggedUser) {
        List<Group> groupList = groupService.getAllGroups();
        List<String> groupName = new ArrayList<>();
        User currentUser = userService.findUserByUsername(loggedUser.getName());
        if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
            for (int i = 0; i < groupList.size(); i++) {
                groupName.add(groupList.get(i).getNameEn());
            }
        } else {
            List<Group> assignedGroups = currentUser.getGroups();
            for (int i = 0; i < assignedGroups.size(); i++) {
                groupName.add(assignedGroups.get(i).getNameEn());
            }
        }
        modelAndView.addObject("groupList", groupName);
        modelAndView.setViewName("add-report");
        return modelAndView;
    }

    @GetMapping("/report/{id}")
    public ModelAndView showReportDetailsPage(@PathVariable long id) {
        Report reportDetails = reportService.getReportById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reportDetails", reportDetails);
        modelAndView.setViewName("report-details");
        return modelAndView;
    }

    @GetMapping("/reports/edit/{id}")
    public ModelAndView ShowEditReportPage(@PathVariable long id, Principal loggedUser) {
        Report reportDetails = reportService.getReportById(id);
        List<Group> groupList = groupService.getAllGroups();
        User currentUser = userService.findUserByUsername(loggedUser.getName());
        List<String> groupName = new ArrayList<>();
        if (currentUser.getRoles().get(0).getRole().equals("ROLE_ADMIN")) {
            for (int i = 0; i < groupList.size(); i++) {
                groupName.add(groupList.get(i).getNameEn());
            }
        } else {
            List<Group> assignedGroups = currentUser.getGroups();
            for (int i = 0; i < assignedGroups.size(); i++) {
                groupName.add(assignedGroups.get(i).getNameEn());
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reportDetails", reportDetails);
        modelAndView.addObject("groupList", groupName);
        modelAndView.setViewName("edit-report");
        return modelAndView;
    }

    @PostMapping("/reports")
    public ModelAndView submitReport(@RequestParam("files") MultipartFile[] files, Report report, String groupName,Principal loggedUser) {
        Group groupAssigned = groupService.getGroupByName(groupName);
        groupAssigned.getReports().add(report);
        report.setGroup(groupAssigned);
        report.setCreatedBy(userService.findUserByUsername(loggedUser.getName()).getName());
        report.setLastUpdateBy("");
        Set<Attachment> attachments = Arrays.asList(files).stream().map(file -> {
            try {
                return uploadFile(file,report);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toSet());

        report.setAttachments(attachments);
        reportService.createReport(report);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/reports");
        return modelAndView;
    }

    @PostMapping("/reports/edit/{id}")
    public ModelAndView updateReport(@PathVariable long id, MultipartFile[] files, Report report,String groupName,Principal loggedUser, ModelAndView model) {
        Report reportToBeUpdated = reportService.getReportById(report.getId());
        if (!groupName.equals(reportToBeUpdated.getGroup().getNameEn())) {
            report.setGroup(groupService.getGroupByName(groupName));
        }else {
            report.setGroup(reportToBeUpdated.getGroup());
        }
            Set<Attachment> attachments = Arrays.asList(files).stream().map(file -> {
                try {
                    return uploadFile(file,report);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toSet());
        if( attachments == null) {
            report.setAttachments(reportToBeUpdated.getAttachments());
        } else {
            report.getAttachments().addAll(attachments);

        }

        report.setId(reportToBeUpdated.getId());
        report.setLastUpdateBy(userService.findUserByUsername(loggedUser.getName()).getName());
        report.setCreatedAt(reportToBeUpdated.getCreatedAt());
        report.setCreatedBy(reportToBeUpdated.getCreatedBy());
        reportService.updateReport(report);
        model.setViewName("redirect:/reports");
        return model;
    }

    @PostMapping("/reports/{id}")
    public ModelAndView deleteReport(@PathVariable("id") Long id, ModelAndView model) {
        Report report = reportService.getReportById(id);
        reportService.deleteReport(report.getId());
        model.setViewName("redirect:/reports");
        return model;
    }



    public Attachment uploadFile(MultipartFile file, Report report) throws IOException {

        Attachment attachment = new Attachment();
        if (file.getOriginalFilename().equals("")){
            return null;
        } else {
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setData(file.getBytes());
            attachment.setReport(report);

            return attachment;
        }
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws FileNotFoundException {
        // Load file as Resource
        Attachment attachment = attachmentService.getFileByFileName(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
