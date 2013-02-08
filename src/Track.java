
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name="Track")
public class Track 
{
    private long code;
    private Composer composer;
    private String name;
    private List<Album> album = new ArrayList<Album>();
    
    public Track() {}
    
    public Track(String name)
    {
        this.name = name;
    }
    
    @Id
    @GeneratedValue
    @Column(name="code")
    public long getCode() { return code; }
    public void setCode(long code) { this.code = code; }
    
    @ManyToOne
    @JoinColumn(name="composer_id")
    public Composer getComposer() { return composer; }
    public void setComposer(Composer composer) { this.composer = composer; }
    
    @Column(name="name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    @ManyToMany
    @JoinTable(name="Album_Track", 
               joinColumns={@JoinColumn(name="track_id")},
               inverseJoinColumns={@JoinColumn(name="album_id")})
    public List<Album> getAlbum() { return album; }
    public void setAlbum(List<Album> album) { this.album = album; }
    
    public static Track find(String name)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Track where name = :name");
        
        query.setString("name", name);
        Track track = (Track) query.uniqueResult();
        
        session.close();
        return track;
    }
        
    public static void load()
    {
        Session session = HibernateContext.getSession();
        
        Composer rogers = Composer.find("yo");
        
        Track java = new Track("Java sucks");
        java.setComposer(rogers);
        
        Album doe = Album.find("foo");
        
        // Assign tracks to albums.
        java.getAlbum().add(doe);

        Transaction tx = session.beginTransaction();
        {
            session.save(java);
        }
        tx.commit();
        session.close();
        
        System.out.println("Class table loaded.");
    }
    
    
}
