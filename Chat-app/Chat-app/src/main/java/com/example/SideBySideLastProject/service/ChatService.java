package com.example.SideBySideLastProject.service;

import com.example.SideBySideLastProject.model.ChatHistory;
import com.example.SideBySideLastProject.model.IChatHistoryInterface;
import com.example.SideBySideLastProject.model.User;
import com.example.SideBySideLastProject.repository.IChatRepository;
import com.example.SideBySideLastProject.repository.IStatusRepository;
import com.example.SideBySideLastProject.repository.IUserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ChatService implements IChatHistoryInterface {
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IChatRepository iChatRepository;
    @Autowired
    private IStatusRepository iStatusRepository;
    @Override
    public JSONObject validateMessage(JSONObject jsonObject) {
        JSONObject error = new JSONObject();
        if(!jsonObject.has("sender")){
            error.put("errorMessage","sender's id is mandatory");
        }else if(iUserRepository.FindByUserID(jsonObject.getInt("sender"))==null){
            error.put("errorMessage","sender's id is wrong");
        }
        if(!jsonObject.has("receiver")){
            error.put("errorMessage","receiver's id is mandatory");
        }else if(iUserRepository.FindByUserID(jsonObject.getInt("receiver"))==null){
            error.put("errorMessage","No receiver is present with id - "+jsonObject.getInt("receiver"));
            return error;
        }

        if(!jsonObject.has("message")){
            error.put("errorMessage","message is mandatory");
            return error;
        } else if(jsonObject.getString("message").length()==0){
            error.put("errorMessage","message cannot be empty");
            return error;
        }

        ChatHistory newChat = new ChatHistory();
        newChat.setFrom(iUserRepository.findById(jsonObject.getInt("sender")).get());
        newChat.setTo(iUserRepository.findById(jsonObject.getInt("receiver")).get());
        newChat.setMessage(jsonObject.getString("message"));
        newChat.setStatus_id(iStatusRepository.findById(1).get());
        newChat.setCreated_date(new Timestamp(System.currentTimeMillis()));
        iChatRepository.save(newChat);
        error.put("message","Validated and send");
        return error;
    }

    @Override
    public List<String> getReceivedMessages(Integer user_id) {
//      List<ChatHistory>receivedMessages =  iChatRepository.getReceivedMessages(user_id);
        List<String>receivedMessages1 = iChatRepository.receivedMessages(user_id);
        receivedMessages1.add(0,"Received messages");

        return receivedMessages1;
    }
    @Override
    public List<String> getsentMessages(Integer user_id) {
        List<String> sentMessages = iChatRepository.sentMessages(user_id);
       sentMessages.add(0,"Sent messages");
        return sentMessages;
    }

    @Override
    public JSONObject getConversation(Integer user1, Integer user2) {
        JSONObject response = new JSONObject();
        JSONArray conversations = new JSONArray();
        List<ChatHistory> chatList = iChatRepository.getConversation(user1, user2);

        for (ChatHistory chat : chatList) {
            JSONObject messageObj = new JSONObject();
            messageObj.put("chatId" , chat.getChat_id());
            messageObj.put("timestamp" , chat.getCreated_date());
            messageObj.put("senderName" , chat.getFrom().getFirst_name());
            messageObj.put("message", chat.getMessage());
            conversations.put(messageObj);
        }
        response.put("conversation", conversations);
        return response;
    }

    @Override
    public Integer deleteConversation(Integer userId1, Integer userId2) {
        return iChatRepository.deleteConversation(userId1,userId2);
    }
 public List<String> getConversation1(Integer userId1, Integer userId2){
       return  iChatRepository.getConversation1(userId1,userId2);
 }

}
