package com.example.mymovie;

public class DataModel {
//this one is for the movies on top
    private String Title;
    private String Turl;
    private String Tvid;




    public DataModel() {
    }

    public DataModel(String title, String turl,
                     String tvid) {

        Title = title;
        Turl = turl;
        Tvid = tvid;
    }


    //get

    public String getTitle() {
        return Title;
    }

    public String getTurl() {
        return Turl;
    }

    public String getTvid() {
        return Tvid;
    }

    //set

    public void setTitle(String ttitle) {
        Title = ttitle;
    }

    public void setTurl(String turl) {
        Turl = turl;
    }

    public void setTvid(String tvid) {
        Tvid = tvid;
    }
}
