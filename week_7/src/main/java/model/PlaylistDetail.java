package model;

public class PlaylistDetail
{
    private int Detail_Id;
    private int Playlist_Id;
    private String SongIdOrPostcast_Id;
    private String SongTypes;

    //<<<<<     Setter  >>>>>>>>>>

    public void setDetail_Id(int detail_Id) {
        Detail_Id = detail_Id;
    }

    public void setPlaylist_Id(int playlist_Id) {Playlist_Id = playlist_Id;}

    public void setSongIdOrPostcast_Id(String songIdOrPostcast_Id)
    {
        SongIdOrPostcast_Id = songIdOrPostcast_Id;
    }

    public void setSongTypes(String songTypes)
    {
        SongTypes = songTypes;
    }

    //<<<<<     Getter     >>>>>>>>>>>>

    public int getDetail_Id()
    {
        return Detail_Id;
    }

    public int getPlaylist_Id()
    {
        return Playlist_Id;
    }

    public String getSongIdOrPostcast_Id()
    {
        return SongIdOrPostcast_Id;
    }

    public String getSongTypes()
    {
        return SongTypes;
    }

    //<<<<  Constructer >>>>>>>

    public PlaylistDetail(int detail_Id, int playlist_Id, String songIdOrPostcast_Id, String songTypes) {
        Detail_Id = detail_Id;
        Playlist_Id = playlist_Id;
        SongIdOrPostcast_Id = songIdOrPostcast_Id;
        SongTypes = songTypes;
    }

    //<<<<<<    To String Method    >>>>>>

    @Override
    public String toString() {
        return "PlaylistDetail{" +
                "Detail_Id=" + Detail_Id +
                ", Playlist_Id=" + Playlist_Id +
                ", SongIdOrPostcast_Id='" + SongIdOrPostcast_Id + '\'' +
                ", SongTypes='" + SongTypes + '\'' +
                '}';
    }

}
