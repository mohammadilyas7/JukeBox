package model;

import java.util.Date;

public class Podcast
{
    String podId;
    String epId;
    String podName;
    String epName;
    String celebrity;
    String duration;
    Date publishedDate;
    String podSource;

    public Podcast()
    {

    }

    public Podcast(String podId, String epId, String podName, String epName, String celebrity, String duration, Date publishedDate, String podSource) {
        this.podId = podId;
        this.epId = epId;
        this.podName = podName;
        this.epName = epName;
        this.celebrity = celebrity;
        this.duration = duration;
        this.publishedDate = publishedDate;
        this.podSource = podSource;
    }

    public String getPodId() {
        return podId;
    }

    public void setPodId(String podId) {
        this.podId = podId;
    }

    public String getEpId() {
        return epId;
    }

    public void setEpId(String epId) {
        this.epId = epId;
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName;
    }

    public String getCelebrity() {
        return celebrity;
    }

    public void setCelebrity(String celebrity) {
        this.celebrity = celebrity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPodSource() {
        return podSource;
    }

    public void setPodSource(String podSource) {
        this.podSource = podSource;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "podId='" + podId + '\'' +
                ", epId='" + epId + '\'' +
                ", podName='" + podName + '\'' +
                ", epName='" + epName + '\'' +
                ", celebrity='" + celebrity + '\'' +
                ", duration='" + duration + '\'' +
                ", publishedDate=" + publishedDate +
                ", podSource='" + podSource + '\'' +
                '}';
    }
}
