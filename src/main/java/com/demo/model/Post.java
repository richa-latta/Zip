package com.demo.model;

import java.util.Objects;

/**
 * Represents a Post entity from the JSONPlaceholder API.
 * Used to demonstrate REST API client implementation and testing.
 */
public class Post {
    private int id;
    private int userId;
    private String title;
    private String body;

    /**
     * Default constructor.
     */
    public Post() {
    }

    /**
     * Parameterized constructor.
     *
     * @param id the post ID
     * @param userId the ID of the user who created the post
     * @param title the post title
     * @param body the post content
     */
    public Post(int id, int userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && userId == post.userId
                && Objects.equals(title, post.title)
                && Objects.equals(body, post.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, body);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", userId=" + userId
                + ", title='" + title + '\''
                + ", body='" + body + '\''
                + '}';
    }
}
