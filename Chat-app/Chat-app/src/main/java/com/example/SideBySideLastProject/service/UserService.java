package com.example.SideBySideLastProject.service;

import com.example.SideBySideLastProject.commonUtils.CommonUtils;
import com.example.SideBySideLastProject.model.IUserInterface;
import com.example.SideBySideLastProject.model.User;
import com.example.SideBySideLastProject.repository.IStatusRepository;
import com.example.SideBySideLastProject.repository.IUserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService implements IUserInterface {
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IStatusRepository iStatusRepository;
    @Override
    public User setUSer(JSONObject jsonObject) {
    User user = new User();
    user.setAge(jsonObject.getInt("age"));
    user.setEmail(jsonObject.getString("email"));
    user.setFirst_name(jsonObject.getString("first_name"));
    user.setLast_name(jsonObject.getString("last_name"));
    user.setGender(jsonObject.getString("gender"));
    user.setUser_name(jsonObject.getString("user_name"));
    user.setPassword(jsonObject.getString("password"));
    user.setPhone_number(jsonObject.getString("phone_number"));
    user.setCreated_time(new Timestamp(System.currentTimeMillis()));
    user.setUpdated_time(null);
    user.setStatus_id(iStatusRepository.findById(1).get());
    return user;
    }
@Override
    public int addUser(User newUser) {
        User savedUser =  iUserRepository.save(newUser);
        return savedUser.getUser_id();
    }
@Override
    public JSONObject validateUser(JSONObject jsonObject) {
        JSONObject errorlist = new JSONObject();
        if(!jsonObject.has("isUpdate")){
            if(jsonObject.has("user_name")){
                String user_name = jsonObject.getString("user_name");
                //JPA query to check redundance username
                List<User>userList = iUserRepository.FindByUserName(user_name);
                if(!userList.isEmpty()){
                    errorlist.put("user_name","User already exists with user_name");
                }
            }else{
                errorlist.put("user_name","Missing user_name");
            }
        }

        if(jsonObject.has("password")){
            if(!CommonUtils.isVAlidPassword(jsonObject.getString("password"))){
                errorlist.put("user_name"," Wrong password");
            }
        }else{
            errorlist.put("password","Missing password");
        }

        if(jsonObject.has("first_name")){

        }else{
            errorlist.put("first_name","Missing first_name");
        }

        if(jsonObject.has("phone_number")){
            if(!CommonUtils.isValidMobileNo(jsonObject.getString("phone_number"))){
                errorlist.put("phone_number","Invalid phone_number");
            }
        }else{
            errorlist.put("first_name","Missing phone_number");
        }


        if(jsonObject.has("email")){
          if(!CommonUtils.isValidEmail(jsonObject.getString("email"))){
           errorlist.put("email","Invalid email");
       }
        }else{
            errorlist.put("first_name","Missing email");
        }

       return errorlist;


    }
@Override
    public JSONArray getALl() {
        List<User> userList = iUserRepository.getAllUsers();
        JSONArray jsonArray = new JSONArray();
        for(User u1 : userList){
            jsonArray.put(setResponse(u1));
        }
        return jsonArray;
    }
@Override
    public JSONObject setResponse(User u1) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id",u1.getUser_id());
        jsonObject.put("user_name",u1.getUser_name());
        jsonObject.put("first_name",u1.getFirst_name());
        jsonObject.put("last_name",u1.getLast_name());
        jsonObject.put("email",u1.getEmail());
        jsonObject.put("phone_number",u1.getPhone_number());
        jsonObject.put("gender",u1.getGender());
        jsonObject.put("created_date",u1.getCreated_time());
        return jsonObject;
    }
@Override
    public JSONArray getUSer(Integer userId) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = setResponse(iUserRepository.FindByUserID(userId));
        return jsonArray.put(jsonObject);

    }
@Override
    public String  deleteUser(Integer user_id) {
        if(!iUserRepository.findById(user_id).isPresent()){
            return "No user found with user_id - "+ user_id;
        }
        iUserRepository.deleteById(user_id);
        return null;
    }
@Override
    public JSONObject logIn(JSONObject jsonObject) {
        JSONObject jsonObject1 = new JSONObject();
        if(!jsonObject.has("user_name") || jsonObject.getString("user_name")==null ){
            return jsonObject1.put("errorMessage","user_name is mandatory");
        }
        if(jsonObject.getString("password").length()==0){
            return jsonObject1.put("errorMessage","password is mandatory");
        }

        List<User> userList = iUserRepository.FindByUserName(jsonObject.getString("user_name"));
        if(userList.isEmpty()){
            jsonObject1.put("errorMessage","-wrong user_name");
            return jsonObject1;
        }
        else if(!userList.get(0).getPassword().equals(jsonObject.get("password"))){
            jsonObject1.put("errorMessage","-wrong password");
            return jsonObject1;
        }
        User u1 = userList.get(0);
         return setResponse(u1).put("logging status","successful");
    }
@Override
    public void updateUser(JSONObject jsonObject) {
        Integer user_id = jsonObject.getInt("user_id");
        String first_name = jsonObject.getString("first_name");
        String last_name  = jsonObject.getString("last_name");
        Integer age = jsonObject.getInt("age");
        String email = jsonObject.getString("email");
        String gender = jsonObject.getString("gender");
        String phone_number = jsonObject.getString("phone_number");
        Timestamp updated_time = new Timestamp(System.currentTimeMillis());

       iUserRepository.updateByFName(first_name,last_name,user_id,age,email,gender,phone_number,updated_time);

    }
}
