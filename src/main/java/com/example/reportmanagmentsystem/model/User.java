package com.example.reportmanagmentsystem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "USERS")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
        @GenericGenerator(name = "seq", strategy="increment")
        @Column(name = "USER_ID")
        private long id;

        @Column(nullable = false, name = "NAME")
        private String name;

        @Column(unique = true, nullable = false, name = "USER_NAME")
        private String username;

        @Column (nullable = false, name = "PASSWORD")
        private String password;

        @Column(nullable = false , unique = true, name = "EMAIL")
        private String email;

        @Column(nullable = false , name = "MOBILE_NUMBER")
        private String mobileNumber;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="USER_ROLE",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ROLE_ID")})
    private List<Role> roles;

        @ManyToMany
        @JoinTable(name = "USERS_GROUPS",
                joinColumns = {
                        @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID",
                                nullable = false, updatable = false)},
                inverseJoinColumns = {
                        @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID",
                                nullable = false, updatable = false)})

        private List<Group> groups;

    public User(long id, String name, String username, String password, String email, String mobileNumber,  List<Group> groups) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.groups = groups;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(long id, String name, String username, String password, String email, String mobileNumber) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;

    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", roles=" + roles +
                ", groups=" + groups +
                '}';
    }
}
