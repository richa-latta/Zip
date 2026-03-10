package com.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for User model class.
 */
class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void testParameterizedConstructor() {
        User user = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");

        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("123-456-7890", user.getPhone());
        assertEquals("example.com", user.getWebsite());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setId(2);
        user.setName("Jane Smith");
        user.setUsername("janesmith");
        user.setEmail("jane@example.com");
        user.setPhone("098-765-4321");
        user.setWebsite("janesmith.com");

        assertEquals(2, user.getId());
        assertEquals("Jane Smith", user.getName());
        assertEquals("janesmith", user.getUsername());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("098-765-4321", user.getPhone());
        assertEquals("janesmith.com", user.getWebsite());
    }

    @Test
    void testEquals_SameObject() {
        User user = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");

        assertTrue(user.equals(user));
    }

    @Test
    void testEquals_EqualObjects() {
        User user1 = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");
        User user2 = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");

        assertTrue(user1.equals(user2));
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testEquals_DifferentId() {
        User user1 = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");
        User user2 = new User(2, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");

        assertFalse(user1.equals(user2));
    }

    @Test
    void testEquals_Null() {
        User user = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");

        assertFalse(user.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        User user = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");

        assertFalse(user.equals("Not a User"));
    }

    @Test
    void testHashCode_ConsistentWithEquals() {
        User user1 = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");
        User user2 = new User(1, "John Doe", "johndoe", "john@example.com",
                "098-765-4321", "different.com");

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testGetDisplayName() {
        User user = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");

        assertEquals("John Doe (@johndoe)", user.getDisplayName());
    }

    @Test
    void testToString() {
        User user = new User(1, "John Doe", "johndoe", "john@example.com",
                "123-456-7890", "example.com");

        String result = user.toString();

        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("johndoe"));
        assertTrue(result.contains("john@example.com"));
    }
}
