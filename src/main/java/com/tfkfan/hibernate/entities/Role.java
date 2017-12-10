package com.tfkfan.hibernate.entities;
 
 import java.util.Set;
 import javax.persistence.*;
 
 @Entity
 @Table(name = "roles")
 public class Role {
     private Long id;
     private String role;
 
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     public Long getId() {
         return id;
     }
 
     public void setId(Long id) {
         this.id = id;
     }
 
     public String getRole() {
         return role;
     }
 
     public void setRole(String role) {
         this.role = role;
     }

}
