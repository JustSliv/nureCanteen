package org.nure.Controller;

import org.nure.dto.BasketDto;
import org.nure.models.Basket;
import org.nure.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

   @Autowired
   private BasketService basketService;

   @GetMapping("/{id}")
   @ResponseBody
   public ResponseEntity<Basket> getOneBasket(@PathVariable Long id){

      return ResponseEntity.ok(basketService.findOneById(id));
   }

   @GetMapping("/user/{id}")
   @ResponseBody
   public ResponseEntity<List<BasketDto>> getUserBasket(@PathVariable Long id){
      return ResponseEntity.ok(basketService.getUserBasket(id));
   }

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Basket> addOneBasket(@RequestBody Basket basket){

      if(basket == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      basketService.save(basket);

      return ResponseEntity.ok(basket);
   }

   @GetMapping("/all")
   public ResponseEntity<List<Basket>> getAllBaskets(){

      return ResponseEntity.ok(basketService.getAll());
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Basket> updateOneBasket(@RequestBody Basket newBasket, @PathVariable Long id){
      basketService.basketUpdate(id, newBasket);
      return ResponseEntity.ok(newBasket);
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Basket> deleteOne(@PathVariable Long id) {
      basketService.basketDelete(id);

      return new ResponseEntity<>(HttpStatus.OK);
   }

   @DeleteMapping("/user/{user_id}/{check_id}")
   public ResponseEntity<Basket> changeBasketStatusByUserId(@PathVariable Long user_id, @PathVariable Long check_id){

      basketService.changeBasketStatusByUserId(user_id, check_id);
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
