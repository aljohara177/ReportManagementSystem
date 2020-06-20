package com.example.reportmanagmentsystem.model;


import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table( name = "GROUPS")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GROUP_ID")
    private long id;

    @Column(nullable = false, unique = true, name = "NAME_ENGLISH")
    private String nameEn;

    @Column(name = "NAME_ARABIC")
    private String nameAr;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NUMBER_OF_ASSIGNED_USERS")
    private Long numberOfAssignedUsers;


    @Column(name = "CREATED_AT")
    @CreationTimestamp
    private Date createdAt;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "group",
            cascade = CascadeType.ALL)
    private Set<Report> reports = new HashSet<>();


    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Group(long id, String nameEn, String nameAr, String description, Long numberOfAssignedUsers, Date createdAt, Set<User> users, Set<Report> reports) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameAr = nameAr;
        this.description = description;
        this.numberOfAssignedUsers = numberOfAssignedUsers;
        this.createdAt = createdAt;
        this.users = users;
        this.reports = reports;
    }

    public Group(String nameEn, String nameAr, String description, Long numberOfAssignedUsers, Date createdAt, Set<User> users, Set<Report> reports) {
        this.nameEn = nameEn;
        this.nameAr = nameAr;
        this.description = description;
        this.numberOfAssignedUsers = numberOfAssignedUsers;
        this.createdAt = createdAt;
        this.users = users;
        this.reports = reports;
    }

    public Group() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getNumberOfAssignedUsers() {
        return numberOfAssignedUsers;
    }

    public void setNumberOfAssignedUsers(Long numberOfAssignedUsers) {
        this.numberOfAssignedUsers = numberOfAssignedUsers;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", nameAr='" + nameAr + '\'' +
                ", description='" + description + '\'' +
                ", numberOfAssignedUsers=" + numberOfAssignedUsers +
                ", createdAt=" + createdAt +
                '}';
    }
}

