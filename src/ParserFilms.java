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
             PreparedStatement statement = connection.prepareStatement("INSERT INTO films(name_film, rating, description, poster, path, year_of_release) VALUE (?, ?, ?, ?, ?, ?)")) {

            for (File film:films) {

                String actors = new String(Files.readAllBytes(Paths.get(film.getPath() + "/actors.txt")));
                String description = new String(Files.readAllBytes(Paths.get(film.getPath() + "/description.txt")));
                String genres = new String(Files.readAllBytes(Paths.get(film.getPath() + "/genres.txt")));
                String names = new String(Files.readAllBytes(Paths.get(film.getPath() + "/names.txt")));
                String Producer = new String(Files.readAllBytes(Paths.get(film.getPath() + "/Producer.txt")));
                String year = new String(Files.readAllBytes(Paths.get(film.getPath() + "/year.txt")));

                String[] namesArray = names.split(" / ");
                for (int i = 0; i<namesArray.length; i++) {
                    namesArray[i] = namesArray[i].trim();
                }



                statement.setString(1, new Gson().toJson(namesArray));
                statement.setInt(2,5);
                statement.setString(3, description);
                statement.setString(4,"http://localhost/films/" + film.getName() + "/poster.png");
                statement.setString(5, film.getPath() + "filmName");
                statement.setInt(6,Integer.parseInt(year.trim()));
                statement.executeUpdate();
//                resultSet.next();
//                System.out.println(resultSet.getString("DATABASE()"));

            }//for
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }







    }



}
