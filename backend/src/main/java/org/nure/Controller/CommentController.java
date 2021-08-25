package org.nure.Controller;

import org.nure.models.Comment;
import org.nure.models.Product;
import org.nure.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

   @Autowired
   private CommentService commentService;

   @GetMapping("/{id}")
   @ResponseBody
   public ResponseEntity<Comment> getOne(@PathVariable Long id){

      return ResponseEntity.ok(commentService.findOneById(id));
   }

   @GetMapping("/all")
   public ResponseEntity<List<Comment>> getAll(){

      return ResponseEntity.ok(commentService.getAllComments());
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<Comment> updateComment(@RequestBody Comment newComment, @PathVariable Long id){
      commentService.updateComment(newComment, id);
      return ResponseEntity.ok(newComment);
   }

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Comment> addComment(@RequestBody Comment comment){

      commentService.addComment(comment);
      return ResponseEntity.ok(comment);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Product> deleteOne(@PathVariable Long commentId) {
      commentService.deleteComment(commentId);
      return new ResponseEntity<>(HttpStatus.OK);
   }
}
