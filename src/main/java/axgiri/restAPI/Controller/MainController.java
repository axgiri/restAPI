package axgiri.restAPI.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import axgiri.restAPI.DTO.CatDTO;
import axgiri.restAPI.Entity.Cat;
import axgiri.restAPI.Repository.CatRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "the main methods of interaction with RestAPI")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController{
    private final CatRepo catRepo;
    private final ObjectMapper objectMapper;
    
    @Operation(
        summary = "adds a cat to the database",
        description = "gets the DTO of cat, collects the cat through the builder, saves the entity in the database"
    )
    @PostMapping("/rest/post/addCatToDB")
    public void addCatToDB(@RequestBody CatDTO catDTO){
        Cat savedCat = catRepo.save(Cat.builder()
        .age(catDTO.getAge())
        .weight(catDTO.getWeight())
        .name(catDTO.getName())
        .build());
        log.info(String.format("cat saived %d", savedCat.getAge()));
    }
    
    @GetMapping("/rest/get/sterAndDester/MVC/addToDB")
    public String deSter() throws JsonProcessingException{
        List<Cat> cats = catRepo.findAll();
        try{
            return objectMapper.writeValueAsString(cats);
        } catch (JsonProcessingException e){
            throw e;
        }
    }


@PostMapping("/rest/post/sterAndDester/MVC/addToDataBaseBySQL")
public ResponseEntity<String> addToDB(@RequestBody CatDTO catDTO) {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cats", "root", "root");
        String sql = "insert into cats (name, weight, age) VALUES (?, ?, ?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, catDTO.getName());
        statement.setInt(2, catDTO.getWeight());
        statement.setInt(3, catDTO.getAge());
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return ResponseEntity.ok("cat added");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    } catch (SQLException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail " + e.getMessage());
    } finally {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
        }
    }
}

    
    @GetMapping("/rest/getAll")
    public ResponseEntity<List<Cat>> getAll(){
        List<Cat> cats = catRepo.findAll();
        try{
            return ResponseEntity.ok(cats);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/rest/selectByID")
    public Cat getCat(@RequestParam int id){
        return catRepo.findById(id).orElseThrow();
    }
    
    @DeleteMapping ("/rest/deleteByID")
    public void deleteByCatById(@RequestParam int id){
        catRepo.deleteById(id);
    }

    @PutMapping("/rest/put")
    public String changeCat(@RequestBody Cat cat) {
        if (catRepo.existsById(cat.getAge())){
            return "no such row";
        }
        return catRepo.save(cat).toString(); 
    }
} 