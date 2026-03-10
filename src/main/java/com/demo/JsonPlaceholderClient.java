package com.demo;

import com.demo.exception.ApiException;
import com.demo.model.Post;
import com.demo.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

/**
 * REST API client for JSONPlaceholder API.
 * Demonstrates best practices for API automation testing including
 * error handling, response parsing, and proper resource management.
 */
public class JsonPlaceholderClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final int TIMEOUT_SECONDS = 10;
    private static final Type USER_LIST_TYPE = new TypeToken<List<User>>() { }.getType();
    private static final Type POST_LIST_TYPE = new TypeToken<List<Post>>() { }.getType();

    private final HttpClient httpClient;
    private final Gson gson;

    /**
     * Default constructor initializing HTTP client and JSON parser.
     */
    public JsonPlaceholderClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
        this.gson = new Gson();
    }

    /**
     * Constructor for dependency injection (useful for testing).
     *
     * @param httpClient the HTTP client to use
     * @param gson the Gson instance for JSON parsing
     */
    public JsonPlaceholderClient(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    /**
     * Retrieves all users from the API.
     *
     * @return list of all users
     * @throws ApiException if the request fails or response cannot be parsed
     */
    public List<User> getAllUsers() throws ApiException {
        String endpoint = BASE_URL + "/users";
        HttpRequest request = buildGetRequest(endpoint);

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            validateResponse(response, endpoint);

            return gson.fromJson(response.body(), USER_LIST_TYPE);
        } catch (JsonSyntaxException e) {
            throw new ApiException("Failed to parse users response", e);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a specific user by ID.
     *
     * @param userId the user ID
     * @return the user object
     * @throws ApiException if the request fails or user is not found
     */
    public User getUserById(int userId) throws ApiException {
        if (userId <= 0) {
            throw new ApiException("User ID must be positive");
        }

        String endpoint = BASE_URL + "/users/" + userId;
        HttpRequest request = buildGetRequest(endpoint);

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            validateResponse(response, endpoint);

            return gson.fromJson(response.body(), User.class);
        } catch (JsonSyntaxException e) {
            throw new ApiException("Failed to parse user response", e);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all posts from the API.
     *
     * @return list of all posts
     * @throws ApiException if the request fails or response cannot be parsed
     */
    public List<Post> getAllPosts() throws ApiException {
        String endpoint = BASE_URL + "/posts";
        HttpRequest request = buildGetRequest(endpoint);

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            validateResponse(response, endpoint);

            return gson.fromJson(response.body(), POST_LIST_TYPE);
        } catch (JsonSyntaxException e) {
            throw new ApiException("Failed to parse posts response", e);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves posts for a specific user.
     *
     * @param userId the user ID
     * @return list of posts by the user
     * @throws ApiException if the request fails or response cannot be parsed
     */
    public List<Post> getPostsByUserId(int userId) throws ApiException {
        if (userId <= 0) {
            throw new ApiException("User ID must be positive");
        }

        String endpoint = BASE_URL + "/posts?userId=" + userId;
        HttpRequest request = buildGetRequest(endpoint);

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            validateResponse(response, endpoint);

            return gson.fromJson(response.body(), POST_LIST_TYPE);
        } catch (JsonSyntaxException e) {
            throw new ApiException("Failed to parse posts response", e);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a new post.
     *
     * @param post the post to create
     * @return the created post with assigned ID
     * @throws ApiException if the request fails or response cannot be parsed
     */
    public Post createPost(Post post) throws ApiException {
        if (post == null) {
            throw new ApiException("Post cannot be null");
        }

        String endpoint = BASE_URL + "/posts";
        String jsonBody = gson.toJson(post);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                throw new ApiException("Failed to create post. Status: " + response.statusCode(),
                        response.statusCode());
            }

            return gson.fromJson(response.body(), Post.class);
        } catch (JsonSyntaxException e) {
            throw new ApiException("Failed to parse create post response", e);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Builds a GET request for the specified endpoint.
     *
     * @param endpoint the API endpoint URL
     * @return configured HTTP request
     */
    private HttpRequest buildGetRequest(String endpoint) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .GET()
                .build();
    }

    /**
     * Validates the HTTP response status code.
     *
     * @param response the HTTP response
     * @param endpoint the endpoint that was called
     * @throws ApiException if the status code indicates an error
     */
    private void validateResponse(HttpResponse<String> response, String endpoint)
            throws ApiException {
        int statusCode = response.statusCode();

        if (statusCode == 404) {
            throw new ApiException("Resource not found: " + endpoint, statusCode);
        } else if (statusCode >= 400 && statusCode < 500) {
            throw new ApiException("Client error: " + statusCode, statusCode);
        } else if (statusCode >= 500) {
            throw new ApiException("Server error: " + statusCode, statusCode);
        } else if (statusCode != 200) {
            throw new ApiException("Unexpected status code: " + statusCode, statusCode);
        }
    }
}
