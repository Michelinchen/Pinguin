package htw.webtech.Pinguin;

public class MealEntry {
    private String name;
    private Macronutrient macro;

    public MealEntry( String name,  Macronutrient macro){
        this.name = name;
        this.macro = macro;
    }

    public String getName() {
        return name;
    }

    public Macronutrient getMacro() {
        return macro;
    }
}
