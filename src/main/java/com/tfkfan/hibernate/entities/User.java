package com.tfkfan.hibernate.entities;
 
 import java.io.Serializable;
 import java.util.Set;
 import javax.persistence.*;
 
 @Entity
 @Table(name = "users")
 public class User implements Serializable {
     private static final long serialVersionUID = 1L;
 
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id")
     protected int id;
 
     @Column(name = "username")
     protected String username;
 
     @Column(name = "password")
     protected String password;
 
     public User() {
 
     }
 
     public User(String username, String password) {
         setUsername(username);
         setPassword(password);
     }
 
     public String getPassword() {
         return password;
     }
 
     public void setPassword(String password) {
         this.password = password;
     }
 
     public int getId() {
         return id;
     }
 
     public void setId(int id) {
         this.id = id;
     }
 
     public String getName() {
         return username;
     }
 
     public void setUsername(String name) {
         this.username = name;
     }
     
   
 }
