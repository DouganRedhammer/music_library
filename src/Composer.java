
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
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/*
 * This class represents the composer objects in the database
 * 
 * @author Daniel Franklin, Akash Nadha, Pardeep Bajwa
 * @group 
 * 
 */

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
    
    /*
     *  The uniquie id of the composer
     */
    @Id
    @GeneratedValue
    @Column(name="id")
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    /*
     *  The name of the composer
     */
    @Column(name="name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /*
     * A composer may compose many songs
     */
    @OneToMany(mappedBy="composer", targetEntity=Track.class,
               cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public List<Track> getTrack() { return tracks; }
    public void setTrack(List<Track> tracks) { this.tracks = tracks; }
    
    /*
     *  finds the composer specified by the argumnet
     */
    public static Composer find(String name)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Composer where name = :name");
        
        query.setString("name", name);
        Composer composer = (Composer) query.uniqueResult();
        
        session.close();
        return composer;
    }
   
    /*
     *  Loads the initial composer data into the database
     */
    public static void load()
    {
        Session session = HibernateContext.getSession();
        Transaction tx = session.beginTransaction();
        {
            
            session.save(new Composer("Robert DeLeo"));
            session.save(new Composer("Kurt Cobain"));
        }
        tx.commit();
        session.close();

        System.out.println("Composer table loaded.");
    }
    
    /*
     *  Lists all the composers to the screen
     */
    public static void list()
    {  
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Composer.class);
        criteria.addOrder(Order.asc("name"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
       
        List<Composer> composerList = criteria.list();       
        System.out.println("All composers in the muisc library:");      

        for (Composer composer : composerList) 
        {
            System.out.println(composer.getName());
        }
        
        session.close();
    }
}