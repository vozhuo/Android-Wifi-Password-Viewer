package com.vozhuo.wifipasswordviewer;

/**
 * Created by qyz on 2016/5/14.
 */
public class WifiInfo {
    private String ssid = "";
    private String password = "";

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public String getSsid() {
        return ssid;
    }
}
