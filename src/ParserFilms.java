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

        File filmsFolder = new File("/var/www/html/films");
        File[] films = filmsFolder.listFiles();
        Properties connInfo = new Properties();
        connInfo.put("user", "alex");
        connInfo.put("password", "angel");
        connInfo.put("useUnicode","true"); // (1)
        connInfo.put("charSet", "UTF8"); // (2)
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/films?", connInfo);
             PreparedStatement statementFilms = connection.prepareStatement("INSERT INTO films(rating, description, poster, path, year_of_release) VALUE (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statementNames = connection.prepareStatement("INSERT INTO names_film (name_film, id_film) VALUE (?, ?)");
             PreparedStatement statementGenres = connection.prepareStatement("INSERT INTO genres (genre) VALUE (?)");
             PreparedStatement statementConnGenres = connection.prepareStatement("INSERT INTO connections_genres (film, genre) VALUE (?, ?)");
             PreparedStatement statementActors = connection.prepareStatement("INSERT INTO actors (name_actor) VALUE (?)");
             PreparedStatement statementConnActors = connection.prepareStatement("INSERT INTO connections_actors (film, actor) VALUE (?, ?)");
             PreparedStatement statementCountries = connection.prepareStatement("INSERT INTO countries (country) VALUE (?)");
             PreparedStatement statementConnCountries = connection.prepareStatement("INSERT INTO connections_countries (film, country) VALUE (?, ?)");
             PreparedStatement statementWriters = connection.prepareStatement("INSERT INTO writers (name_writers) VALUE (?)");
             PreparedStatement statementConnWriters = connection.prepareStatement("INSERT INTO connections_writers (film, writers) VALUE (?, ?)")

             ) {

            for (File film:films) {

                String actors = new String(Files.readAllBytes(Paths.get(film.getPath() + "/actors.txt")));
                String description = new String(Files.readAllBytes(Paths.get(film.getPath() + "/description.txt")));
                String genres = new String(Files.readAllBytes(Paths.get(film.getPath() + "/genres.txt")));
                String names = new String(Files.readAllBytes(Paths.get(film.getPath() + "/names.txt")));
                String writers = new String(Files.readAllBytes(Paths.get(film.getPath() + "/Producer.txt")));
                String year = new String(Files.readAllBytes(Paths.get(film.getPath() + "/year.txt")));
                String countries = new String(Files.readAllBytes(Paths.get(film.getPath() + "/countries.txt")));

                statementFilms.setInt(1,5);
                statementFilms.setString(2, description);
                statementFilms.setString(3,"http://93.170.123.54/films/" + film.getName() + "/poster.png");
                statementFilms.setString(4, film.getPath() + "filmName");
                statementFilms.setInt(5,Integer.parseInt(year.trim()));
                statementFilms.executeUpdate();

                ResultSet generatedKeys = statementFilms.getGeneratedKeys();
                generatedKeys.next();
                int id = generatedKeys.getInt(1);


                String[] namesArray = names.split(" / ");
                for (int i = 0; i<namesArray.length; i++) {

                    statementNames.setString(1, namesArray[i].trim());
                    statementNames.setInt(2, id);
                    statementNames.executeUpdate();
                }

                String[] genresArray = genres.split(", ");

                for (int i = 0; i<genresArray.length; i++) {
                    String genre = genresArray[i].trim();
                    try {
                        statementGenres.setString(1, genre);
                        statementGenres.executeUpdate();
                        //System.out.println(genresArray[i].trim() + " - Добавлен");
                    }catch (Exception e){
                        //System.out.println(genresArray[i].trim() + " - Уже есть в базе");
                    }

                    statementConnGenres.setInt(1, id);
                    statementConnGenres.setString(2, genre);
                    statementConnGenres.executeUpdate();

                }


                String[] actorsArray = actors.split(", ");

                for (int i = 0; i<actorsArray.length; i++) {
                    String actor = actorsArray[i];
                    try{
                        if (actor.contains("(")) actor = actorsArray[i].substring(0, actorsArray[i].indexOf("(") - 1);
                        statementActors.setString(1, actor.trim());
                        statementActors.executeUpdate();
                    }catch (Exception e){
                        //System.out.println(actorsArray[i].trim() + " - Уже есть в базе");
                    }

                    statementConnActors.setInt(1, id);
                    statementConnActors.setString(2, actor.trim());
                    statementConnActors.executeUpdate();
                }

                String[] countriesArray = countries.split(", ");
                for (int i = 0; i<countriesArray.length; i++) {
                    String country = countriesArray[i];
                    try{
                        statementCountries.setString(1, country.trim());
                        statementCountries.executeUpdate();
                    }catch (Exception e){
                        //System.out.println(actorsArray[i].trim() + " - Уже есть в базе");
                    }

                    statementConnCountries.setInt(1, id);
                    statementConnCountries.setString(2, country.trim());
                    statementConnCountries.executeUpdate();
                }


                String[] writersArray = writers.split(", ");
                for (int i = 0; i<writersArray.length; i++) {
                    String writer = writersArray[i];
                    try{
                        statementWriters.setString(1, writer.trim());
                        statementWriters.executeUpdate();
                    }catch (Exception e){
                        //System.out.println(actorsArray[i].trim() + " - Уже есть в базе");
                    }

                    statementConnWriters.setInt(1, id);
                    statementConnWriters.setString(2, writer.trim());
                    statementConnWriters.executeUpdate();
                }

            }//for
        }catch (Exception e){
            e.printStackTrace();
        }







    }



}
