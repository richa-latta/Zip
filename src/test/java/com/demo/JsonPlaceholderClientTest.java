package com.demo;

import com.demo.exception.ApiException;
import com.demo.model.Post;
import com.demo.model.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Unit tests for JsonPlaceholderClient.
 * Demonstrates mocking HTTP responses for API testing.
 */
@ExtendWith(MockitoExtension.class)
class JsonPlaceholderClientTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockResponse;

    private JsonPlaceholderClient client;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        client = new JsonPlaceholderClient(mockHttpClient, gson);
    }

    @Test
    void testGetAllUsers_Success() throws Exception {
        // Arrange
        String mockJsonResponse = "[{\"id\":1,\"name\":\"John Doe\","
                + "\"username\":\"johndoe\",\"email\":\"john@example.com\","
                + "\"phone\":\"123-456-7890\",\"website\":\"example.com\"}]";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(mockJsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Act
        List<User> users = client.getAllUsers();

        // Assert
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        assertEquals("johndoe", users.get(0).getUsername());
    }

    @Test
    void testGetUserById_Success() throws Exception {
        // Arrange
        String mockJsonResponse = "{\"id\":1,\"name\":\"John Doe\","
                + "\"username\":\"johndoe\",\"email\":\"john@example.com\","
                + "\"phone\":\"123-456-7890\",\"website\":\"example.com\"}";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(mockJsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Act
        User user = client.getUserById(1);

        // Assert
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testGetUserById_InvalidId() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            client.getUserById(0);
        });

        assertTrue(exception.getMessage().contains("User ID must be positive"));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        // Arrange
        when(mockResponse.statusCode()).thenReturn(404);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            client.getUserById(999);
        });

        assertEquals(404, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Resource not found"));
    }

    @Test
    void testGetAllPosts_Success() throws Exception {
        // Arrange
        String mockJsonResponse = "[{\"id\":1,\"userId\":1,"
                + "\"title\":\"Test Post\",\"body\":\"Test content\"}]";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(mockJsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Act
        List<Post> posts = client.getAllPosts();

        // Assert
        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals("Test Post", posts.get(0).getTitle());
        assertEquals("Test content", posts.get(0).getBody());
    }

    @Test
    void testGetPostsByUserId_Success() throws Exception {
        // Arrange
        String mockJsonResponse = "[{\"id\":1,\"userId\":1,"
                + "\"title\":\"User Post\",\"body\":\"User content\"},"
                + "{\"id\":2,\"userId\":1,"
                + "\"title\":\"Another Post\",\"body\":\"More content\"}]";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(mockJsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Act
        List<Post> posts = client.getPostsByUserId(1);

        // Assert
        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals(1, posts.get(0).getUserId());
        assertEquals(1, posts.get(1).getUserId());
    }

    @Test
    void testGetPostsByUserId_InvalidId() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            client.getPostsByUserId(-1);
        });

        assertTrue(exception.getMessage().contains("User ID must be positive"));
    }

    @Test
    void testCreatePost_Success() throws Exception {
        // Arrange
        Post newPost = new Post(0, 1, "New Post", "New content");
        String mockJsonResponse = "{\"id\":101,\"userId\":1,"
                + "\"title\":\"New Post\",\"body\":\"New content\"}";

        when(mockResponse.statusCode()).thenReturn(201);
        when(mockResponse.body()).thenReturn(mockJsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Act
        Post createdPost = client.createPost(newPost);

        // Assert
        assertNotNull(createdPost);
        assertEquals(101, createdPost.getId());
        assertEquals("New Post", createdPost.getTitle());
    }

    @Test
    void testCreatePost_NullPost() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            client.createPost(null);
        });

        assertTrue(exception.getMessage().contains("Post cannot be null"));
    }

    @Test
    void testCreatePost_ServerError() throws Exception {
        // Arrange
        Post newPost = new Post(0, 1, "New Post", "New content");

        when(mockResponse.statusCode()).thenReturn(500);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            client.createPost(newPost);
        });

        assertEquals(500, exception.getStatusCode());
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(new JsonPlaceholderClient());
    }

    @Test
    void testGetUserById_ClientError() throws Exception {
        when(mockResponse.statusCode()).thenReturn(400);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        ApiException exception = assertThrows(ApiException.class, () -> client.getUserById(1));

        assertEquals(400, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Client error"));
    }

    @Test
    void testGetUserById_ServerError() throws Exception {
        when(mockResponse.statusCode()).thenReturn(503);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        ApiException exception = assertThrows(ApiException.class, () -> client.getUserById(1));

        assertEquals(503, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Server error"));
    }

    @Test
    void testGetUserById_UnexpectedStatus() throws Exception {
        when(mockResponse.statusCode()).thenReturn(301);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        ApiException exception = assertThrows(ApiException.class, () -> client.getUserById(1));

        assertTrue(exception.getMessage().contains("Unexpected status code"));
    }

    @Test
    void testGetAllPosts_NetworkError() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Network error"));

        ApiException exception = assertThrows(ApiException.class, () -> client.getAllPosts());

        assertTrue(exception.getMessage().contains("Request failed"));
    }

    @Test
    void testGetPostsByUserId_NetworkError() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Network error"));

        ApiException exception = assertThrows(ApiException.class,
                () -> client.getPostsByUserId(1));

        assertTrue(exception.getMessage().contains("Request failed"));
    }

    @Test
    void testGetAllPosts_InvalidJson() throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("not valid json");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        assertThrows(ApiException.class, () -> client.getAllPosts());
    }

    @Test
    void testGetUserById_NetworkError() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Network error"));

        ApiException exception = assertThrows(ApiException.class, () -> client.getUserById(1));

        assertTrue(exception.getMessage().contains("Request failed"));
    }

    @Test
    void testGetAllUsers_InvalidJson() throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("not valid json");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        assertThrows(ApiException.class, () -> client.getAllUsers());
    }

    @Test
    void testGetPostsByUserId_InvalidJson() throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("not valid json");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        assertThrows(ApiException.class, () -> client.getPostsByUserId(1));
    }

    @Test
    void testCreatePost_NetworkError() throws Exception {
        Post newPost = new Post(0, 1, "New Post", "New content");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Network error"));

        assertThrows(ApiException.class, () -> client.createPost(newPost));
    }

    @Test
    void testCreatePost_InvalidJson() throws Exception {
        Post newPost = new Post(0, 1, "New Post", "New content");
        when(mockResponse.statusCode()).thenReturn(201);
        when(mockResponse.body()).thenReturn("not valid json");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        assertThrows(ApiException.class, () -> client.createPost(newPost));
    }

    @Test
    void testGetAllUsers_NetworkError() throws Exception {
        // Arrange
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Network error"));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            client.getAllUsers();
        });

        assertTrue(exception.getMessage().contains("Request failed"));
    }

    @Test
    void testGetUserById_InvalidJson() throws Exception {
        // Arrange
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("invalid json");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            client.getUserById(1);
        });

        assertTrue(exception.getMessage().contains("Failed to parse"));
    }
}
