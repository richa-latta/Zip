package com.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for Post model class.
 */
class PostTest {

    @Test
    void testDefaultConstructor() {
        Post post = new Post();
        assertNotNull(post);
    }

    @Test
    void testParameterizedConstructor() {
        Post post = new Post(1, 10, "Test Title", "Test Body");

        assertEquals(1, post.getId());
        assertEquals(10, post.getUserId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Body", post.getBody());
    }

    @Test
    void testSettersAndGetters() {
        Post post = new Post();
        post.setId(2);
        post.setUserId(20);
        post.setTitle("Updated Title");
        post.setBody("Updated Body");

        assertEquals(2, post.getId());
        assertEquals(20, post.getUserId());
        assertEquals("Updated Title", post.getTitle());
        assertEquals("Updated Body", post.getBody());
    }

    @Test
    void testEquals_SameObject() {
        Post post = new Post(1, 10, "Test Title", "Test Body");

        assertTrue(post.equals(post));
    }

    @Test
    void testEquals_EqualObjects() {
        Post post1 = new Post(1, 10, "Test Title", "Test Body");
        Post post2 = new Post(1, 10, "Test Title", "Test Body");

        assertTrue(post1.equals(post2));
        assertEquals(post1.hashCode(), post2.hashCode());
    }

    @Test
    void testEquals_DifferentId() {
        Post post1 = new Post(1, 10, "Test Title", "Test Body");
        Post post2 = new Post(2, 10, "Test Title", "Test Body");

        assertFalse(post1.equals(post2));
    }

    @Test
    void testEquals_DifferentUserId() {
        Post post1 = new Post(1, 10, "Test Title", "Test Body");
        Post post2 = new Post(1, 20, "Test Title", "Test Body");

        assertFalse(post1.equals(post2));
    }

    @Test
    void testEquals_Null() {
        Post post = new Post(1, 10, "Test Title", "Test Body");

        assertFalse(post.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        Post post = new Post(1, 10, "Test Title", "Test Body");

        assertFalse(post.equals("Not a Post"));
    }

    @Test
    void testHashCode_ConsistentWithEquals() {
        Post post1 = new Post(1, 10, "Test Title", "Test Body");
        Post post2 = new Post(1, 10, "Test Title", "Test Body");

        assertEquals(post1.hashCode(), post2.hashCode());
    }

    @Test
    void testToString() {
        Post post = new Post(1, 10, "Test Title", "Test Body");

        String result = post.toString();

        assertTrue(result.contains("Test Title"));
        assertTrue(result.contains("Test Body"));
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("userId=10"));
    }
}
