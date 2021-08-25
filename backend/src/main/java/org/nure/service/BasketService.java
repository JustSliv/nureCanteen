package org.nure.service;

import org.nure.Repositories.BasketRepo;
import org.nure.Repositories.CheckRepo;
import org.nure.dto.BasketDto;
import org.nure.models.Basket;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

   private final BasketRepo basketRepo;
   private final CheckRepo checkRepo;

   public BasketService(BasketRepo basketRepo, CheckRepo checkRepo) {
      this.basketRepo = basketRepo;
      this.checkRepo = checkRepo;
   }

      public Basket findOneById(Long basketId){
         return basketRepo.findById(basketId)
            .orElseThrow(() -> new ResourceNotFoundException("Basket with basketId " + basketId + " not found"));
      }

      public List<BasketDto> getUserBasket(Long basketId){
         return basketRepo.getUserBasket(basketId);
      }

      public void save(Basket basket){

         basket.setActive(true);
         basketRepo.save(basket);
      }

      public List<Basket> getAll(){
         return basketRepo.selectAll();
      }

      public Basket basketUpdate(Long basketId, Basket newBasket){

         Basket basketToChange = this.findOneById(basketId);

         if(Long.valueOf(newBasket.getCount()) != null) {
            basketToChange.setCount(newBasket.getCount());
         }

         basketRepo.save(basketToChange);

         return basketToChange;
      }

      public void basketDelete(Long basketId){
       basketRepo.deleteById(basketId);
      }

      public void changeBasketStatusByUserId(Long user_id, Long check_id){
         basketRepo.changeActive(user_id, check_id);
      }
}
