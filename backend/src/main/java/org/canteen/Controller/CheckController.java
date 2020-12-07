package org.canteen.Controller;

import org.canteen.Repositories.CheckRepo;
import org.canteen.Repositories.dto.CheckDTO;
import org.canteen.models.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/check")
public class CheckController {

   @Autowired
   private CheckRepo checkRepo;

   @GetMapping("/all")
   public ResponseEntity<List<Check>> allProducts(){

      return ResponseEntity.ok(checkRepo.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<List<CheckDTO>> getOne(@PathVariable Long id){


      return ResponseEntity.ok(checkRepo.getProductSumInCheck(id));
   }

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Check> saveProduct(@RequestBody Check check){
      HttpHeaders headers = new HttpHeaders();

      if(check == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      LocalDateTime currTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      checkRepo.save(check);
      checkRepo.setDate(currTime.format(formatter));
      System.out.println(currTime.format(formatter));

      return new ResponseEntity<>(check, headers, HttpStatus.CREATED);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Check> deleteOne(@PathVariable Long id) {
      checkRepo.deleteById(id);

      return new ResponseEntity<>(HttpStatus.OK);
   }

}
