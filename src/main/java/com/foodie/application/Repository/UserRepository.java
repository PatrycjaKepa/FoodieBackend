package com.foodie.application.Repository;

import com.foodie.application.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> { // wykonuje wszystkie zapytania na bazie danych, np.pobieranie po id (tylko pobieranie informacji)
    User findByEmail(String email);
}
