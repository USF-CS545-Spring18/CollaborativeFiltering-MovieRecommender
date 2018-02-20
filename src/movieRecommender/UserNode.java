package movieRecommender;

/** UserNode. The class represents a node in the UsersList.
 *  Stores a userId, a list of ratings of type MovieRatingsList,
 *  and a reference to the "next" user in the list.
 *  FILL IN CODE in methods getFavoriteMovies and getLeastFavoriteMovies
 *
 *  Do not modify signatures of methods.
 *  */
public class UserNode {
    private int userId;
    private MovieRatingsList movieRatings;
    private UserNode nextUser;

    /** A constructor for the UserNode.
     * @param id 	User id
     * */
    public UserNode(int id) {
        userId = id;
        movieRatings = new MovieRatingsList();
        nextUser = null;
    }

    /**
     * Getter for the next reference
     * @return the next node in the linked list of users
     */
    public UserNode next() {
        return nextUser;
    }

    /**
     * Setter for the next reference
     * @param anotherUserNode A user node
     */
    public void setNext(UserNode anotherUserNode) {
        this.nextUser = anotherUserNode;
    }

    /** Return a userId stored in this node */
    public int getId() {
        return userId;
    }

    /** Print info contained in this node:
     *  userId and a list of ratings.
     *  Expected format: (userid) movieId:rating; movieId:rating; movieId:rating; */
    public void print() {
        System.out.print("(" + userId + ") ");
        movieRatings.print();

    }

    public String toString(){
        String s = "(" + userId + ") ";
        s = s + movieRatings.toString();
        return s;
    }


    /**
     * Add rating info for a given movie to the MovieRatingsList
     *  for this user node
     *
     * @param movieId id of the movie
     * @param rating  rating of the movie
     */
    public void insert(int movieId, double rating) {
        movieRatings.insertByRating(movieId, rating);

    }

    /**
     * return if the user has seen the movie;
     * @param movieId
     * @return
     */
    public boolean ifSeen(int movieId){
        MovieRatingNode head = movieRatings.getHead();
        MovieRatingNode current = head;
        while(current != null){
            int id = current.getMovieId();
            if(movieId == id){
                return true;
            }
            current = current.next();
        }
        return false;
    }

    /**
     * Returns an array of user's favorite movies (up to n). These are the
     * movies that this user gave the rating of 5.
     *
     * @param n  the maximum number of movies to return
     * @return array containing movie ids of movies rated as 5 (by this user)
     */
    public int[] getFavoriteMovies(int n) {
        // FILL IN CODE
        MovieRatingsList best = movieRatings.getNBestRankedMovies(n);
        MovieRatingNode cur1 = best.getHead();
        MovieRatingNode cur2 = best.getHead();
        int max = 0;
        while(cur1 != null){
            max++;
            cur1 = cur1.next();
        }
        int[] result = new int[max];
        int index = 0;
        while (cur2 != null){
            int id = cur2.getMovieId();
            result[index] = id;
            index++;
            cur2 = cur2.next();
        }

        return result; // don't forget to change
    }

    /**
     * Returns an array of movies the user likes the least (up to n). These
     * are the movies that this user gave the rating of 1.
     *
     * @param n the maximum number of movies to return
     * @return array of movie ids of movies rated as 1
     */
    public int[] getLeastFavoriteMovies(int n) {
        // FILL IN CODE
        MovieRatingsList worst = movieRatings.getNWorstRankedMovies(n);
        MovieRatingNode cur1 = worst.getHead();
        MovieRatingNode cur2 = worst.getHead();
        int max = 0;
        while(cur1 != null){
            max++;
            cur1 = cur1.next();
        }
        int[] result = new int[max];
        int index = 0;
        while (cur2 != null){
            int id = cur2.getMovieId();
            result[index] = id;
            index++;
            cur2 = cur2.next();
        }

        return result; // don't forget to change
    }

    /**
     * Computes the similarity of this user with the given "other" user using
     * Pearson correlation - simply calls computeSimilarity method
     * from MovieRatingsList
     *
     * @param otherUser a user to compare the current user with
     * @return similarity score
     */
    public double computeSimilarity(UserNode otherUser) {
        return movieRatings.computeSimilarity(otherUser.movieRatings);
    }

}
