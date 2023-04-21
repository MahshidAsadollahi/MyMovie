package com.example.mymovie;

public class SeriesModel {
    private  String Scast;
    private  String Scover;
    private String Sdesc;
    private String Slink;
    private String Stitle;
    private String STlink;
    private String Sthumb;

    public SeriesModel() {

    }

    public SeriesModel(String scast, String scover, String sdesc, String slink, String stitle, String STlink, String sthumb) {
        Scast = scast;
        Scover = scover;
        Sdesc = sdesc;
        Slink = slink;
        Stitle = stitle;
        this.STlink = STlink;
        Sthumb = sthumb;
    }

    public String getScast() {
        return Scast;
    }

    public void setScast(String scast) {
        Scast = scast;
    }

    public String getScover() {
        return Scover;
    }

    public void setScover(String scover) {
        Scover = scover;
    }

    public String getSdesc() {
        return Sdesc;
    }

    public void setSdesc(String sdesc) {
        Sdesc = sdesc;
    }

    public String getSlink() {
        return Slink;
    }

    public void setSlink(String slink) {
        Slink = slink;
    }

    public String getStitle() {
        return Stitle;
    }

    public void setStitle(String stitle) {
        Stitle = stitle;
    }

    public String getSTlink() {
        return STlink;
    }

    public void setSTlink(String STlink) {
        this.STlink = STlink;
    }

    public String getSthumb() {
        return Sthumb;
    }

    public void setSthumb(String sthumb) {
        Sthumb = sthumb;
    }
}