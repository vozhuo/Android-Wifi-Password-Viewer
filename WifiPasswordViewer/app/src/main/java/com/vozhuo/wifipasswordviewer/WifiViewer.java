package com.vozhuo.wifipasswordviewer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qyz on 2016/5/14.
 */
public class WifiViewer {
    public List<WifiInfo> View() throws IOException {
        List<WifiInfo> wifiInfos = new ArrayList<WifiInfo>();


        Process process = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        StringBuffer wifiInfo  = new StringBuffer();

        try {
            process = Runtime.getRuntime().exec("su"); //获取最高权限
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream = new DataOutputStream(process.getOutputStream());

            dataOutputStream.writeBytes("cat /data/misc/wifi/*.conf\n"); //显示Wifi文件
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();

            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                wifiInfo.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            process.destroy();
        }

        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);  //利用正则表达式过滤Wifi信息
        Matcher networkMatcher = network.matcher(wifiInfo.toString());

        while (networkMatcher.find()) {
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkMatcher.group());

            if(ssidMatcher.find()) {
                WifiInfo info = new WifiInfo();
                info.setSsid(ssidMatcher.group(1));

                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkMatcher.group());
                if (pskMatcher.find()) {
                    info.setPassword(pskMatcher.group(1));
                } else {
                    info.setPassword("无密码");
                }
                wifiInfos.add(info);
            }
        }
        return wifiInfos;
    }
}