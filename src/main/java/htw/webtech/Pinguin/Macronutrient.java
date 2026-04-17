package htw.webtech.Pinguin;

public class Macronutrient {
    private  double countFat;
    private  double countCarbs;
    private  double countProteins;

    public Macronutrient(double countFat, double countCarbs, double countProteins) {
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
}
