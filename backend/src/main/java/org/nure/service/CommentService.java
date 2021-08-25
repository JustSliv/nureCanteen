package org.nure.service;

import org.nure.Repositories.CommentRepo;
import org.nure.models.Comment;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentService {

   private final CommentRepo commentRepo;

   public CommentService(CommentRepo commentRepo) {
      this.commentRepo = commentRepo;
   }

   public Comment findOneById(Long commentId){
      return commentRepo.findById(commentId)
         .orElseThrow(() -> new ResourceNotFoundException("Comment with comment Id " + commentId + " does not exist"));
   }

   public List<Comment> getAllComments(){
      return commentRepo.findAll();
   }

   public void updateComment(Comment comment, Long commentId){
      Comment commentToChange = this.findOneById(commentId);

      if(comment.getMsg() != null) {
         commentToChange.setMsg(comment.getMsg());
      }

      commentRepo.save(commentToChange);
   }
   public Comment addComment(Comment comment){

      LocalDateTime currTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss");

      commentRepo.addComment(currTime.format(formatter), comment.getMsg(), comment.getProduct_id().getProduct_id(), comment.getUser().getid());

      comment.setDate(currTime.format(formatter));

      commentRepo.save(comment);

      return comment;

   }

   public void saveComment(Comment comment){
      commentRepo.save(comment);
   }

   public void deleteComment(Long commentId){
      commentRepo.deleteById(commentId);
   }

}
