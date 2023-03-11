package com.example.SideBySideLastProject.model;

import org.json.JSONObject;

import java.util.List;

public interface IChatHistoryInterface {
    public JSONObject validateMessage(JSONObject jsonObject);
    public List<String> getReceivedMessages(Integer user_id);
    public List<String> getsentMessages(Integer user_id);
    public JSONObject getConversation(Integer user1, Integer user2);

    public Integer deleteConversation(Integer userId1, Integer userId2);
}
