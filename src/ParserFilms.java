import com.google.gson.Gson;
import com.mysql.jdbc.Statement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class ParserFilms {

    ResultSet resultSet;
    void listFilms(){

        File filmsFolder = new File("C:\\Apache24\\htdocs\\films");
        File[] films = filmsFolder.listFiles();
        Properties connInfo = new Properties();
        connInfo.put("user", "root");
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

                statementFilms.setInt(1,5);
                statementFilms.setString(2, description);
                statementFilms.setString(3,"http://localhost/films/" + film.getName() + "/poster.png");
                statementFilms.setString(4, film.getPath() + "filmName");
                statementFilms.setInt(5,Integer.parseInt(year.trim()));
                statementFilms.executeUpdate();

                ResultSet generatedKeys = statementFilms.getGeneratedKeys();
                generatedKeys.next();
                int id = generatedKeys.getInt(1);
                System.out.println("id: " + id);

                 String[] namesArray = names.split(" / ");
                for (int i = 0; i<namesArray.length; i++) {

                    statementNames.setString(1, namesArray[i].trim());
                    statementNames.setInt(2, id);
                    statementNames.executeUpdate();
                }

            }//for
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }







    }



}
