

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

@Entity
@Table(name="Artist")
public class Artist 
{
    private long id;
    private String name;
    
    public Artist() {}
    
    public Artist(String name)
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
    
    public static Artist find(String name)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Artist where name = :name");
        
        query.setString("name", name);
        Artist artist = (Artist) query.uniqueResult();
        
        session.close();
        return artist;
    }
        
    public static void load()
    {
        Session session = HibernateContext.getSession();
        
        // Load the Student table in a transaction.
        Transaction tx = session.beginTransaction();
        {
            session.save(new Album("foo", new Artist("bag")));

        }
        tx.commit();
        session.close();

        System.out.println("Album table loaded.");
    }
    
    
    
}
