package com.PZSP2.PFIMJ.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import com.PZSP2.PFIMJ.controllers.TestController;
import com.PZSP2.PFIMJ.models.TestModel;
import com.PZSP2.PFIMJ.services.TestService;
import com.lowagie.text.Header;

import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTests {
    

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;
    
    @Test
    void SanityCheck(){
        assertTrue(true);
    }

    @WithMockUser(value = "user")
    @Test
    void addTestTest() throws Exception{
        mockMvc.perform(post("/api/tests/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"mytest123\",\"description\" : \"sampletest\",\"subjectId\" : 1}"))
                        .andExpect(status().isOk());
        verify(testService).addTest(any());
    }

    @Test
    void addTestnoAuthTest() throws Exception{
        mockMvc.perform(post("/api/tests/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"mytest123\",\"description\" : \"sampletest\",\"subjectId\" : 1}"))
                        .andExpect(status().isUnauthorized());
        verify(testService, times(0)).addTest(any());
    }

    @WithMockUser(value = "user")
    @Test
    void getSubjectTestsTest() throws Exception{
        mockMvc.perform(get("/api/tests/{subjectId}", 1L))
                        .andExpect(status().isOk());
        verify(testService).getSubjectTests(1L);
    }

    @WithMockUser(value = "user")
    @Test
    void getTestsByTitleLikeTest() throws Exception{
        mockMvc.perform(get("/api/tests/search/{subjectId}/{title}", 1L, "title"))
                        .andExpect(status().isOk());
        verify(testService).getTestsByIdAndTitleLike(1L, "title");
    }

    @WithMockUser(value = "user")
    @Test
    void deleteTestTest() throws Exception{
        when(testService.deleteTest(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/tests/{testId}", 1L))
                        .andExpect(status().isOk());
        verify(testService).deleteTest(1L);
    }

    @WithMockUser(value = "user")
    @Test
    void deleteMissingTestTest() throws Exception{
        when(testService.deleteTest(1L)).thenReturn(false);
        mockMvc.perform(delete("/api/tests/{testId}", 1L))
                        .andExpect(status().isNotFound());
        verify(testService).deleteTest(1L);
    }

    @WithMockUser(value = "user")
    @Test
    void updateDescriptionTestTest() throws Exception{
        when(testService.changeDescriptionPool(any(), any())).thenReturn(true);
        mockMvc.perform(put("/api/tests/description/{testId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\" : \"UpdatedDescription\"}"))
                        .andExpect(status().isOk());
        verify(testService).changeDescriptionPool(any(), any());
    }

    @WithMockUser(value = "user")
    @Test
    void updateDescriptionMissingTestTest() throws Exception{
        when(testService.changeDescriptionPool(any(), any())).thenReturn(false);
        mockMvc.perform(put("/api/tests/description/{testId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\" : \"UpdatedDescription\"}"))
                        .andExpect(status().isNotFound());
        verify(testService).changeDescriptionPool(any(), any());
    }

    @WithMockUser(value = "user")
    @Test
    void updateTitleTestTest() throws Exception{
        when(testService.renameTest(any(), any())).thenReturn(true);
        mockMvc.perform(put("/api/tests/rename/{testId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\" : \"RenamedTest2\"}"))
                        .andExpect(status().isOk());
        verify(testService).renameTest(any(), any());
    }

    @WithMockUser(value = "user")
    @Test
    void updateTitleMissingTestTest() throws Exception{
        when(testService.renameTest(any(), any())).thenReturn(false);
        mockMvc.perform(put("/api/tests/rename/{testId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\" : \"RenamedTest2\"}"))
                        .andExpect(status().isNotFound());
        verify(testService).renameTest(any(), any());
    }
}
