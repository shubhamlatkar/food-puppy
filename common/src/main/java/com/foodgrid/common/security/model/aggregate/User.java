package com.foodgrid.common.security.model.aggregate;

import com.foodgrid.common.security.model.entity.UserMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.foodgrid.common.security.utility.UserActivities.*;

@Document
public class User {

    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 10)
    private String phone;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private List<Role> roles;

    private List<String> activeTokens;

    private UserMetadata metadata;

    public User() {
    }

    public User(String username, String phone, String email, String password, List<Role> roles) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.metadata = new UserMetadata(new Date(), new Date(), SIGNUP);
    }

    public List<String> getActiveTokens() {
        return activeTokens;
    }

    public User setActiveTokens(List<String> activeTokens) {
        this.activeTokens = activeTokens;
        if (activeTokens.isEmpty())
            metadata.setLastActivity(LOGOUT);
        return this;
    }

    public UserMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(UserMetadata metadata) {
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", activeTokens=" + activeTokens +
                '}';
    }

    public void addRole(Role role) {
        if (roles == null)
            roles = new ArrayList<>();
        roles.add(role);
    }

    public User removeToken(String token) {
        if (activeTokens != null)
            activeTokens.remove(token);
        metadata.setLastActivity(LOGOUT);
        return this;
    }

    public User addToken(String token) {
        if (activeTokens == null)
            activeTokens = new ArrayList<>();
        metadata.setLastActivity(LOGIN);
        metadata.setLastUpdatedAt(new Date());
        activeTokens.add(token);
        return this;
    }
}
