package htw.webtech.NutriTrack;

import jakarta.persistence.Embeddable;

@Embeddable
public class Macronutrient{
    private double countFat;
    private double countCarbs;
    private double countProteins;

    public Macronutrient(){}

    public Macronutrient(final double countFat, final double countCarbs, final double countProteins) {
        this.countFat = countFat;
        this.countCarbs = countCarbs;
        this.countProteins = countProteins;
    }

    public double getCountFat() {
        return countFat;
    }

    public double getCountCarbs() {
        return countCarbs;
    }

    public double getCountProteins() {
        return countProteins;
    }

    public void setCountFat(final double countFat){
        this.countFat = countFat;
    }

    public void setCountCarbs(final double countCarbs){ this.countCarbs = countCarbs; }

    public void setCountProteins(double countProteins) { this.countProteins = countProteins; }

    public double getTotalCalories(){
        return this.getCountFat() * 9 + this.getCountCarbs() * 4 + this.getCountProteins() * 4;
    }
}
