package org.canteen.Controller;

import org.canteen.Repositories.CommentRepo;
import org.canteen.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

   @PostMapping("/")
   public ResponseEntity<Comment> addOne(@RequestBody Comment comment){
      return ResponseEntity.ok(commentRepo.save(comment));
   }
}
