package main.java.model;

import java.util.Date;

public class Song
{
   private int song_Id;
   private String song_Name;
   private String Genre;
   private String Artist;
   private float duration;
   private Date releasDate;
   private String Album;
   private String source;

   //<<<<<<<<<< Setter >>>>>>>>>>>>

   public void setSong_Id(int song_Id) {
      this.song_Id = song_Id;
   }

   public void setSong_Name(String song_Name) {
      this.song_Name = song_Name;
   }

   public void setGenre(String genre) {
      Genre = genre;
   }

   public void setArtist(String artist) {
      Artist = artist;
   }

   public void setDuration(float duration) {
      this.duration = duration;
   }

   public void setReleasDate(Date releasDate) {
      this.releasDate = releasDate;
   }

   public void setAlbum(String album) {
      Album = album;
   }

   public void setSource(String source) {
      this.source = source;
   }

   //<<<<<<<<<<<   Getter  >>>>>>>>>>>>>

   public int getSong_Id() {
      return song_Id;
   }

   public String getSong_Name() {
      return song_Name;
   }

   public String getGenre() {
      return Genre;
   }

   public String getArtist() {
      return Artist;
   }

   public float getDuration() {
      return duration;
   }

   public Date getReleasDate() {
      return releasDate;
   }

   public String getAlbum() {
      return Album;
   }

   public String getSource() {
      return source;
   }

   //<<<<<<<<<<   Constructer    >>>>>>>>>>>>>

   public Song(int song_Id, String song_Name, String genre, String artist, float duration, Date releasDate, String album, String source) {
      this.song_Id = song_Id;
      this.song_Name = song_Name;
      Genre = genre;
      Artist = artist;
      this.duration = duration;
      this.releasDate = releasDate;
      Album = album;
      this.source = source;
   }

   //<<<<<<<<<<<< To String method   >>>>>>>>>>>>>>

   @Override
   public String toString()
   {
      return "Song{" +
              "song_Id=" + song_Id +
              ", song_Name='" + song_Name + '\'' +
              ", Genre='" + Genre + '\'' +
              ", Artist='" + Artist + '\'' +
              ", duration=" + duration +
              ", releasDate=" + releasDate +
              ", Album='" + Album + '\'' +
              ", source='" + source + '\'' +
              '}';
   }
}
