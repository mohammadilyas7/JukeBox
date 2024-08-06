package model;
public class PlaList
{
    private int playlist_Id;
    private String PlayListName;
    private int User_Id;


    public PlaList() {

    }

    //<<<<<<<<<<<    Setter  >>>>>>>>>>>>

    public void setPlaylist_Id(int playlist_Id) {this.playlist_Id = playlist_Id;}
    public void setPlayListName(String playListName) {PlayListName = playListName;}
    public void setUser_Id(int user_Id) {User_Id = user_Id;}

    //<<<<<<<<<<       Getter       >>>>>>>>>>>

    public int getPlaylist_Id() {return playlist_Id;}
    public String getPlayListName() {return PlayListName;}
    public int getUser_Id() {return User_Id;}

    //<<<<<<<<<<    Constructer     >>>>>>>>>>>

    public PlaList(int playlist_Id, String playListName, int user_Id)
    {
        this.playlist_Id = playlist_Id;
        PlayListName = playListName;
        User_Id = user_Id;
    }

    //<<<<<<<<<<    To String   >>>>>>>>>>>>
    @Override
    public String toString()
    {
        return "PlaList{" +
                "playlist_Id=" + playlist_Id +
                ", PlayListName='" + PlayListName + '\'' +
                ", User_Id=" + User_Id +
                '}';
    }
}
