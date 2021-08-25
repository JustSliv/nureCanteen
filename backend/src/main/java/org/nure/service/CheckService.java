package org.nure.service;

import org.nure.Repositories.CheckRepo;
import org.nure.models.Check;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckService {

   private final CheckRepo checkRepo;

   public CheckService(CheckRepo checkRepo) {
      this.checkRepo = checkRepo;
   }

   public List<Check> getAllChecks(){
      return checkRepo.findAll();
   }

   public Check getOneCheck(Long checkId){
      return checkRepo.findById(checkId)
         .orElseThrow(() -> new ResourceNotFoundException("Check with checkId " + checkId));
   }

   public void saveCheck(Check check){
      checkRepo.save(check);
   }

   public Check updateCheck(Check check){
      Check checkToUpdate = checkRepo.findById(check.getCheck_id())
         .orElseThrow(() -> new ResourceNotFoundException("Check with checkId " + check.getCheck_id()));

      if(!check.getCanteen().equals(checkToUpdate.getCanteen()) && check.getCanteen() != null){
         checkToUpdate.setCanteen(check.getCanteen());
      }
      if(check.getSum() != checkToUpdate.getSum() && check != null){
         checkToUpdate.setSum(check.getSum());
      }
      if(check.getUser_id() != checkToUpdate.getUser_id() && check != null){
         checkToUpdate.setUser_id(check.getUser_id());
      }
      checkRepo.save(checkToUpdate);

      return checkRepo.save(check);
   }

   public void deleteCheckById(Long checkId){
      checkRepo.deleteById(checkId);
   }
}
