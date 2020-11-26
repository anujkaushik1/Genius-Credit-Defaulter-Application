
package com.example.geniuscredit1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pradr {

    @SerializedName("ntr")
    @Expose
    private String ntr;
    @SerializedName("addr")
    @Expose
    private Addr addr;

    public String getNtr() {
        return ntr;
    }

    public void setNtr(String ntr) {
        this.ntr = ntr;
    }

    public Addr getAddr() {
        return addr;
    }

    public void setAddr(Addr addr) {
        this.addr = addr;
    }

}
