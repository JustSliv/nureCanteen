package org.canteen.Controller;

import org.canteen.Repositories.BasketRepo;
import org.canteen.Repositories.CheckRepo;
import org.canteen.Repositories.dto.BasketDtoRepo;
import org.canteen.models.Basket;
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
@RequestMapping("/api/basket")
public class BasketController {

   @Autowired
   private BasketRepo basketRepo;

   @Autowired
   private CheckRepo checkRepo;

   public static Long user_id = 0l;
   public static Long check_id = 0l;

   @GetMapping("/{id}")
   @ResponseBody
   public ResponseEntity<Basket> getOne(@PathVariable Long id){

      return ResponseEntity.ok(basketRepo.findById(id).get());
   }

   @GetMapping("/user/{id}")
   @ResponseBody
   public ResponseEntity<List<BasketDtoRepo>> getUserBasket(@PathVariable Long id){
      return ResponseEntity.ok(basketRepo.getUserBasket(id));
   }

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Basket> saveProduct(@RequestBody Basket basket){
      HttpHeaders headers = new HttpHeaders();

      if(basket == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      basket.setActive(true);
      basketRepo.save(basket);

      return new ResponseEntity<>(basket, headers, HttpStatus.CREATED);
   }

   @RequestMapping(value = "/mobile/{check_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Basket> saveProductForMobile(@RequestBody Basket basket, @PathVariable("check_id") Long check_id){
      HttpHeaders headers = new HttpHeaders();
      Check check = new Check();
      if(basket == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      basket.setActive(true);
      check.setCheck_id(check_id);
      basket.setCheck_id(check);
      basketRepo.changeActiveMobile(check_id);
      basketRepo.save(basket);
      LocalDateTime currTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

      DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
      checkRepo.setDate(currTime.format(formatter), check_id);

      checkRepo.setTime(currTime.format(formatter1), check_id);

      return new ResponseEntity<>(basket, headers, HttpStatus.CREATED);
   }

   @GetMapping("/all")
   public ResponseEntity<List<Basket>> getAll(){

      return ResponseEntity.ok(basketRepo.selectAll());
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Basket> putProduct(@RequestBody Basket newBasket, @PathVariable Long id){
      HttpHeaders headers = new HttpHeaders();

      Basket basketToChange = basketRepo.findById(id).get();

      if(Long.valueOf(newBasket.getCount()) != null) {
         basketToChange.setCount(newBasket.getCount());
      }

      basketRepo.save(basketToChange);
      return new ResponseEntity<>(basketToChange, headers, HttpStatus.OK);
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Basket> deleteOne(@PathVariable Long id) {
      basketRepo.deleteById(id);

      return new ResponseEntity<>(HttpStatus.OK);
   }

   @DeleteMapping("/user/{user_id}/{check_id}")
   public ResponseEntity<Basket> deleteByUserId(@PathVariable Long user_id, @PathVariable Long check_id){

//      basketRepo.changeActive(user_id, check_id);
      this.user_id = user_id;
      this.check_id = check_id;
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
