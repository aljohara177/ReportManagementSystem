package com.example.reportmanagmentsystem.service;

import com.example.reportmanagmentsystem.model.Group;
import com.example.reportmanagmentsystem.model.Report;
import com.example.reportmanagmentsystem.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportService {
    Report createReport(Report report);

    Report updateReport(Report report);

    List<Report> getAllReports();

    Report getReportById(Long reportId);

    void deleteReport(long reportId);

        List<Report> findReportByGroupName(String group) ;

    List<Report> findReportByTag(String tag);

    List<Report> findReportByReportName(String reportName);

    List<Report> findReportByCreatedBy(String createdBy);

    List<Report> findReportByLastUpdateBy(String lastUpdateBy);

    List<Report> findReportByContent(String content);

}
