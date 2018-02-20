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

//        MovieRatingsList ml = new MovieRatingsList();
//        ml.insertByRating(0,5.0);
//        ml.insertByRating(1,5);
//        ml.insertByRating(2,5);
//        ml.insertByRating(3,1);
//        ml.insertByRating(4,1);
//        ml.insertByRating(5,1);
//        ml.insertByRating(6,1);
////        ml.setRating(2,3.5);
////        System.out.println(ml.getMiddleNode());
////        System.out.println(ml.getMedianRating());
//
//        ml.print();
//        System.out.print("\n*****************\n");
////        ml.setRating(4,2.5);
////        ml.print();
////        System.out.print("\n");
//////        System.out.println(ml.getRating(2));
//////        System.out.println(ml.getRating(5));
//        MovieRatingNode head = ml.getHead();
//        MovieRatingsList ml2 = ml.reverse(head);
//        ml2.print();
//        System.out.print("\n*****************\n");
//        double d = ml.computeSimilarity(ml2);
//        System.out.print(d);



//        MovieRecommender recommender = new MovieRecommender();
//
//        // movies.csv and ratings.csv should be in the project folder
//        recommender.loadData("movies.csv","ratings.csv");
//        System.out.println("Loaded movie data...");
//
//        recommender.findRecommendations(3, 15, "recommendations");
//        System.out.println();
//        recommender.findAntiRecommendations(3, 15, "antiRecommendations");

    }


}
