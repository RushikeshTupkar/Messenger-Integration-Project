package com.example.SideBySideLastProject.repository;

import com.example.SideBySideLastProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface IUserRepository extends JpaRepository<User,Integer> {

    @Query(value = "Select * from tbl_user where user_name = :userName And status_id_status_id = 1", nativeQuery = true)
    public List<User> FindByUserName(String userName);

    @Query(value = "Select * from tbl_user where user_id = :user_id And status_id_status_id = 1", nativeQuery = true)
    public User FindByUserID(Integer user_id);


    @Query(value = "Select * from tbl_user where status_id_status_id = 1", nativeQuery = true)
    public List<User> getAllUsers();


    //database independent query
    @Modifying
    @Transactional
    @Query("update User set first_name = :first_name,last_name =:last_name,age=:age, email=:email, gender=:gender, " +
            "phone_number=:phone_number,updated_time = :updated_time  where user_id=:user_id")
    public Integer updateByFName(String first_name, String last_name, Integer user_id,Integer age, String email, String gender,
                                 String  phone_number, Timestamp updated_time);

    @Modifying
    @Transactional
    @Query("update User set status_id_status_id = 2 where user_id=:user_id")
    public void deleteById(Integer user_id);



    @Modifying
    @Transactional
    @Query(value = "update tbl_user set first_name = :first_name,last_name =:last_name,age=:age, email=:email, gender=:gender, " +
            "phone_number=:phone_number,updated_time = :updated_time  where user_id=:user_id",countQuery = "SELECT count(*) from tbl_user",nativeQuery = true)
    public Integer updateUserData(String first_name, String last_name, Integer user_id,Integer age, String email, String gender,
                                 String  phone_number, Timestamp updated_time);



}
