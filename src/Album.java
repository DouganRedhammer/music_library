import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/*
 * This class represents the album objects in the database
 * 
 * @author Daniel Franklin, Akash Nadha, Pardeep Bajwa
 * @group 
 * 
 */

@Entity
public class Album 
{
    private long id;
    private String name;
    private Artist artist;
    private List<Track> tracks = new ArrayList<Track>();
    
    public Album() {}
    
    public Album(String name, Artist artist)
    {
        this.name = name;
        this.artist = artist;
    }
   
    //The unique id of the album
    @Id
    @GeneratedValue //auto increment
    @Column(name="id")
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    /*
     *  Getter and setter for album name
     */
    @Column(name="name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    /*
     *  A one to one mapping to an artist.
     */
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="album_id")
    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }
    
    /*
     *  An album can have many tracks and the same track could be on many albums
     */
    @ManyToMany
    @JoinTable(name="Album_Track", 
               joinColumns={@JoinColumn(name="album_id")},
               inverseJoinColumns={@JoinColumn(name="track_id")})
    public List<Track> getTrack() { return tracks; }
    public void setTrack(List<Track> tracks) { this.tracks = tracks; }
    
    /*
     *  Loads the initial album data into the database
     * 
     */
     public static void load()
    {
        Session session = HibernateContext.getSession();
        
        // Load the Student table in a transaction.
        Transaction tx = session.beginTransaction();
        {
            session.save(new Album("Nevermind", new Artist("Nirvana")));
            session.save(new Album("Core", new Artist("Stone Temple Pilots")));
            
            Artist tsp = Artist.find("The Smashing Pumpkins");
            session.save(new Album("Rotten Apples", tsp));
            session.save(new Album("Adore", tsp));
        }
        tx.commit();
        session.close();

        System.out.println("Album table loaded.");
    }
     
    /*
     *  finds the album specified by the argument
     *  @return an Album object
     */ 
    public static Album find(String name)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Album where name = :name");
        
        query.setString("name", name);
        Album album = (Album) query.uniqueResult();
        
        session.close();
        return album;
    }
    
    /*
     *  Lists all the albums to the screen
     */
    public static void list()
    {  
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Album.class);
        criteria.addOrder(Order.asc("name"));
        
        List<Album> album = criteria.list();       
        System.out.println("All albums in the music library:");      

      
        for (Album albums : album) 
        {
            System.out.println(albums.getName());
        }
        
        session.close();
    }
    
    
    public void print()
    {
        System.out.printf("%d: %s\n", id, name);
    }
    
    /*
     *  Prints all of the albums with artist, tracks and composer information
     */
    public static void albumDetails()
    {
        Session session = HibernateContext.getSession();
        Criteria albumCriteria = session.createCriteria(Album.class);
        Criteria artistCriteria = albumCriteria.createCriteria("artist");
        artistCriteria.addOrder(Order.desc("name"));
        albumCriteria.addOrder(Order.desc("name"));
        
        List<Album> album = albumCriteria.list();       
        System.out.println("All Albums:");      

      
        for (Album albums : album) {
            Artist artist = albums.getArtist();
            
            System.out.println("Album: " + albums.getName());
            System.out.println("Artist: " + artist.getName());

            // Loop over the album's tracks.
            for (Track track : albums.getTrack()) {
                System.out.printf("          Track: %s\n", track.getName());
                System.out.printf("                 Composer: %s\n\n", track.getComposer().getName());
            }
        }
     }
        
    /*
     *  Prints the specified album with artist, tracks and composer information
     */
    public static void albumDetails(String albumName)  
    {
        Session session = HibernateContext.getSession();
        Criteria albumCriteria = session.createCriteria(Album.class);
        Criteria artistCriteria = albumCriteria.createCriteria("artist");
        
        albumCriteria.add(Restrictions.eq("name", albumName));

        albumCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
       
        albumCriteria.addOrder(Order.desc("name"));
        artistCriteria.addOrder(Order.desc("name"));
        
        List<Album> albums = albumCriteria.list(); 
        
        System.out.println("Album details:");
        for (Album album : albums) {
            Artist artist = album.getArtist();
            
            System.out.println("Album: " + album.getName());
            System.out.println("Artist: " + artist.getName());

            // Loop over the album's tracks.
            for (Track track : album.getTrack()) {
                System.out.printf("          Track: %s\n", track.getName());
                System.out.printf("                 Composer: %s\n\n", track.getComposer().getName());
            }
        }
        
        session.close();
        }
    }
