package htw.webtech.Pinguin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MealController {

    @GetMapping("/meals")
    public List<MealEntry> getAllMeals(){
        return List.of(
                new MealEntry("Pizza", new Macronutrient(50, 100, 30)),
                new MealEntry("Cheeseburger", new Macronutrient(100, 80, 50)),
                new MealEntry("Pasta", new Macronutrient(50.0, 150.0, 20.0))
        );
    }

}
