package songImplements;

import model.PodCast;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PodcastImplTest
{
    PodcastImpl podcast = new PodcastImpl();

    @Test
    void retrievePodcasts()
    {
        List<PodCast> podcasts = podcast.retrievePodcasts();
         assertEquals(3,podcasts.size());
        assertNotEquals(2,podcasts.size());
    }
}