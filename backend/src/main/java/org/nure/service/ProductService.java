package org.nure.service;

import org.nure.Repositories.ProductRepo;
import org.nure.models.Comment;
import org.nure.models.Product;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

   private final ProductRepo productRepo;


   public ProductService(ProductRepo productRepo) {
      this.productRepo = productRepo;
   }

   public List<Product> getAllProducts(){
      return productRepo.findAll();
   }

   public Product getProductById(Long productId){
      return productRepo.findById(productId)
         .orElseThrow(() -> new ResourceNotFoundException("Product with product Id " + productId + " does not exist"));
   }

   public void save(Product product){
      productRepo.save(product);
   }

   public Product updateProduct(Long productId, Product updatedProduct){

      Product productToChange = this.getProductById(productId);

      if(updatedProduct.getName() != null) {
         productToChange.setName(updatedProduct.getName());
      }
      if((long) updatedProduct.getAvailable_count() != 0) {
         productToChange.setAvailable_count(updatedProduct.getAvailable_count());
      }
      if(updatedProduct.getCategory() != null) {
         productToChange.setCategory(updatedProduct.getCategory());
      }
      if(updatedProduct.getDescription() != null) {
         productToChange.setDescription(updatedProduct.getDescription());
      }
      if(updatedProduct.getImage() != null) {
         productToChange.setImage(updatedProduct.getImage());
      }
      if((long) updatedProduct.getPrice() != 0) {
         productToChange.setPrice(updatedProduct.getPrice());
      }
      if((long) updatedProduct.getTotal_count() != 0) {
         productToChange.setTotal_count(updatedProduct.getTotal_count());
      }
      if((long) updatedProduct.getCalories() != 0) {
         productToChange.setCalories(updatedProduct.getCalories());
      }
      if((long) updatedProduct.getWeight() != 0) {
         productToChange.setWeight(updatedProduct.getWeight());
      }

      productRepo.save(productToChange);
      return productToChange;
   }

   public void deleteProductById(Long productId){
      productRepo.deleteById(productId);
   }

   public List<Comment> findCommentsForProduct(Long productId){
      List<Comment> commentsForProduct = productRepo.findCommentsForProduct(productId);
      return commentsForProduct;
   }
}
