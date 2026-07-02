package htw.webtech.NutriTrack;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<MealEntry, Long> {
    List<MealEntry> findByOwner(String owner);

}