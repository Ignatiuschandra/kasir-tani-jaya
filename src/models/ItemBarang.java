/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Chandra
 */
public class ItemBarang {
    private String id;
    private String nama;

    public ItemBarang(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    
    @Override
    public String toString()
    {
        return nama;
    }
    
    public boolean equals(ItemBarang item)
    {
        System.out.println(item.nama);
        System.out.println(this.nama);
        if(item.id.equalsIgnoreCase(this.id) 
                && item.nama.equalsIgnoreCase(this.nama)){
            return true;
        }
        
        return false;
    }
    
}
