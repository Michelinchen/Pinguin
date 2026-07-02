package htw.webtech.NutriTrack;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MealController {

    private final MealService service;

    public MealController(MealService service) {
        this.service = service;
    }

    @GetMapping("/meals")
    public List<MealEntry> getAllMeals(@RequestParam("Owner") String owner) {
        return service.getAllMeals(owner);
    }

    @PostMapping("/meals")
    public MealEntry createMeal(@RequestBody MealEntry meal, @RequestParam("Owner") String owner){
        return service.save(meal, owner);
    }

    @DeleteMapping("/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable Long id, @RequestParam("Owner") String owner){
        service.delete(id, owner);
    }

    @PutMapping("/meals/{id}")
    public MealEntry updateMeal(@PathVariable Long id, @RequestBody MealEntry meal, @RequestParam("Owner") String owner){
        return service.update(id, meal, owner);
    }
}
