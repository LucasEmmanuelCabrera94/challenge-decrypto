package challenge.decrypto.controllers;

import challenge.decrypto.models.principals.PrincipalDTO;
import challenge.decrypto.models.principals.PrincipalRequestDTO;
import challenge.decrypto.models.principals.UpdatePrincipalRequestDTO;
import challenge.decrypto.services.interfaces.PrincipalService;
import challenge.decrypto.utils.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrincipalController.class)
public class PrincipalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrincipalService principalService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<PrincipalDTO> principalList;
    private PrincipalDTO principal;

    @BeforeEach
    public void setUp() {
        principalList = new ArrayList<>();
        principal = PrincipalDTO.builder().id(1L).description("Comitente 1").build();
        principalList.add(principal);
        principalList.add(PrincipalDTO.builder().id(2L).description("Comitente 2").build());
    }

    @Test
    public void testCreatePrincipal_Success() throws Exception {
        PrincipalRequestDTO requestDTO = PrincipalRequestDTO.builder()
                .markets(List.of(1L, 2L)).description("Nuevo Comitente").build();

        PrincipalDTO createdPrincipal = PrincipalDTO.builder().id(1L).description("Nuevo Comitente").build();
        Mockito.when(principalService.createPrincipal(any(PrincipalRequestDTO.class))).thenReturn(createdPrincipal);

        mockMvc.perform(post("/comitentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("Nuevo Comitente")));
    }

    @Test
    public void testGetAllPrincipals_Success() throws Exception {
        Mockito.when(principalService.getAllPrincipals()).thenReturn(principalList);

        mockMvc.perform(get("/comitentes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("Comitente 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].description", is("Comitente 2")));
    }

    @Test
    public void testGetPrincipalById_Success() throws Exception {
        Mockito.when(principalService.getPrincipalById(1L)).thenReturn(principal);

        mockMvc.perform(get("/comitentes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("Comitente 1")));
    }

    @Test
    public void testGetPrincipalById_NotFound() throws Exception {
        Mockito.when(principalService.getPrincipalById(anyLong())).thenThrow(new NotFoundException("Comitente no encontrado"));

        mockMvc.perform(get("/comitentes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePrincipal_Success() throws Exception {
        UpdatePrincipalRequestDTO requestDTO = new UpdatePrincipalRequestDTO();
        requestDTO.setDescription("Comitente Actualizado");

        PrincipalDTO updatedPrincipal = PrincipalDTO.builder().id(1L).description("Comitente Actualizado").build();
        Mockito.when(principalService.updatePrincipal(anyLong(), any(UpdatePrincipalRequestDTO.class))).thenReturn(updatedPrincipal);

        mockMvc.perform(put("/comitentes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("Comitente Actualizado")));
    }

    @Test
    public void testDeletePrincipal_Success() throws Exception {
        Mockito.when(principalService.deletePrincipal(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/comitentes/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePrincipal_NotFound() throws Exception {
        Mockito.when(principalService.deletePrincipal(anyLong())).thenThrow(new NotFoundException("Comitente no encontrado"));

        mockMvc.perform(delete("/comitentes/99"))
                .andExpect(status().isNotFound());
    }
}
