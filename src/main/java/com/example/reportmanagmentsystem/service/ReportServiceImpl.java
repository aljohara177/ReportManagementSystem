package com.example.reportmanagmentsystem.service;

import com.example.reportmanagmentsystem.exceptions.ItemNotFoundException;
import com.example.reportmanagmentsystem.model.Group;
import com.example.reportmanagmentsystem.model.Report;

import com.example.reportmanagmentsystem.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Override
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(Report report) {

            return reportRepository.save(report);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Report getReportById(Long reportId) {
        Optional<Report> reportFromDb = reportRepository.findById(reportId);

        if (reportFromDb.isPresent()) {
            return reportFromDb.get();
        } else {
            throw new ItemNotFoundException(("Report does not exist"));
        }
    }

    @Override
    public void deleteReport(long reportId) {
        Report reportToBeDeleted = reportRepository.findById(reportId).get();
        reportRepository.delete(reportToBeDeleted);

    }

    @Override
    public List<Report> findReportByGroupName(String group) {
        return reportRepository.findReportByGroup(group);
    }

    @Override
    public List<Report> findReportByTag(String tag) {
        return reportRepository.findReportByTag(tag);
    }

    @Override
    public List<Report> findReportByReportName(String reportName) {
        return reportRepository.findReportByReportName(reportName);
    }

    @Override
    public List<Report> findReportByCreatedBy(String createdBy) {
        return reportRepository.findReportByCreatedBy(createdBy);
    }

    @Override
    public List<Report> findReportByLastUpdateBy(String lastUpdateBy) {
        return reportRepository.findReportByLastUpdateBy(lastUpdateBy);
    }
    @Override
    public List<Report> findReportByContent(String content) {
        return reportRepository.findReportByContent(content);
    }


}
