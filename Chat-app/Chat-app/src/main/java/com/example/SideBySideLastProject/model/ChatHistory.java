package com.example.SideBySideLastProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_chat_history")

public class ChatHistory {
    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chat_id;
    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User to;
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User from;
    @Column(name = "message")
    private String message;

    @Column(name = "created_date")
    private Timestamp created_date;

    @Column(name = "updated_date")
    private Timestamp updated_date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_status_id")
    private Status status_id;

}
