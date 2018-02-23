package movieRecommender;

/** A driver class for the MovieRecommender. In the main method, we
 * create a movie recommender, load movie data from files and compute
 * recommendations and anti-recommendations for a particular user.
 */
public class Driver {
    public static void main(String[] args) {
        UsersList ul = new UsersList();
        UserNode un1 = new UserNode(1);
        un1.insert(0,5.0);
        un1.insert(1,5.0);
        un1.insert(2,5.0);
        un1.insert(3,4.0);
        un1.insert(4,1.0);
        un1.insert(7,3.5);
        un1.insert(6,1.0);
        un1.insert(5,1.0);

        UserNode un2 = new UserNode(2);
        un2.insert(0,5.0);
        un2.insert(1,4.0);
        un2.insert(2,5.0);
        un2.insert(3,1.0);
        un2.insert(4,2.0);
        un2.insert(5,5.0);
        un2.insert(6,3.0);



        int[] result = un1.getLeastFavoriteMovies(2);
        for(int i = 0; i < result.length; i++){
            System.out.println(result[i]);
        }

    }


}
