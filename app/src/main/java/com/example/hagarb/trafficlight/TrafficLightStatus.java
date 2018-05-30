package com.example.hagarb.trafficlight;

/**
 * Created by Tzahi on 14/05/2018.
 */

public class TrafficLightStatus {
    private boolean status=false;
    private int TlcCode;


    public TrafficLightStatus(boolean status, int code){
        this.status = status;
        this.TlcCode = code;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTlcCode() {
        return TlcCode;
    }

    public void setTlcCode(int tlcCode) {
        TlcCode = tlcCode;
    }
}
