package org.canteen.Controller;

import org.canteen.Repositories.ProductRepo;
import org.canteen.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

   @Autowired
   private ProductRepo productRepo;

   @GetMapping("/all")
   public ResponseEntity<List<Product>> allProducts(){

   return ResponseEntity.ok(productRepo.findAll());
   }
}
