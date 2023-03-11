package com.example.SideBySideLastProject.model;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IUserInterface {
    public User setUSer(JSONObject jsonObject);
    public int addUser(User newUser);
    public JSONObject validateUser(JSONObject jsonObject);
    public JSONArray getALl();
    public JSONObject setResponse(User u1);
    public JSONArray getUSer(Integer userId);
    public String  deleteUser(Integer user_id);
    public JSONObject logIn(JSONObject jsonObject);
    public void updateUser(JSONObject jsonObject);
}
