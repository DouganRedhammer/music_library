
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

    public Track(Composer composer, String name) {
        this.composer = composer;
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
        
        //Create some Smashing Pumpkins track and link it to both albums
        Composer billy =  new Composer("Billy Corgan");
        Track perfect = new Track(billy,"Perfect");
        Album adore = Album.find("Adore");
        Album apples = Album.find("Rotten Apples");
        
        perfect.getAlbum().add(adore);
        perfect.getAlbum().add(apples);
       
        //Add some STP tracks
        Composer robert = Composer.find("Robert DeLeo");
        Track creep = new Track(robert,"Creep");
        Track garden = new Track(robert,"Wicked Garden");
        Track dead = new Track(robert,"Dead & Bloated");
        Album core = Album.find("Core");
        creep.getAlbum().add(core);
        garden.getAlbum().add(core);
        dead.getAlbum().add(core);
        
        //Create some Nirvana tracks
        Composer kurt = Composer.find("Kurt Cobain");
        Track smells = new Track(kurt, "Smells Like Teen Spirit");
        Track come = new Track(kurt, "Come as You Are");
        Track polly = new Track(kurt, "Polly");
        Album nevermind = Album.find("Nevermind");
        smells.getAlbum().add(nevermind);
        come.getAlbum().add(nevermind);
        polly.getAlbum().add(nevermind);
        
        
        Transaction tx = session.beginTransaction();
        {
            session.save(billy);
            session.save(perfect);
            session.save(creep);
            session.save(garden);
            session.save(dead);
            session.save(smells);
            session.save(come);
            session.save(polly);
        }
        tx.commit();
        session.close();
        
        System.out.println("Class table loaded.");
    }
    
    
}
