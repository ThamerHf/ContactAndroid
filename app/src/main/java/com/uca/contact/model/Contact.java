package com.uca.contact.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;
import java.util.Objects;

@Entity
public class Contact {

    @PrimaryKey
    private String tel;

    private String nom;

    private String addresse;

    private Blob photo;

}
