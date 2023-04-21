package com.example.mymovie;

public class CombinedModel {
    //movies
    private String Fcast;
    private String Fcover;
    private String Fdes;
    private String Flink;
    private String Fthumb;
    private String Ftitle;
    private String Tlink;

    //series
    private String Scast;
    private String Scover;
    private String Sdesc;
    private String Slink;
    private String Stitle;
    private String Sthumb;
    private String STlink;




    public CombinedModel(FeatureModel featureModel) {
        this.Fcast = featureModel.getFcast();
        this.Fcover = featureModel.getFcover();
        this.Fdes = featureModel.getFdes();
        this.Flink = featureModel.getFlink();
        this.Fthumb = featureModel.getFthumb();
        this.Ftitle = featureModel.getFtitle();
        this.Tlink = featureModel.getTlink();



    }
    public CombinedModel(SeriesModel seriesModel) {
        this.Scast=seriesModel.getScast();
        this.Scover=seriesModel.getScover();
        this.Sdesc=seriesModel.getSdesc();
        this.Slink=seriesModel.getSlink();
        this.Stitle=seriesModel.getStitle();
        this.Sthumb=seriesModel.getSthumb();
        this.STlink=seriesModel.getSTlink();
    }

    public CombinedModel(String fcast, String fcover, String fdes, String flink, String fthumb, String ftitle, String tlink, String scast, String scover, String sdesc, String slink, String stitle, String sthumb, String STlink) {
        Fcast = fcast;
        Fcover = fcover;
        Fdes = fdes;
        Flink = flink;
        Fthumb = fthumb;
        Ftitle = ftitle;
        Tlink = tlink;
        Scast = scast;
        Scover = scover;
        Sdesc = sdesc;
        Slink = slink;
        Stitle = stitle;
        Sthumb = sthumb;
        this.STlink = STlink;


    }



    //setter and getter of both


    public String getFcast() {
        return Fcast;
    }

    public void setFcast(String fcast) {
        Fcast = fcast;
    }

    public String getFcover() {
        return Fcover;
    }

    public void setFcover(String fcover) {
        Fcover = fcover;
    }

    public String getFdes() {
        return Fdes;
    }

    public void setFdes(String fdes) {
        Fdes = fdes;
    }

    public String getFlink() {
        return Flink;
    }

    public void setFlink(String flink) {
        Flink = flink;
    }

    public String getFthumb() {
        return Fthumb;
    }

    public void setFthumb(String fthumb) {
        Fthumb = fthumb;
    }

    public String getFtitle() {
        return Ftitle;
    }

    public void setFtitle(String ftitle) {
        Ftitle = ftitle;
    }

    public String getTlink() {
        return Tlink;
    }

    public void setTlink(String tlink) {
        Tlink = tlink;
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

    public String getSthumb() {
        return Sthumb;
    }

    public void setSthumb(String sthumb) {
        Sthumb = sthumb;
    }

    public String getSTlink() {
        return STlink;
    }

    public void setSTlink(String stlink) {
        STlink = stlink;
    }
    public boolean isMovie() {
        return Ftitle != null && Flink != null && Fcover != null && Fthumb != null && Fdes != null && Fcast != null;
    }



}
