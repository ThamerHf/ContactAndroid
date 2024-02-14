package com.uca.contact.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.uca.contact.model.Contact;

import java.util.List;

@Dao
public interface ContactRepository {

    @Insert
    Contact insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contact order by contact.nom asc")
    List<Contact> getAll();

    @Query("Select * FROM contact where contact.nom LIKE :nom order by contact.nom asc")
    List<Contact> getOrderList(String nom);
}
