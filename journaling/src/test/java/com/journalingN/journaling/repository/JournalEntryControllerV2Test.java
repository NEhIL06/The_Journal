package com.journalingN.journaling.controller;

import com.journalingN.journaling.entity.JournalEntry;
import com.journalingN.journaling.entity.User;
import com.journalingN.journaling.services.JournalEntryService;
import com.journalingN.journaling.services.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JournalEntryControllerV2.class)
class JournalEntryControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JournalEntryService journalEntryService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "testuser")
    void getAllUserEntriesByUserName_ReturnsEntries() throws Exception {
        // Setup
        User user = new User();
        JournalEntry entry1 = new JournalEntry();
        JournalEntry entry2 = new JournalEntry();
        user.setJournalEntries(Arrays.asList(entry1, entry2));

        when(userService.findByUserName("testuser")).thenReturn(user);

        // Execute & Verify
        mockMvc.perform(get("/journal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.title").value("Title1"))
                .andExpect(jsonPath("$[1].title").value("Title2"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void createEntry_Success() throws Exception {
        // Setup
        JournalEntry newEntry = new JournalEntry("New Title", "New Content");
        when(journalEntryService.saveEntry(any(), eq("testuser"))).thenReturn(newEntry);

        // Execute & Verify
        mockMvc.perform(post("/journal")
                        .contentType("application/json")
                        .content("{\"title\":\"New Title\",\"content\":\"New Content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void getJournalEntryById_Found() throws Exception {
        // Setup
        ObjectId entryId = new ObjectId();
        JournalEntry entry = new JournalEntry("Test Title", "Test Content");
        User user = new User();
        user.setJournalEntries(Collections.singletonList(entry));

        when(userService.findByUserName("testuser")).thenReturn(user);
        when(journalEntryService.findByID(entryId)).thenReturn(Optional.of(entry));

        // Execute & Verify
        mockMvc.perform(get("/journal/id/" + entryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void deleteEntryById_Success() throws Exception {
        // Setup
        ObjectId entryId = new ObjectId();
        when(journalEntryService.deleteById(eq(entryId), eq("testuser"))).thenReturn(true);

        // Execute & Verify
        mockMvc.perform(delete("/journal/id/" + entryId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser")
    void updateJournalById_Success() throws Exception {
        // Setup
        ObjectId entryId = new ObjectId();
        JournalEntry existing = new JournalEntry("Old Title", "Old Content");
        JournalEntry updated = new JournalEntry("Updated Title", "Updated Content");
        User user = new User();
        user.setJournalEntries(Collections.singletonList(existing));

        when(userService.findByUserName("testuser")).thenReturn(user);
        when(journalEntryService.findByID(entryId)).thenReturn(Optional.of(existing));

        // Execute & Verify
        mockMvc.perform(put("/journal/id/" + entryId)
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Title\",\"content\":\"Updated Content\"}"))
                .andExpect(status().isOk());
    }
}