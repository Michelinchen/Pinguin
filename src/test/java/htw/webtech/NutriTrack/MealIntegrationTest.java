package htw.webtech.NutriTrack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MealIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String owner;

    @BeforeEach
    public void setUpMealEntriesAndOwner(){
        owner = "integration@test.de";
    }

    @Test
    public void testPostThenGet() throws Exception{
        this.mockMvc.perform(post("/meals").param("Owner", owner).contentType(MediaType.APPLICATION_JSON)
                .content("""
                {"name":"Burger","macro":{"countFat":20,"countCarbs":3,"countProteins":2}}
                """))
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/meals").param("Owner", owner))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Burger"));
    }
}

