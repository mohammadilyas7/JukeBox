package operation;

import model.Podcast;

import java.text.ParseException;
import java.util.List;

public interface IPodcastOperations extends AutoCloseable
{
    public List<Podcast> retrievePodcasts();
    public void displayPodcasts(List<Podcast> podcasts);
    public void search(List<Podcast> podcasts);
    public void searchByPublishedDate(List<Podcast> podcasts) throws ParseException;
}
