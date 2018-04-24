import com.mysql.jdbc.Statement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class ParseGenres {


    ResultSet resultSet;
    void listFilms(){

        File filmsFolder = new File("/var/www/html/films");
        File[] films = filmsFolder.listFiles();
        Properties connInfo = new Properties();
        connInfo.put("user", "alex");
        connInfo.put("password", "angel");
        connInfo.put("useUnicode","true"); // (1)
        connInfo.put("charSet", "UTF8"); // (2)
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/films?", connInfo);
             PreparedStatement statementFilms = connection.prepareStatement("INSERT INTO films(rating, description, poster, path, year_of_release) VALUE (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statementNames = connection.prepareStatement("INSERT INTO names_film (name_film, id_film) VALUE (?, ?)")) {

            for (File film:films) {

                String actors = new String(Files.readAllBytes(Paths.get(film.getPath() + "/actors.txt")));
                String description = new String(Files.readAllBytes(Paths.get(film.getPath() + "/description.txt")));
                String genres = new String(Files.readAllBytes(Paths.get(film.getPath() + "/genres.txt")));
                String names = new String(Files.readAllBytes(Paths.get(film.getPath() + "/names.txt")));
                String Producer = new String(Files.readAllBytes(Paths.get(film.getPath() + "/Producer.txt")));
                String year = new String(Files.readAllBytes(Paths.get(film.getPath() + "/year.txt")));


            }//for
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }







    }
}
