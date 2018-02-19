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
public class SmoothieRaakaAine {
    
    private Integer id;
    private Smoothie smoothie;
    private RaakaAine raaka_aine;
    private Integer jarjestys;
    private String maara;
    private String ohje;
    
    public SmoothieRaakaAine(Integer id, Smoothie smoothie, RaakaAine raaka_aine, Integer jarjestys, String maara, String ohje) {
        this.id = id;
        this.smoothie = smoothie;
        this.raaka_aine = raaka_aine;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Smoothie getSmoothie() {
        return smoothie;
    }

    public RaakaAine getRaaka_aine() {
        return raaka_aine;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }
    
     public Integer getId() {
        return id;
    }
}
