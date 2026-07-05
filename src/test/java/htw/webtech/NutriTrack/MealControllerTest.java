package htw.webtech.NutriTrack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(MealController.class)
public class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MealService service;

    private MealEntry burger;
    private MealEntry pizza;
    private String owner;

    @BeforeEach
    public void setUpMealEntriesAndOwner(){
        burger = new MealEntry("Burger", new Macronutrient(20, 3, 2));
        pizza = new MealEntry("Pizza", new Macronutrient(39,4,2));
        owner = "test@test.de";
    }

    @Test
    public void testGetRoute() throws Exception {
        List<MealEntry> mealList = List.of(burger, pizza);
        when(service.getAllMeals(owner)).thenReturn(mealList);

        this.mockMvc.perform(get("/meals").param("Owner", owner))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Burger"));
    }

    @Test
    public void testDeleteRoute() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/meals/1").param("Owner", owner))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPostRoute() throws Exception{
        doReturn(burger).when(service).save(any(MealEntry.class), eq(owner));

        this.mockMvc.perform(post("/meals").param("Owner", owner).contentType(MediaType.APPLICATION_JSON)
                .content("""
                {"name":"Burger","macro":{"countFat":20,"countCarbs":3,"countProteins":2}}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Burger"));


    }

}
