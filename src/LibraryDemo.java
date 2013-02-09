import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LibraryDemo 
{
    private static final String HELP_MESSAGE =
        "*** Commands: create, load, find <n>, add, delete, teachers, quit\n" +
        "***           students, classes";
    
    public static void main(String args[]) 
    {
        BufferedReader stdin = 
                new BufferedReader(new InputStreamReader(System.in));
        String command;
                                    
        Class klasses[] = {Album.class, Artist.class, 
                           Track.class, Composer.class};
        HibernateContext.addClasses(klasses);

        do {
            System.out.print("\nCommand? ");
            
            try {
                command = stdin.readLine();
            }
            catch (java.io.IOException ex) {
                command = "?";
            }
            
            String parts[] = command.split(" ");
            
            if (command.equalsIgnoreCase("create")) {
                HibernateContext.createSchema();
            }
            else if (command.equalsIgnoreCase("load")) {
              Artist.load();
              Album.load();
              Composer.load();
              Track.load();
              
               
            }
            else if (command.equalsIgnoreCase("add")) {
                //Student.add();
            }
            else if (command.equalsIgnoreCase("delete")) {
                //Student.delete();
            }
            else if (command.equalsIgnoreCase("albums")) {
                Album.list();
            }
            else if (!command.equalsIgnoreCase("quit")) {
                System.out.println(HELP_MESSAGE);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }
}
