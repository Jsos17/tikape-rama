/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.Objects;

/**
 *
 * @author jpssilve
 */
public class SmoothieRaakaAineTulostusApu implements Comparable<SmoothieRaakaAineTulostusApu> {
   
    private String smoothieNimi;
    private String raakaAineNimi;
    private String jarjestys;
    private String maara;
    private String ohje;
    
    public SmoothieRaakaAineTulostusApu(String smoothieNimi, String raakaAineNimi, String jarjestys, String maara, String ohje) {
        this.smoothieNimi = smoothieNimi;
        this.raakaAineNimi = raakaAineNimi;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.smoothieNimi);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SmoothieRaakaAineTulostusApu other = (SmoothieRaakaAineTulostusApu) obj;
        if (!Objects.equals(this.smoothieNimi, other.smoothieNimi)) {
            return false;
        }
        return true;
    }

    public void setSmoothieNimi(String smoothieNimi) {
        this.smoothieNimi = smoothieNimi;
    }

    public void setRaakaAineNimi(String raakaAineNimi) {
        this.raakaAineNimi = raakaAineNimi;
    }

    public void setJarjestys(String jarjestys) {
        this.jarjestys = jarjestys;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

    public String getSmoothieNimi() {
        return smoothieNimi;
    }

    public String getRaakaAineNimi() {
        return raakaAineNimi;
    }

    public String getJarjestys() {
        return jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }
    
    @Override
    public int compareTo(SmoothieRaakaAineTulostusApu smraTulApu) {
        int tulos = this.getSmoothieNimi().compareTo(smraTulApu.getSmoothieNimi());
        
        if (tulos == 0) {
            return this.getJarjestys().compareTo(smraTulApu.getJarjestys());
        }
                    
        return tulos;
    }
}
