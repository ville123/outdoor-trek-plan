package com.trekplanner.app.model;

/**
 * Created by Shakur on 24.3.2018.
 */

public class TrekItem  {

    private Long id;
    private Long itemId;
    private Item item;
    private Long trekId;
    private int count;
    private String notes;
    private Double totalWeight;
    private String status;
    private boolean wasUsed;

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }

    public void setTrekId(Long trekId){
        this.trekId = trekId;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public void setTotalWeight(double totalWeight){
        this.totalWeight = totalWeight;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setWasUsed(boolean wasUsed){
        this.wasUsed = wasUsed;
    }

    public Long getId() {
        return id;
    }

    public Long getItemId(){
        return itemId;
    }

    public Long getTrekId(){
        return trekId;
    }

    public int getCount(){
        return count;
    }

    public String getNotes(){
        return notes;
    }

    public Double getTotalWeight(){
        return totalWeight;
    }

    public String getStatus(){
        return status;
    }

    public boolean getWasUsed(){
        return wasUsed;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}