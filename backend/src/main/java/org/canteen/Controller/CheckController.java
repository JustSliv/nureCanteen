package org.canteen.Controller;

import org.canteen.Repositories.BasketRepo;
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

import static org.canteen.Controller.BasketController.check_id;
import static org.canteen.Controller.BasketController.user_id;

@RestController
@RequestMapping("/api/check")
public class CheckController {

   @Autowired
   private CheckRepo checkRepo;

   @Autowired
   private BasketRepo basketRepo;

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

      checkRepo.save(check);

      return new ResponseEntity<>(check, headers, HttpStatus.CREATED);
   }

   @RequestMapping(value = "/{check_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<String> update(@PathVariable("check_id") Long check_id){
      LocalDateTime currTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

      DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
      checkRepo.setDate(currTime.format(formatter), check_id);

      checkRepo.setTime(currTime.format(formatter1), check_id);
      basketRepo.changeActive(user_id, check_id);
      return ResponseEntity.ok("Все окей");

   }

   @DeleteMapping("/{id}")
      public ResponseEntity<Check> deleteOne(@PathVariable Long id) {
         checkRepo.deleteById(id);

         return new ResponseEntity<>(HttpStatus.OK);
   }

}
