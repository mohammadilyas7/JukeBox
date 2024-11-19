package model;

public class PlayList
{
    int playListId;
    String playListName;
    int userId;

    public PlayList(int playListId, String playListName, int userId) {
        this.playListId = playListId;
        this.playListName = playListName;
        this.userId = userId;
    }

    public int getPlayListId() {
        return playListId;
    }

    public void setPlayListId(int playListId) {
        this.playListId = playListId;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PlayList{" +
                "playListId=" + playListId +
                ", playListName='" + playListName + '\'' +
                ", userId=" + userId +
                '}';
    }
}