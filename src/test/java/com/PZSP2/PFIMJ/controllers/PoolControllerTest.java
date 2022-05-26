package com.PZSP2.PFIMJ.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import com.PZSP2.PFIMJ.db.entities.Pool;
import com.PZSP2.PFIMJ.db.entities.Subject;
import com.PZSP2.PFIMJ.models.PoolModel;
import com.PZSP2.PFIMJ.services.PoolsService;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class PoolControllerTest {

    @MockBean
    private PoolsService poolsService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void SanityCheck(){
        assertTrue(true);
    }

    @WithMockUser(value = "user")
    @Test
    void addPool() throws Exception {
        Pool pool = new Pool();
        Subject subject = new Subject();
        subject.setId(1L);
        pool.setSubject(subject);
        PoolModel poolModel = new PoolModel(pool.getId(),pool.getName(),pool.getDescription(),pool.getSubject().getId());
        when(poolsService.addPool(poolModel)).thenReturn(pool);

        mockMvc.perform(post("/api/pool/add").
                contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"name\": \"test9\",\n" +
                        "    \"description\": \"Testowa pula zadan\",\n" +
                        "    \"subjectId\": 2454\n" +
                        "}")).andExpect(status().isOk());
    }

    @WithMockUser(value = "user")
    @Test
    void getPools() throws Exception {
        Pool pool = new Pool();
        Subject subject = new Subject();
        subject.setId(1L);
        pool.setSubject(subject);
        PoolModel poolModel = new PoolModel(1L,"T","P",1L);
        when(poolsService.getSubjectPools(1L)).thenReturn(List.of(poolModel));

        mockMvc.perform(get("/api/pool/pools/{subject_id}",1L))
                .andExpect(status().isOk()).andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"T\",\n" +
                        "    \"description\":\"P\",\n" +
                        "    \"subjectId\":1\n" +
                        "  }\n" +
                        "]"));
    }

    @WithMockUser(value = "user")
    @Test
    void updateNamePool() throws Exception {
        when(poolsService.renamePool(any(), any())).thenReturn(true);
        mockMvc.perform(put("/api/pool/rename/{poolId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"UpdatedName\"}"))
                .andExpect(status().isOk());
        verify(poolsService).renamePool(any(), any());
    }

    @WithMockUser(value = "user")
    @Test
    void updateNamePoolNotFound() throws Exception {
        when(poolsService.renamePool(any(), any())).thenReturn(false);
        mockMvc.perform(put("/api/pool/rename/{poolId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"UpdatedName\"}"))
                .andExpect(status().isNotFound());
        verify(poolsService).renamePool(any(), any());
    }

    @WithMockUser(value = "user")
    @Test
    void updateDescriptionPool() throws Exception {
        when(poolsService.changeDescriptionPool(any(), any())).thenReturn(true);
        mockMvc.perform(put("/api/pool/description/{poolId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\" : \"Description\"}"))
                .andExpect(status().isOk());
        verify(poolsService).changeDescriptionPool(any(), any());
    }
    @WithMockUser(value = "user")
    @Test
    void updateDescriptionPoolNotFound() throws Exception {
        when(poolsService.changeDescriptionPool(any(), any())).thenReturn(false);
        mockMvc.perform(put("/api/pool/description/{poolId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\" : \"Description\"}"))
                .andExpect(status().isNotFound());
        verify(poolsService).changeDescriptionPool(any(), any());
    }

    @WithMockUser(value = "user")
    @Test
    void deletePool() throws Exception {
        when(poolsService.deletePool(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/pool/delete/{poolId}", 1L))
                .andExpect(status().isOk());
        verify(poolsService).deletePool(1L);
    }
    @WithMockUser(value = "user")
    @Test
    void deletePoolNotFound() throws Exception {
        when(poolsService.deletePool(1L)).thenReturn(false);
        mockMvc.perform(delete("/api/pool/delete/{poolId}", 1L))
                .andExpect(status().isNotFound());
        verify(poolsService).deletePool(1L);
    }


    @WithMockUser(value = "user")
    @Test
    void importPoolstoSubject() throws Exception {
        List<Long> pools= new ArrayList<Long>();
        pools.add(1L);
        when(poolsService.createPoolsCopy(any(),any())).thenReturn(true);
        mockMvc.perform(post("/api/pool/import/{subjectId}",1L)
                .contentType(MediaType.APPLICATION_JSON).content("[1]"))
                .andExpect(status().isOk());
        verify(poolsService).createPoolsCopy(any(),any());
    }
}