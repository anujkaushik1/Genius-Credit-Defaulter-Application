
package com.example.geniuscredit1;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxpayerInfo {

    @SerializedName("ctj")
    @Expose
    private String ctj;
    @SerializedName("pradr")
    @Expose
    private Pradr pradr;
    @SerializedName("tradeNam")
    @Expose
    private String tradeNam;
    @SerializedName("lgnm")
    @Expose
    private String lgnm;
    @SerializedName("cxdt")
    @Expose
    private String cxdt;
    @SerializedName("ctb")
    @Expose
    private String ctb;
    @SerializedName("rgdt")
    @Expose
    private String rgdt;
    @SerializedName("adadr")
    @Expose
    private List<Object> adadr = null;
    @SerializedName("dty")
    @Expose
    private String dty;
    @SerializedName("lstupdt")
    @Expose
    private String lstupdt;
    @SerializedName("nba")
    @Expose
    private List<String> nba = null;
    @SerializedName("stjCd")
    @Expose
    private String stjCd;
    @SerializedName("stj")
    @Expose
    private String stj;
    @SerializedName("gstin")
    @Expose
    private String gstin;
    @SerializedName("ctjCd")
    @Expose
    private String ctjCd;
    @SerializedName("sts")
    @Expose
    private String sts;




    public String getCtj() {
        return ctj;
    }

    public void setCtj(String ctj) {
        this.ctj = ctj;
    }

    public Pradr getPradr() {
        return pradr;
    }

    public void setPradr(Pradr pradr) {
        this.pradr = pradr;
    }

    public String getTradeNam() {
        return tradeNam;
    }

    public void setTradeNam(String tradeNam) {
        this.tradeNam = tradeNam;
    }

    public String getLgnm() {
        return lgnm;
    }

    public void setLgnm(String lgnm) {
        this.lgnm = lgnm;
    }

    public String getCxdt() {
        return cxdt;
    }

    public void setCxdt(String cxdt) {
        this.cxdt = cxdt;
    }

    public String getCtb() {
        return ctb;
    }

    public void setCtb(String ctb) {
        this.ctb = ctb;
    }

    public String getRgdt() {
        return rgdt;
    }

    public void setRgdt(String rgdt) {
        this.rgdt = rgdt;
    }

    public List<Object> getAdadr() {
        return adadr;
    }

    public void setAdadr(List<Object> adadr) {
        this.adadr = adadr;
    }

    public String getDty() {
        return dty;
    }

    public void setDty(String dty) {
        this.dty = dty;
    }

    public String getLstupdt() {
        return lstupdt;
    }

    public void setLstupdt(String lstupdt) {
        this.lstupdt = lstupdt;
    }

    public List<String> getNba() {
        return nba;
    }

    public void setNba(List<String> nba) {
        this.nba = nba;
    }

    public String getStjCd() {
        return stjCd;
    }

    public void setStjCd(String stjCd) {
        this.stjCd = stjCd;
    }

    public String getStj() {
        return stj;
    }

    public void setStj(String stj) {
        this.stj = stj;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getCtjCd() {
        return ctjCd;
    }

    public void setCtjCd(String ctjCd) {
        this.ctjCd = ctjCd;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

}
