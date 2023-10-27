package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.model.Reaction;
import com.ecommerce.library.service.*;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
//import com.nimbusds.jose.shaded.gson.JsonObject;
//import com.nimbusds.jose.shaded.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/measurements")
    public ResponseEntity<List<String>> getMeasurements() {
        List<Measurement> measurementsObject = measurementService.findAllByActivated();
        List<String> measurements = new ArrayList<>();
        for (Measurement measurement : measurementsObject) {
            measurements.add(measurement.getName());
        }
        System.out.println("danhsach:" + measurements);

        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/products")
    public JSONArray getProducts() {
        JSONArray products = productService.getAllProductsJson();
        return products;
    }

    @GetMapping("/suggest-products")
    public JSONArray getSuggestProducts(@RequestParam String query) {
        JSONArray products = productService.getSuggestProducts(query);
        return products;
    }

    @GetMapping("/suggest-recipes")
    public JSONArray getSuggestRecipes(@RequestParam String query) {
        JSONArray recipes = recipeService.getSuggestRecipes(query);
        return recipes;
    }

    @GetMapping("/reaction")
    public JSONArray getReactions(@RequestParam Long commentId) {
        JSONArray reactions = reactionService.getReactionsByCommentId(commentId);
        return reactions;
    }

    @PostMapping("/add-reaction")
    public String addReaction(@RequestBody String requestBody){
        System.out.println("reaction");
        JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
        Long commentId = jsonObject.get("commentId").getAsLong();
        Long customerId = jsonObject.get("customerId").getAsLong();
        String type = jsonObject.get("type").getAsString();
        System.out.println(commentId + customerId + type);
        try {
            Reaction reaction = new Reaction();
            //check all customer
            Set<Customer> customerCurrent = reaction.getCustomers();
            if (customerCurrent == null) {
                customerCurrent = new HashSet<>();
            }
            Set<Customer> customer = new HashSet<>();
            customer.addAll(customerCurrent);
            customer.add(customerService.findById(customerId));
            //check if user like or dislike
            System.out.println(reactionService.checkReaction(commentId, customerId, type));
            if (reactionService.checkReaction(commentId, customerId, type)) {
                //if user like or dislike, change reaction
                System.out.println(reactionService.findReactionsByCommentIdAndCustomerId(commentId,customerId).getType()+"<==>"+ type);
                if(reactionService.findReactionsByCommentIdAndCustomerId(commentId,customerId).getType().equals(type)){

                    //if user like or dislike same reaction, delete reaction
                    reactionService.deleteByCommentIdAndCustomerId(commentId, customerId);
                }else {
                    reactionService.changeReaction(commentId, customerId, type);
                }
            }else {
                //user not like or dislike, add reaction
                reaction.setComment(commentService.findById(commentId));
                customer.add(customerService.findById(customerId));
                reaction.setCustomers(customer);
                reaction.setType(type);
                reactionService.save(reaction);
            }
//            reaction.setComment(commentService.findById(commentId));
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }
    //show reaction in each comment
    @GetMapping("/show-reaction")
    public JSONArray showReaction(@RequestParam Long commentId,@RequestParam String type){
        JSONArray show_reaction = new JSONArray();
        int count = reactionService.countReactionsByComment_IdAndType(commentId,type);
        show_reaction.add(count);
        return show_reaction;
    }

    @GetMapping("/check-reaction")
    public boolean checkReaction(@RequestParam Long commentId,@RequestParam Long customerId,@RequestParam String type){
        boolean check = reactionService.countReactionsByComment_IdAndCustomer_IdAndType(commentId,customerId,type) > 0;
        return check;
    }


}







