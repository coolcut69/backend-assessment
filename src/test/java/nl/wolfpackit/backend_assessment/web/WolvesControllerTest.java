package nl.wolfpackit.backend_assessment.web;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.wolfpackit.backend_assessment.model.Gender;
import nl.wolfpackit.backend_assessment.model.WolfEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WolvesController.class)
class WolvesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WolvesService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createWolf() throws Exception {
        //given
        WolfEntity entity = new WolfEntity();
        entity.setId(5L);
        entity.setBirthDate(DateUtilities.createDate(1978, 8, 25));
        entity.setGender(Gender.MALE);
        entity.setName("Chris Borghmans");

        WolfResource resource = new WolfResource(entity);
        String wolfAsString = mapper.writeValueAsString(resource);
        when(service.createWolf(any(WolfResource.class))).thenReturn(entity);

        //when
        mockMvc.perform(post("/api/wolves")
                .contentType(MediaType.APPLICATION_JSON)
                .content(wolfAsString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(service, times(1)).createWolf(any(WolfResource.class));
    }

    @Test
    void deleteCurrency() throws Exception {
        mockMvc.perform(delete("/api/wolves/{id}", 5L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteWolf(5L);
    }
}
