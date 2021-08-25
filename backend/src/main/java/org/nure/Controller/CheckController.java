package org.nure.Controller;

import org.nure.Repositories.BasketRepo;
import org.nure.models.Check;
import org.nure.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/check")
public class CheckController {

   @Autowired
   private CheckService checkService;

   @Autowired
   private BasketRepo basketRepo;

   @GetMapping("/all")
   public ResponseEntity<List<Check>> allChecks(){
      return ResponseEntity.ok(checkService.getAllChecks());
   }

   @GetMapping("/{id}")
   public ResponseEntity<Check> getOne(@PathVariable Long id){
      return ResponseEntity.ok(checkService.getOneCheck(id));
   }

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Check> saveCheck(@RequestBody Check check){

      if(check == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      checkService.saveCheck(check);

      return ResponseEntity.ok(check);
   }

   @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Check> updateCheck(@RequestBody Check check){
      checkService.updateCheck(check);
      return ResponseEntity.ok(check);

   }

   @DeleteMapping("/{id}")
      public ResponseEntity<Check> deleteOne(@PathVariable Long id) {
         checkService.deleteCheckById(id);
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
