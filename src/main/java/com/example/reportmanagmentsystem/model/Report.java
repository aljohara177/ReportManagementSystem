package com.example.reportmanagmentsystem.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "REPORTS")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "REPORT_ID")
    private long id;

    @Column(nullable = false, name = "REPORT_NAME")
    private String reportName;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "CREATED_AT")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;

    @OneToMany(mappedBy = "report",
            cascade = CascadeType.ALL)
    private Set<Attachment> attachments = new HashSet<>();

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Report(long id, String reportName, String content, String tag, Date createdAt, String createdBy, Group group) {
        this.id = id;
        this.reportName = reportName;
        this.content = content;
        this.tag = tag;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.group = group;
    }

    public Report( String reportName, String content, String tag, Date createdAt, String createdBy, Group group) {
        this.reportName = reportName;
        this.content = content;
        this.tag = tag;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.group = group;
    }

    public Report() {
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportName='" + reportName + '\'' +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdateBy='" + lastUpdateBy + '\'' +
                ", group=" + group +
                ", attachments=" + attachments +
                '}';
    }
}
