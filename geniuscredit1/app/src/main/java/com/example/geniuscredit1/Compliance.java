
package com.example.geniuscredit1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Compliance {

    @SerializedName("filingFrequency")
    @Expose
    private Object filingFrequency;

    public Object getFilingFrequency() {
        return filingFrequency;
    }

    public void setFilingFrequency(Object filingFrequency) {
        this.filingFrequency = filingFrequency;
    }

}
