
package com.example.geniuscredit1;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GSTJson {

    @SerializedName("taxpayerInfo")
    @Expose
    private TaxpayerInfo taxpayerInfo;
    @SerializedName("filing")
    @Expose
    private List<Object> filing = null;
    @SerializedName("compliance")
    @Expose
    private Compliance compliance;





    public TaxpayerInfo getTaxpayerInfo() {
        return taxpayerInfo;
    }

    public void setTaxpayerInfo(TaxpayerInfo taxpayerInfo) {
        this.taxpayerInfo = taxpayerInfo;
    }

    public List<Object> getFiling() {
        return filing;
    }

    public void setFiling(List<Object> filing) {
        this.filing = filing;
    }

    public Compliance getCompliance() {
        return compliance;
    }

    public void setCompliance(Compliance compliance) {
        this.compliance = compliance;
    }

}
