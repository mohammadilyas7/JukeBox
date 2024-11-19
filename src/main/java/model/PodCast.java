package main.java.model;

import java.util.Date;

public class PodCast
{
    private String podcast_Id;
    private String episod_Id;
    private String podcastName;
    private String episod_Name;
    private String celebrity;
    private String duration;
    private Date realeasDate;
    private String songSource;

    //<<<<<<<<< Setter >>>>>>>>>>>>>>>

    public void setPodcast_Id(String podcast_Id)
    {
        this.podcast_Id = podcast_Id;
    }

    public void setEpisod_Id(String episod_Id) {
        this.episod_Id = episod_Id;
    }

    public void setPodcastName(String podcastName) {
        this.podcastName = podcastName;
    }

    public void setEpisod_Name(String episod_Name) {
        this.episod_Name = episod_Name;
    }

    public void setCelebrity(String celebrity) {
        this.celebrity = celebrity;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setRealeasDate(Date realeasDate) {
        this.realeasDate = realeasDate;
    }

    public void setSongSource(String songSource) {
        this.songSource = songSource;
    }

    //<<<<<<<<<<<  Getter  >>>>>>>>>>>>>>>

    public String getPodcast_Id() {
        return podcast_Id;
    }

    public String getEpisod_Id() {
        return episod_Id;
    }

    public String getPodcastName() {
        return podcastName;
    }

    public String getEpisod_Name() {
        return episod_Name;
    }

    public String getCelebrity() {
        return celebrity;
    }

    public String getDuration() {
        return duration;
    }

    public Date getRealeasDate() {
        return realeasDate;
    }

    public String getSongSource() {
        return songSource;
    }

    //<<<<<<<<<<<<<<      Constructer   >>>>>>>>>>>>>>>>>

    public PodCast(String podcast_Id, String episod_Id, String podcastName, String episod_Name, String celebrity, String duration, Date realeasDate, String songSource) {
        this.podcast_Id = podcast_Id;
        this.episod_Id = episod_Id;
        this.podcastName = podcastName;
        this.episod_Name = episod_Name;
        this.celebrity = celebrity;
        this.duration = duration;
        this.realeasDate = realeasDate;
        this.songSource = songSource;
    }

    //   To String Method >>>>>

    @Override
    public String toString() {
        return "PodCast{" +
                "podcast_Id='" + podcast_Id + '\'' +
                ", episod_Id='" + episod_Id + '\'' +
                ", raees='" + podcastName + '\'' +
                ", episod_Name='" + episod_Name + '\'' +
                ", celebrity='" + celebrity + '\'' +
                ", duration='" + duration + '\'' +
                ", realeasDate=" + realeasDate +
                ", songSource='" + songSource + '\'' +
                '}';
    }
}
