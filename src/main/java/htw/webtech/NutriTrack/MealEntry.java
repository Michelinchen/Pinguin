package htw.webtech.NutriTrack;

import jakarta.persistence.*;

@Entity
public class MealEntry {

    private String name;
    private boolean favorite;
    private String owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Macronutrient macro;

    public MealEntry() {}

    public MealEntry( String name,  Macronutrient macro){
        this.name = name;
        this.macro = macro;
    }

    public Long getId() {return id;}

    public String getName() {
        return name;
    }

    public Macronutrient getMacro() {
        return macro;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(Long id) {this.id = id;}

    public void setName(String name) {this.name = name;}

    public void setMacro(Macronutrient macro) {this.macro = macro;}

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
