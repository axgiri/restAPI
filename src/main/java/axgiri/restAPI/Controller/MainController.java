package axgiri.restAPI.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import axgiri.restAPI.Entity.Cat;
import axgiri.restAPI.Repository.CatRepo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController{

    
    private final CatRepo catRepo;
    private final ObjectMapper objectMapper;
    
    @PostMapping("/rest/post/addCatToDB")
    public void addCatToDB(@RequestBody Cat cat){
        Cat savedCat = catRepo.save(cat);
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
    
    @GetMapping("/rest/getAll")
    public ResponseEntity<List<Cat>> getAll(){
        List<Cat> cats = catRepo.findAll();
        try{
            return ResponseEntity.ok(cats);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // @GetMapping("/api/all")
    // public List<Cat> getAll1Cats(){
    //     return CatRepo.findAll();
    // }

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
