package com.demo.model;

import java.util.Objects;

/**
 * Represents a User entity from the JSONPlaceholder API.
 * Used to demonstrate REST API client implementation and testing.
 */
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Parameterized constructor.
     *
     * @param id the user ID
     * @param name the user's full name
     * @param username the username
     * @param email the email address
     * @param phone the phone number
     * @param website the website URL
     */
    public User(int id, String name, String username, String email, String phone, String website) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Returns a display name combining the full name and username.
     *
     * @return formatted display name, e.g. "John Doe (johndoe)"
     */
    public String getDisplayName() {
        return name + " (@" + username + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && Objects.equals(name, user.name)
                && Objects.equals(username, user.username)
                && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", website='" + website + '\''
                + '}';
    }
}
