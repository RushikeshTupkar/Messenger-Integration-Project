package com.example.SideBySideLastProject.repository;

import com.example.SideBySideLastProject.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IChatRepository extends JpaRepository<ChatHistory,Integer> {


    @Query(value = "Select Concat(tbl_chat_history.from_user_id,'-',tbl_user.first_name,'->  ',tbl_chat_history.message,'  [ ',tbl_chat_history.created_date,' ]') from tbl_user Inner join tbl_chat_history on tbl_user.user_id=tbl_chat_history.from_user_id " +
            "where status_status_id = 1 and to_user_id=:to_user_id",countQuery = "Select count(*) from tbl_chat_history",
            nativeQuery = true)
     public List<String> receivedMessages(Integer to_user_id);


    @Query(value = "Select Concat(tbl_chat_history.to_user_id,'-',tbl_user.first_name,'->  ',tbl_chat_history.message,'  [ ',tbl_chat_history.created_date,' ]') from tbl_user Inner join tbl_chat_history on tbl_user.user_id=tbl_chat_history.to_user_id " +
            "where status_status_id = 1 and from_user_id=:from_user_id order by tbl_chat_history.created_date",countQuery = "Select count(*) from tbl_chat_history",
            nativeQuery = true)
    public List<String> sentMessages(Integer from_user_id);

    @Query(value = "select * from tbl_chat_history where (from_user_id = :user1 and to_user_id = :user2 and status_status_id  = 1)" +
            "or (from_user_id = :user2 and to_user_id = :user1) and status_status_id  = 1 order by created_date",
            nativeQuery = true)
    List<ChatHistory> getConversation(Integer user1, Integer user2);

    @Query(value = "select message from tbl_chat_history where (from_user_id = :user1 and to_user_id = :user2 and status_status_id  = 1)" +
            "or (from_user_id = :user2 and to_user_id = :user1) and status_status_id  = 1 order by created_date",
            nativeQuery = true)
    List<String> getConversation1(Integer user1, Integer user2);

    @Modifying
    @Transactional
    @Query(value = "update tbl_chat_history set status_status_id  = 2 where (from_user_id = :user1 and to_user_id = :user2)" +
            "or (from_user_id = :user2 and to_user_id = :user1)",countQuery = "Select count(*) from tbl_chat_history",
            nativeQuery = true)
    Integer deleteConversation(Integer user1, Integer user2);
}