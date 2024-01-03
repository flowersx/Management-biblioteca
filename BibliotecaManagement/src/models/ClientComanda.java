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
public class ClientComanda {

    private Integer Id;
    private Integer ClientId;
    private String NumeCarte;
    private Integer Cantitate;
    private Integer Pret;
    private Date date;
    private String username;

    // Public constructor
    public ClientComanda(Integer id, Integer clientId, String numeCarte, Integer cantitate, Integer pret, Date date, String username) {
        this.Id = id;
        this.ClientId = clientId;
        this.NumeCarte = numeCarte;
        this.Cantitate = cantitate;
        this.Pret = pret;
        this.date = date;
        this.username = username;
    }

    // Getters and setters 
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Integer getClientId() {
        return ClientId;
    }

    public void setClientId(Integer clientId) {
        this.ClientId = clientId;
    }

    public String getNumeCarte() {
        return NumeCarte;
    }

    public void setNumeCarte(String numeCarte) {
        this.NumeCarte = numeCarte;
    }

    public Integer getCantitate() {
        return Cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.Cantitate = cantitate;
    }

    public Integer getPret() {
        return Pret;
    }

    public void setPret(Integer pret) {
        this.Pret = pret;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
