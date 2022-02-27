package com.softtech.softtechspringboot.app.reflection;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
class Customer {

 public Long id;
 private String surname;
 private String name;

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getSurname() {
  return surname;
 }

 public void setSurname(String surname) {
  this.surname = surname;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 @Override
 public String toString() {
  return "Customer{" +
          "id=" + id +
          ", surname='" + surname + '\'' +
          ", name='" + name + '\'' +
          '}';
 }
}
