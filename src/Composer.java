
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
public class Composer 
{
    private long id;
    private String name;
    private Artist artist;
    private List<Track> tracks;

    public Composer() {}
    
    public Composer(String name)
    {
        this.name = name;
    }
    
    @Id
    @GeneratedValue
    @Column(name="id")
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    @Column(name="name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    /*
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="artist_id")
    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }
    */
    @OneToMany(mappedBy="composer", targetEntity=Track.class,
               cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public List<Track> getTrack() { return tracks; }
    public void setTrack(List<Track> tracks) { this.tracks = tracks; }
    
    public static Composer find(String name)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Composer where name = :name");
        
        query.setString("name", name);
        Composer composer = (Composer) query.uniqueResult();
        
        session.close();
        return composer;
    }
        
    public static void load()
    {
        Session session = HibernateContext.getSession();
        
        // Load the Student table in a transaction.
        Transaction tx = session.beginTransaction();
        {
            
            session.save(new Composer("Robert DeLeo"));
            session.save(new Composer("Kurt Cobain"));
        }
        tx.commit();
        session.close();

        System.out.println("Composer table loaded.");
    }
}