package com.example.hagarb.trafficlight;

public class Configuration {
    private String pubnubSubscribeKey;
    private String pubnubPublishKey;
    private String chanel;

    public Configuration(){
        this.pubnubSubscribeKey = "sub-c-57f7dbae-c9e6-11e7-8a2d-cad296c360f6";
        this.pubnubPublishKey = "pub-c-e31073c6-6513-4b2a-8d0b-99535bc49aff";
        this.chanel="Tzahi";
    }

    public String getPubnubSubscribeKey(){
        return this.pubnubSubscribeKey;
    }
    public String getPubnubPublishKey(){
        return this.pubnubPublishKey;
    }
    public String getChanel(){
        return this.chanel;
    }

}
