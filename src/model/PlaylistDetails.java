package model;

public class PlaylistDetails
{
    int detailId;
    int playlistId;
    String songidorpodcastid;
    String playlisttype;

    public PlaylistDetails(int detailId, int playlistId, String songidorpodcastid, String playlisttype) {
        this.detailId = detailId;
        this.playlistId = playlistId;
        this.songidorpodcastid = songidorpodcastid;
        this.playlisttype = playlisttype;
    }
    
    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getSongidorpodcastid() {
        return songidorpodcastid;
    }

    public void setSongidorpodcastid(String songidorpodcastid) {
        this.songidorpodcastid = songidorpodcastid;
    }

    public String getPlaylisttype() {
        return playlisttype;
    }

    public void setPlaylisttype(String playlisttype) {
        this.playlisttype = playlisttype;
    }

    @Override
    public String toString() {
        return "PlaylistDetails{" +
                "detailId=" + detailId +
                ", playlistId=" + playlistId +
                ", songidorpodcastid='" + songidorpodcastid + '\'' +
                ", playlisttype='" + playlisttype + '\'' +
                '}';
    }
}
