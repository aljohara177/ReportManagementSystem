package com.example.reportmanagmentsystem.repository;


import com.example.reportmanagmentsystem.model.Group;
import com.example.reportmanagmentsystem.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long>{
    @Query("SELECT r FROM Report r WHERE r.reportName LIKE %?1%")
    List<Report> findReportByReportName(String reportName);

    @Query("SELECT r FROM Report r WHERE r.createdBy LIKE %?1%")
    List<Report> findReportByCreatedBy(String createdBy);

    @Query("SELECT r FROM Report r WHERE r.lastUpdateBy LIKE %?1%")
    List<Report> findReportByLastUpdateBy(String lastUpdateBy);

    @Query("SELECT r FROM Report r WHERE r.group.nameEn LIKE %?1%")
    List<Report> findReportByGroup(String group);

    @Query("SELECT r FROM Report r WHERE r.tag LIKE %?1%")
    List<Report> findReportByTag(String tag);

    @Query("SELECT r FROM Report r WHERE r.content LIKE %?1%")
    List<Report> findReportByContent(String content);

}
