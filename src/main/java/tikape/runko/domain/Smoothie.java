/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author jpssilve
 */
public class Smoothie {
    
    private Integer id;
    private String nimi;
    
    public Smoothie(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    
    public Smoothie() {
    }
    
     public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
}
