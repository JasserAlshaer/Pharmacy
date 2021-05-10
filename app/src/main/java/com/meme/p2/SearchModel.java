package com.meme.p2;

public class SearchModel {
    public int ph_id , med_id , quantity;
    public String ph_name , ph_address , med_name;

    public SearchModel(int ph_id, int med_id, int quantity, String ph_name, String ph_address, String med_name) {
        this.ph_id = ph_id;
        this.med_id = med_id;
        this.quantity = quantity;
        this.ph_name = ph_name;
        this.ph_address = ph_address;
        this.med_name = med_name;
    }
    public SearchModel(){}

    public int getPh_id() {
        return ph_id;
    }

    public void setPh_id(int ph_id) {
        this.ph_id = ph_id;
    }

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPh_name() {
        return ph_name;
    }

    public void setPh_name(String ph_name) {
        this.ph_name = ph_name;
    }

    public String getPh_address() {
        return ph_address;
    }

    public void setPh_address(String ph_address) {
        this.ph_address = ph_address;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }
}
