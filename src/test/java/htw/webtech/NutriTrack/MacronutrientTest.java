package htw.webtech.NutriTrack;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MacronutrientTest {

    @Test
    void testTotalCalories(){
        Macronutrient macro = new Macronutrient(10, 20, 5);

        double actual = macro.getTotalCalories();
        double expected = 190;

        assertEquals(expected, actual);
    }
}
