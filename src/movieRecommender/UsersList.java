package movieRecommender;


import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * A custom linked list that stores user info. Each node in the list is of type
 * UserNode.
 * FILL IN CODE. Also, add other methods as needed.
 *
 * @author okarpenko
 *
 */
public class UsersList {
    private UserNode head = null;
    private UserNode tail = null; // ok to store tail here, will be handy for appending


    /** Insert the rating for the given userId and given movieId.
     *
     * @param userId  id of the user
     * @param movieId id of the movie
     * @param rating  rating given by this user to this movie
     */
    public void insert(int userId, int movieId, double rating) {

          // Check if UserNode already exists;
        UserNode cur = head;
        boolean flag = false;
        while(cur != null){
            int id = cur.getId();
            if(id == userId){
                flag = true;
                cur.insert(movieId, rating);
                break;
            }
            cur = cur.next();
        }
        // if not, create it and append to this list.
        if(!flag){
            UserNode newNode = new UserNode(userId);
            append(newNode);
            newNode.insert(movieId, rating);
        }


          // Then call insert(movieId, rating) method on the UserNode
          // FILL IN CODE

    }

    /**
     * Append a new node to the list
     * @param newNode a new node to append to the list
     */
    public void append(UserNode newNode) {
        // This is where tail will come in handy
        // FILL IN CODE
        if(head == null){
            head = newNode;
            tail = newNode;
            return;
        }
        tail.setNext(newNode);
        tail = newNode;

    }

    /** Return a UserNode given userId
     *
     * @param userId id of the user (as defined in ratings.csv)
     * @return UserNode for a given userId
     */
    public UserNode get(int userId) {
        // FILL IN CODE
        UserNode cur = head;
        while (cur != null){
            if(cur.getId() == userId){
                break;
            }
            cur = cur.next();
        }

        return cur; // don't forget to change it
    } // get method


    /**
     * The method computes the similarity between the user with the given userid
     * and all the other users. Finds the maximum similarity and returns the
     * "most similar user".
     * Calls computeSimilarity method in class MovieRatingsList/
     *
     * @param userid id of the user
     * @return the node that corresponds to the most similar user
     */
    public UserNode findMostSimilarUser(int userid) {
        UserNode mostSimilarUser = null;
        UserNode user = get(userid);
        UserNode current = head;
        double highest = -1;
        double newOne = -1;
        while(current != null){
            if(current.getId() != userid){
                newOne = user.computeSimilarity(current);
                if(newOne > highest){
                    highest = newOne;
                    mostSimilarUser = current;
                }
            }
            current = current.next();
        }

        return mostSimilarUser;

    }

    /** Print UsersList to a file  with the given name in the following format:
     (userid) movieId:rating; movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating; movieId:rating; movieId:rating;
     Info for each userid should be printed on a separate line
     * @param filename name of the file where to output UsersList info
     */
    public void print(String filename) {
        // FILL IN CODE
        UserNode current = head;
        try(PrintWriter pw = new PrintWriter(filename)){
            while (current != null){
                pw.write(current.toString());
                current = current.next();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
