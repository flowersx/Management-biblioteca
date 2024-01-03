/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author Florin
 */
public class CarteData {

    private Integer Id;
    private String IdCarte;
    private String NumeCarte;
    private String DescriereCarte;
    private Double PretCarte;
    private Date Date;
    private String GenCarte;
    private Integer StocCarte;
    private String Image;
    private String ClientId;

    // Public constructor
    public CarteData(Integer id, String idCarte,
            String numeCarte, String descriereCarte,
            Double pretCarte, Date date,
            String genCarte, Integer stocCarte,
            String image) {
        this.Id = id;
        this.NumeCarte = numeCarte;
        this.IdCarte = idCarte;
        this.DescriereCarte = descriereCarte;
        this.PretCarte = pretCarte;
        this.Date = date;
        this.GenCarte = genCarte;
        this.StocCarte = stocCarte;
        this.Image = image;
    }

    // Public constructor
    public CarteData(Integer id, String idCarte, String numeCarte, Double pretCarte, String image) {
        this.Id = id;
        this.IdCarte = idCarte;
        this.NumeCarte = numeCarte;
        this.PretCarte = pretCarte;
        this.Image = image;
    }
    // Getters and setters 

    public Integer getId() {
        return Id;
    }

    public String getIdCarte() {
        return IdCarte;
    }

    public String getNumeCarte() {
        return NumeCarte;
    }

    public String getDescriereCarte() {
        return DescriereCarte;
    }

    public Double getPretCarte() {
        return PretCarte;
    }

    public Date getDate() {
        return Date;
    }

    public String getGenCarte() {
        return GenCarte;
    }

    public Integer getStocCarte() {
        return StocCarte;
    }

    public String getImage() {
        return Image;
    }
}
