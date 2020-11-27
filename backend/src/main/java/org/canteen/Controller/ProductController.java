package org.canteen.Controller;

import org.canteen.Repositories.CommentRepo;
import org.canteen.Repositories.ProductRepo;
import org.canteen.models.Comment;
import org.canteen.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

   @Autowired
   private ProductRepo productRepo;

   @Autowired
   private CommentRepo commentRepo;

   @GetMapping("/all")
   public ResponseEntity<List<Product>> allProducts(){

   return ResponseEntity.ok(productRepo.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<Product> getOne(@PathVariable Long id){

      return ResponseEntity.ok(productRepo.findById(id).get());
   }

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Product> saveProduct(@RequestBody Product product){
      HttpHeaders headers = new HttpHeaders();

      if(product == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      productRepo.save(product);

      return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Product> putProduct(@RequestBody Product newProd, @PathVariable Long id){
      HttpHeaders headers = new HttpHeaders();

      Product prodToChange = productRepo.findById(id).get();

      if(newProd.getName() != null) {
         prodToChange.setName(newProd.getName());
      }
      if(Long.valueOf(newProd.getAvailable_count()) != null) {
         prodToChange.setAvailable_count(newProd.getAvailable_count());
      }
      if(newProd.getCategory() != null) {
         prodToChange.setCategory(newProd.getCategory());
      }
      if(newProd.getDescription() != null) {
         prodToChange.setDescription(newProd.getDescription());
      }
      if(newProd.getImage() != null) {
         prodToChange.setImage(newProd.getImage());
      }
      if(Long.valueOf(newProd.getPrice()) != null) {
         prodToChange.setPrice(newProd.getPrice());
      }
      if(Long.valueOf(newProd.getTotal_count()) != null) {
         prodToChange.setTotal_count(newProd.getTotal_count());
      }

      productRepo.save(prodToChange);
      return new ResponseEntity<>(prodToChange, headers, HttpStatus.OK);
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Product> deleteOne(@PathVariable Long id) {
      productRepo.deleteById(id);

      return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping("/comments/{id}")
   public ResponseEntity<List<Comment>> getComments(@PathVariable Long id){

      return ResponseEntity.ok(commentRepo.findCommentsForProduct(id));
   }
}
