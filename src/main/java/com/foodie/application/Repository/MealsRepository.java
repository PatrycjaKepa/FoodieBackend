package com.foodie.application.Repository;

import com.foodie.application.Entity.Meals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealsRepository extends JpaRepository<Meals, Long> { // wykonuje wszystkie zapytania na bazie danych, np.pobieranie po id (tylko pobieranie informacji)
    Meals findByTitle(String title);
}
