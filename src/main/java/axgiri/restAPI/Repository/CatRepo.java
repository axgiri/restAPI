package axgiri.restAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import axgiri.restAPI.Entity.Cat;

@Repository
public interface CatRepo extends JpaRepository<Cat, Integer>{
    
}
