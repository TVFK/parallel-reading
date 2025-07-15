package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.taf.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
