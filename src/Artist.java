import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/*
 * This class represents the artist objects in the database
 * 
 * @author Daniel Franklin, Akash Nadha, Pardeep Bajwa
 * @group 
 * 
 */

@Entity
@Table(name="Artist")
public class Artist 
{
    private long id;
    private String name;
    private List<Album> albums;
    public Artist() {}
    
    public Artist(String name)
    {
        this.name = name;
    }
    
    /*
     *  The unique id of the artist
     */
    @Id
    @GeneratedValue
    @Column(name="id")
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    /*
     *  The name of the artist
     */
    @Column(name="name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    /*
     *  An artist may have many albums
     */
    @OneToMany(mappedBy="artist", targetEntity=Album.class,
               cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public List<Album> getAlbum() { return albums; }
    public void setAlbum(List<Album> tracks) { this.albums = tracks; }

    /*
     *  finds the artist specified by the argumnet
     */
    public static Artist find(String name)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Artist where name = :name");
        
        query.setString("name", name);
        Artist artist = (Artist) query.uniqueResult();
        
        session.close();
        return artist;
    }
        
    /*
     *  Loads the initial artist data into the database
     */
    public static void load()
    {
        Session session = HibernateContext.getSession();
        Transaction tx = session.beginTransaction();
        {          
            session.save(new Artist("The Smashing Pumpkins"));
        }
        tx.commit();
        session.close();

        System.out.println("Album table loaded.");
    }
    
    /*
     *  prints the id and name of the artist
     */
    public void print()
    {
        System.out.printf("%d: %s\n", id, name);
    }
    
    /*
     *  Lists all the artists to the screen
     */
    public static void list()
    {  
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Artist.class);
        criteria.addOrder(Order.asc("name"));
        
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
       
        List<Artist> artistList = criteria.list();       
        System.out.println("All artist in the muisc library:");      

      
        for (Artist artists : artistList) 
        {
            System.out.println(artists.getName());
        }
        
        session.close();
    }
    
    /*
     *  Prints all of the artists with all the albums associated with the artist
     */
    public static void artistDetails()
    {
        Session session = HibernateContext.getSession();
        Criteria artistCriteria = session.createCriteria(Artist.class);
        Criteria albumCriteria = artistCriteria.createCriteria("album");
        artistCriteria.addOrder(Order.desc("name"));
        albumCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Artist> artists = artistCriteria.list();       
        System.out.println("All artist with albums:");      

      
        for (Artist artist : artists) {
          
            System.out.println(artist.getName());
            System.out.println("Albums: ");
            // Loop over the album's tracks.
            for (Album album : artist.getAlbum())
            {
                 System.out.println("       "+album.getName());
            }
            System.out.println();
        }
     }
    
    /*
     *  Finds the artist specified by the argument then prints 
     *  all the albums associated with the artist
     */
    public static void artistDetails(String artistName)
    {  
        Session session = HibernateContext.getSession();
        Criteria artistCriteria = session.createCriteria(Artist.class);
        Criteria albumCriteria = artistCriteria.createCriteria("album");
        artistCriteria.addOrder(Order.desc("name"));
        artistCriteria.add(Restrictions.eq("name", artistName));
        artistCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Artist> artists = artistCriteria.list();       
      
        for (Artist artist : artists) {
          
            System.out.println(artist.getName());
            System.out.println("Albums: ");

            for (Album album : artist.getAlbum())
            {
                 System.out.println("       "+album.getName());
            }
            System.out.println();
        }
     }
        
}
