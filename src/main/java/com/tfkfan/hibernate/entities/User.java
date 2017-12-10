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
 
     @Column(name = "name")
     protected String name;
 
     @Column(name = "password")
     protected String password;
 
     public User() {
 
     }
 
     public User(String name, String password) {
         setName(name);
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
         return name;
     }
 
     public void setName(String name) {
         this.name = name;
     }
     
   
 }
