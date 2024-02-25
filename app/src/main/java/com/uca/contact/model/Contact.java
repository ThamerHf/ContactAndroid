package com.uca.contact.model;

import java.util.Objects;
import java.util.UUID;

public class Contact {

    private String contactId;

    private String tel;

    private String name;

    private String address;

    private byte[] photo;

    public Contact() {
    }

    public Contact(String tel, String name, String address) {
        this.tel = tel;
        this.name = name;
        this.address = address;
        this.photo = photo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(contactId, contact.contactId) && Objects.equals(tel, contact.tel) && Objects.equals(name, contact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, tel, name);
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
