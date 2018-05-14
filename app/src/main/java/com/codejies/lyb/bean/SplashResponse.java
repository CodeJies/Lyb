package com.codejies.lyb.bean;

/**
 * Created by Jies on 2018/5/14.
 */

public class SplashResponse {

    /**
     * description : splash image
     * id : 1
     * url : http://www.fycsb.top:8080/splash/images/splash.png
     */
    private String description;
    private int id;
    private String url;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
