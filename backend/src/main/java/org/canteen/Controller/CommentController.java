package org.canteen.Controller;

import org.apache.tomcat.jni.Local;
import org.canteen.Repositories.CommentRepo;
import org.canteen.models.Comment;
import org.canteen.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

   @Autowired
   private CommentRepo commentRepo;

   @GetMapping("/{id}")
   @ResponseBody
   public ResponseEntity<Comment> getOne(@PathVariable Long id){

      return ResponseEntity.ok(commentRepo.findById(id).get());
   }

   @GetMapping("/all")
   public ResponseEntity<List<Comment>> getAll(){

      return ResponseEntity.ok(commentRepo.findAll());
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Comment> putProduct(@RequestBody Comment newComment, @PathVariable Long id){
      HttpHeaders headers = new HttpHeaders();

      Comment commentToChange = commentRepo.findById(id).get();

      if(newComment.getMsg() != null) {
         commentToChange.setMsg(newComment.getMsg());
      }

      commentRepo.save(commentToChange);
      return new ResponseEntity<>(commentToChange, headers, HttpStatus.OK);
   }

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Comment> addOne(@RequestBody Comment comment){

      LocalDateTime currTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss");

      commentRepo.addComment(currTime.format(formatter), comment.getMsg(), comment.getProduct_id().getProduct_id(), comment.getUser().getid());
      
      comment.setDate(currTime.format(formatter));
      return ResponseEntity.ok(comment);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Product> deleteOne(@PathVariable Long id) {
      commentRepo.deleteById(id);

      return new ResponseEntity<>(HttpStatus.OK);
   }
}
