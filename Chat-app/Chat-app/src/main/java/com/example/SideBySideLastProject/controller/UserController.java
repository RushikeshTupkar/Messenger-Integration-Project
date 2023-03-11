package com.example.SideBySideLastProject.controller;

import com.example.SideBySideLastProject.model.User;
import com.example.SideBySideLastProject.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/add_user")
    public ResponseEntity<String> addUser(@RequestBody String user){
        JSONObject jsonObject = new JSONObject(user);
        JSONObject validated = userService.validateUser(jsonObject);
        if(validated.keySet().isEmpty()){
            User newUser = userService.setUSer(jsonObject);
            Integer user_id =  userService.addUser(newUser);
            return new ResponseEntity<>(user_id.toString(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(validated.toString(), HttpStatus.CREATED);
    }

     @GetMapping("/get_all_users")
    public String getAll(@Nullable @RequestParam("user_id") String user_id){
        if(user_id==null){
            return userService.getALl().toString();
        }
        return userService.getUSer(Integer.valueOf(user_id)).toString();
     }

     @DeleteMapping("/delete_user_by_id/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer user_id){
         String response = userService.deleteUser(user_id);
        if(response!=null){
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
         return new ResponseEntity<>("User deleted with user_id - "+user_id, HttpStatus.OK);
     }

     @PostMapping("/log_in")
    public ResponseEntity<String> logIn(@RequestBody String user){
        JSONObject jsonObject = new JSONObject(user);
         JSONObject jsonObject1 = userService.logIn(jsonObject);
//         if(jsonObject1.has("errorMessage")){
//             return new ResponseEntity<>(jsonObject1.toString(),HttpStatus.BAD_REQUEST);
//         }
         return new ResponseEntity<>(jsonObject1.toString(),HttpStatus.OK);
     }

     @PutMapping("/update_user")
    public ResponseEntity<String> updateUser(@RequestBody String user){
         JSONObject jsonObject = new JSONObject(user);
         JSONObject validated = userService.validateUser(jsonObject);
         if(validated.keySet().isEmpty()){
             userService.updateUser(jsonObject);
         }
         return new ResponseEntity<>(validated.toString(), HttpStatus.CREATED);
     }

}
