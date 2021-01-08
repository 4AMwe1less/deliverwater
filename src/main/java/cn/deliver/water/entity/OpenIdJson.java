package cn.deliver.water.entity;

/**
 * @ClassName OpenIdJson
 * @Description TODO
 * @Author J1angHao
 * Date 2020.09.15 下午 12:00
 * Version 1.0
 **/
public class OpenIdJson {
    private String openid;
    private String session_key;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    @Override
    public String toString() {
        return "OpenIdJson{" +
                "openid='" + openid + '\'' +
                ", session_key='" + session_key + '\'' +
                '}';
    }

    public OpenIdJson() {
    }

    public OpenIdJson(String openid, String session_key) {
        this.openid = openid;
        this.session_key = session_key;
    }
}
