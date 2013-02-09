import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryDemo 
{
    private static final String HELP_MESSAGE =
        "*** Commands: create, load, artists, composers, albums\n" +
        "***           detailed artists, detailed albums , album <name>\n"+
        "***           artist <name>, quit";
    
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
            else if (command.equalsIgnoreCase("artists")) {
                Artist.list();
            }
            else if (command.equalsIgnoreCase("tracks")) {
                Track.list();
            }
            else if (command.equalsIgnoreCase("composers")) {
                Composer.list();
            }
            else if (command.equalsIgnoreCase("albums")) {
                Album.list();
            }
            else if (command.equalsIgnoreCase("detailed albums")) {
                Album.albumDetails();
            }
            else if (command.equalsIgnoreCase("detailed artists")) {
                Artist.artistDetails();
            }
            else if (parts[0].equalsIgnoreCase("album") && (parts.length >= 2)) 
            {
                StringBuilder albumName = new StringBuilder();
                for(int i = 1; i<parts.length; i++)
                {
                    albumName.append(parts[i]);
                    albumName.append(" ");
                }
            
                Album.albumDetails(albumName.toString());
            }
            else if (parts[0].equalsIgnoreCase("artist") && (parts.length >= 2)) 
            {
                
                StringBuilder artistName = new StringBuilder();
                for(int i = 1; i<parts.length; i++)
                {
                    artistName.append(parts[i]);
                    artistName.append(" ");
                }
 
                
                Artist.artistDetails(artistName.toString());
            }
            else if (!command.equalsIgnoreCase("quit")) {
                System.out.println(HELP_MESSAGE);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }
}
