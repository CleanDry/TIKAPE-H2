/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package H2;

/**
 *
 * @author Sami Pulli
 */
public class SmoothieRaakaAine {
    private Integer id;
    private Integer smoothieId;
    private Integer raakaAineId;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public SmoothieRaakaAine(Integer id, Integer smoothieId, Integer raakaAineId, Integer jarjestys, String maara, String ohje) {
        this.id = id;
        this.smoothieId = smoothieId;
        this.raakaAineId = raakaAineId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getId() {
        return id;
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

    public Integer getRaakaAineId() {
        return raakaAineId;
    }

    public Integer getSmoothieId() {
        return smoothieId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

    public void setRaakaAineId(Integer raakaAineId) {
        this.raakaAineId = raakaAineId;
    }

    public void setSmoothieId(Integer smoothieId) {
        this.smoothieId = smoothieId;
    }

}
