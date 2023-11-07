package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Comment;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ImgComment;
import com.ecommerce.library.service.CommentService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.RecipeService;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ImageUpload imageUpload;



    @PostMapping("/add-comment")
    public String addComment(MultipartHttpServletRequest request,@RequestParam("recipeId") Long recipeId, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Comment comment = new Comment();
        List<ImgComment> imgComments = new ArrayList<>();
//        ImageUpload imageUpload = new ImageUpload();


        //get data from form
        String content = request.getParameter("comment");
        List<MultipartFile> commentImages = request.getFiles("img-comment");

        comment.setContent(content);
        for (MultipartFile image : commentImages){
            if(!image.isEmpty()){
                ImgComment imgComment = new ImgComment();
                String imageName = "api/images/image-comment/" + imageUpload.uploadImage(image, "image-comment");
                imgComment.setImgUrl(imageName);
                imgComment.setComment(comment);
                imgComments.add(imgComment);
            }
        }

            comment.setImages(imgComments);
            comment.setRecipe(recipeService.getRecipeById(recipeId));
            comment.setCustomer(customerService.findByUsername(principal.getName()));
            commentService.save(comment);
        System.out.println("reid: "+recipeId);

//        System.out.println("add comment success!!");
        return "redirect:/find-recipe/"+recipeId;
//        return "home";
    }
//    @GetMapping("/add-comment")
//    String addComment_(){
//        return "redirect:/find-recipe/{}";
//    }


}
