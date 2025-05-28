package org.example.eksamensprojektbilabonnement.Model;

import java.math.BigDecimal;

public class Bil
{
    private Long bilId;
    private String brand;
    private String model;
    private String nummerPlade;
    private String stelNummer;
    private BigDecimal pris;
    private int produktionAar;
    private String braendstoftype;
    private String bilStatus;

    public Bil() {}

    public Bil(Long bilId, String brand, String model, String nummerPlade, String stelNummer, BigDecimal pris, int produktionAar, String braendstoftype, String bilStatus)
    {
        this.bilId = bilId;
        this.brand = brand;
        this.model = model;
        this.nummerPlade = nummerPlade;
        this.stelNummer = stelNummer;
        this.pris = pris;
        this.produktionAar = produktionAar;
        this.braendstoftype = braendstoftype;
        this.bilStatus = bilStatus;
    }

    public Long getBilId() {
        return bilId;
    }

    public void setBilId(Long bilId) {
        this.bilId = bilId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNummerPlade() {
        return nummerPlade;
    }

    public void setNummerPlade(String nummerPlade) {
        this.nummerPlade = nummerPlade;
    }

    public String getStelNummer() {
        return stelNummer;
    }

    public void setStelNummer(String stelNummer) {
        this.stelNummer = stelNummer;
    }

    public BigDecimal getPris() {
        return pris;
    }

    public void setPris(BigDecimal pris) {
        this.pris = pris;
    }

    public int getProduktionAar() {
        return produktionAar;
    }

    public void setProduktionAar(int produktionAar) {
        this.produktionAar = produktionAar;
    }

    public String getBraendstoftype() {
        return braendstoftype;
    }

    public void setBraendstoftype(String braendstoftype) {
        this.braendstoftype = braendstoftype;
    }

    public String getBilStatus() {
        return bilStatus;
    }

    public void setBilStatus(String bilStatus) {
        this.bilStatus = bilStatus;
    }

}