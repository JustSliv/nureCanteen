package org.nure.Controller;

import org.nure.Repositories.ProductRepo;
import org.nure.models.Comment;
import org.nure.models.Product;
import org.nure.service.ProductService;
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
   private ProductService productService;


   @GetMapping("/all")
   public ResponseEntity<List<Product>> getAllProducts(){

   return ResponseEntity.ok(productService.getAllProducts());
   }

   @GetMapping("/{id}")
   public ResponseEntity<Product> getOne(@PathVariable Long productId){

      return ResponseEntity.ok(productService.getProductById(productId));
   }

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Product> addOneProduct(@RequestBody Product product){

      if(product == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      productService.save(product);

      return ResponseEntity.ok(product);
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Product> putProduct(@RequestBody Product updatedProduct, @PathVariable Long productId){
      productService.updateProduct(productId, updatedProduct);
      return ResponseEntity.ok(updatedProduct);
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Product> deleteOneProduct(@PathVariable Long id) {
      productService.deleteProductById(id);

      return new ResponseEntity<>(HttpStatus.OK);
   }

   @GetMapping("/comments/{id}")
   public ResponseEntity<List<Comment>> getComments(@PathVariable Long productId){
      List<Comment> commentsForProduct = productService.findCommentsForProduct(productId);
      return ResponseEntity.ok(commentsForProduct);
   }
}
