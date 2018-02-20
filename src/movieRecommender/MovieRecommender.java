package movieRecommender;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/** MovieRecommender. A class that is responsible for:
    - Reading movie and ratings data from the file and loading it in the data structure UsersList.
 *  - Computing movie recommendations for a given user and printing them to a file.
 *  - Computing movie "anti-recommendations" for a given user and printing them to file.
 *  Fill in code in methods of this class.
 *  Do not modify signatures of methods.
 */
public class MovieRecommender {
    private UsersList usersData; // linked list of users
    private HashMap<Integer, String> movieMap; // maps each movieId to the movie title

    public MovieRecommender() {
         movieMap = new HashMap<>();
         usersData = new UsersList();
    }

    /**
     * Read user ratings from the file and save data for each user in this list.
     * For each user, the ratings list will be sorted by rating (from largest to
     * smallest).
     * @param movieFilename name of the file with movie info
     * @param ratingsFilename name of the file with ratings info
     */
    public void loadData(String movieFilename, String ratingsFilename) {

        loadMovies(movieFilename);
        loadRatings(ratingsFilename);
    }

    /** Load information about movie ids and titles from the given file.
     *  Store information in a hashmap that maps each movie id to a movie title
     *
     * @param movieFilename csv file that contains movie information.
     *
     */
    private void loadMovies(String movieFilename) {
        // FILL IN CODE
        try(BufferedReader reader = new BufferedReader(new FileReader(movieFilename))){
            String line = reader.readLine();
            line = reader.readLine();
            while(line != null){
                String[] part1 = line.split("\"");
                if(part1.length == 1){
                   String[] part2 = line.split(",");
                   int id = Integer.parseInt(part2[0]);
                   String title = part2[1];
                   movieMap.put(id, title);
                }else{
                    int id = Integer.parseInt(part1[0].replaceAll(",", ""));
                    String title = part1[1];
                    movieMap.put(id, title);
                }
                line = reader.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Load users' movie ratings from the file into UsersList
     * @param ratingsFilename name of the file that contains ratings
     */
    private void loadRatings(String ratingsFilename) {
        // FILL IN CODE
        try(BufferedReader reader = new BufferedReader(new FileReader(ratingsFilename))){
            String line = reader.readLine();
            line = reader.readLine();
            while(line != null){
                String[] part = line.split(",");
                int userId = Integer.parseInt(part[0]);
                int movieId = Integer.parseInt(part[1]);
                double rating = Double.parseDouble(part[2]);
                usersData.insert(userId,movieId,rating);
                line = reader.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * * Computes up to num movie recommendations for the user with the given user
     * id and prints these movie titles to the given file. First calls
     * findMostSimilarUser and then getFavoriteMovies(num) method on the
     * "most similar user" to get up to num recommendations. Prints movies that
     * the user with the given userId has not seen yet.
     * @param userid id of the user
     * @param num max number of recommendations
     * @param filename name of the file where to output recommended movie titles
     *                 Format of the file: one movie title per each line
     */
    public void findRecommendations(int userid, int num, String filename) {

        // compute similarity between userid and all the other users
        // find the most similar user and recommend movies that the most similar
        // user rated as 5.
        // Recommend only the movies that userid has not seen (has not
        // rated).
        // FILL IN CODE
        UserNode user = usersData.get(userid);
        UserNode best = usersData.findMostSimilarUser(userid);
        int[] bestList = best.getFavoriteMovies(num);

        Path outPath = Paths.get(filename);
        outPath.getParent().toFile().mkdirs();
        try(PrintWriter writer = new PrintWriter(filename)){
            for(int i = 0; i < bestList.length; i++){
                if(!user.ifSeen(bestList[i])){
                    String title = movieMap.get(bestList[i]) + "\n";
                    System.out.println(title);
                    writer.write(title);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * Computes up to num movie anti-recommendations for the user with the given
     * user id and prints these movie titles to the given file. These are the
     * movies the user should avoid. First calls findMostSimilarUser and then
     * getLeastFavoriteMovies(num) method on the "most similar user" to get up
     * to num movies the most similar user strongly disliked. Prints only
     * those movies to the file that the user with the given userid has not seen yet.
     * Format: one movie title per each line
     * @param userid id of the user
     * @param num max number of anti-recommendations
     * @param filename name of the file where to output anti-recommendations (movie titles)
     */
    public void findAntiRecommendations(int userid, int num, String filename) {

        // compute similarity between userid and all the other users
        // find the most similar user and anti-recommend movies that the most similar
        // user rated as 1.
        // Anti-recommend only the movies that userid has not seen (has not
        // rated).
        // FILL IN CODE
        UserNode user = usersData.get(userid);
        UserNode best = usersData.findMostSimilarUser(userid);
        int[] bestList = best.getLeastFavoriteMovies(num);

        Path outPath = Paths.get(filename);
        outPath.getParent().toFile().mkdirs();
        try(PrintWriter writer = new PrintWriter(filename)){
            for(int i = 0; i < bestList.length; i++){
                if(!user.ifSeen(bestList[i])){
                    String title = movieMap.get(bestList[i]) + "\n";
                    System.out.println(title);
                    writer.write(title);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
