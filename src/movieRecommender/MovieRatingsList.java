package movieRecommender;

/**
 * MovieRatingsList.
 * A class that stores movie ratings for a user in a custom singly linked list of
 * MovieRatingNode objects. Has various methods to manipulate the list. Stores
 * only the head of the list (no tail! no size!). The list should be sorted by
 * rating (from highest to smallest).
 * Fill in code in the methods of this class.
 * Do not modify signatures of methods. Not all methods are needed to compute recommendations,
 * but all methods are required for the assignment.
 */

import java.util.HashMap;
import java.util.Iterator;

public class MovieRatingsList implements Iterable<MovieRatingNode> {

	private MovieRatingNode head;
	// Note: you are not allowed to store the tail or the size of this list


	/**
	 * Changes the rating for a given movie to newRating. The position of the
	 * node within the list should be changed accordingly, so that the list
	 * remains sorted by rating (from largest to smallest).
	 *
	 * @param movieId id of the movie
	 * @param newRating new rating of this movie
	 */
	public void setRating(int movieId, double newRating) {
		// FILL IN CODE
		MovieRatingNode cur = head;
		if(head == null){
			insertByRating(movieId, newRating);
			return;
		}
		if(head.getMovieId() == movieId){
			head = null;
			insertByRating(movieId,newRating);
			return;
		}
		while(cur != null && cur.next() != null){
			if(cur.next().getMovieId() == movieId){
				cur.setNext(cur.next().next());
				break;
			}
			cur = cur.next();
		}
		insertByRating(movieId,newRating);

	}

    /**
     * Return the rating for a given movie. If the movie is not in the list,
     * returns -1.
     * @param movieId movie id
     * @return rating of a movie with this movie id
     */
	public double getRating(int movieId) {
		// FILL IN CODE
		MovieRatingNode cur = head;
		while(cur != null){
			if(cur.getMovieId() == movieId){
				return cur.getMovieRating();
			}
			cur = cur.next();
		}
		return -1; // don't forget to change it

	}


    /**
     * Insert a new node (with a given movie id and a given rating) into the list.
     * Insert it in the right place based on the value of the rating. Assume
     * the list is sorted by the value of ratings, from highest to smallest. The
     * list should remain sorted after this insert operation.
     *
     * @param movieId id of the movie
     * @param rating rating of the movie
     */
	public void insertByRating(int movieId, double rating) {
		// FILL IN CODE. Make sure to test this method thoroughly
		MovieRatingNode prev = null;
		MovieRatingNode current = head;
		MovieRatingNode newNode = new MovieRatingNode(movieId,rating);
		if(head == null){
			head = newNode;
			return;
		}
		if(head.getMovieRating() < newNode.getMovieRating()){
			newNode.setNext(head);
			head = newNode;
			return;
		}
		if(head.getMovieRating() == newNode.getMovieRating() && head.getMovieId() < newNode.getMovieId()){
			newNode.setNext(head);
			head = newNode;
			return;
		}
		while(current != null){
			if(current.getMovieRating() < newNode.getMovieRating()){
				prev.setNext(newNode);
				newNode.setNext(current);
				break;
			}else if(current.getMovieRating() == newNode.getMovieRating() && current.getMovieId() < newNode.getMovieId()){
				prev.setNext(newNode);
				newNode.setNext(current);
				break;
			}
			prev = current;
			current = current.next();
		}
		prev.setNext(newNode);
	}

    /**
     * Computes similarity between two lists of ratings using Pearson correlation.
	 * https://en.wikipedia.org/wiki/Pearson_correlation_coefficient
	 * Note: You are allowed to use a HashMap for this method.
     *
     * @param otherList another MovieRatingList
     * @return similarity computed using Pearson correlation
     */
    public double computeSimilarity(MovieRatingsList otherList) {
		double similarity = 0;
		HashMap<Integer, Double> map1 = new HashMap<Integer, Double>();
		HashMap<Integer, Double> map2 = new HashMap<Integer, Double>();
        // FILL IN CODE
		MovieRatingNode current = head;
		while (current != null){
			int id = current.getMovieId();
			double rate = current.getMovieRating();
			map1.put(id, rate);
			current = current.next();
		}

		current = otherList.getHead();
		while (current != null){
			int id = current.getMovieId();
			double rate = current.getMovieRating();
			map2.put(id, rate);
			current = current.next();
		}

		//calculate begin
		int n = 0;
		double exy = 0;
		double ex = 0;
		double ey = 0;
		double ex2 = 0;
		double ey2 = 0;

		for(int i: map1.keySet()){
			if(map2.containsKey(i)){
				n++;
				double x = map1.get(i);
				double y = map2.get(i);
				exy = exy + x * y;
				ex = ex + x;
				ey = ey + y;
				ex2 = ex2 + x * x;
				ey2 = ey2 + y * y;
			}
		}
		double up = n * exy - ex * ey;
		double downLeft = Math.sqrt(n * ex2 - ex * ex);
		double downRight = Math.sqrt(n * ey2 - ey * ey);;
		double down = downLeft * downRight;
		similarity = up / down;
        return similarity;

    }
    /**
     * Returns a sublist of this list where the rating values are in the range
     * from begRating to endRating, inclusive.
     *
     * @param begRating lower bound for ratings in the resulting list
     * @param endRating upper bound for ratings in the resulting list
     * @return sublist of the MovieRatingsList that contains only nodes with
     * rating in the given interval
     */
	public MovieRatingsList sublist(int begRating, int endRating) {
		MovieRatingsList res = new MovieRatingsList();

		MovieRatingNode cur = head;
		while(cur != null){
			if(cur.getMovieRating() >= begRating && cur.getMovieRating() <= endRating) {
				res.insertByRating(cur.getMovieId(), cur.getMovieRating());
			}
			cur = cur.next();
		}
		// FILL IN CODE
		return res;
	}

	/** Traverses the list and prints the ratings list in the following format:
	 *  movieId:rating; movieId:rating; movieId:rating;  */
	public void print() {
		// FILL IN CODE
		MovieRatingNode current = head;
		while(current != null){
			System.out.print(current.getMovieId() + ":" + current.getMovieRating() + "; ");
			current = current.next();
		}
	}

	/** Traverses the list and toString the ratings list in the following format:
	 *  movieId:rating; movieId:rating; movieId:rating;  */
	public String toString(){
		MovieRatingNode current = head;
		String result = "";
		while(current != null){
			result = result + current.getMovieId() + ":" + current.getMovieRating() + "; ";
			current = current.next();
		}
		return result;
	}

	/**
	 * Returns the middle node in the list - the one half way into the list.
	 * Needs to have the running time O(n), and should be done in one pass
     * using slow & fast pointers (as described in class).
	 *
	 * @return the middle MovieRatingNode
	 */
	public MovieRatingNode getMiddleNode() {
		MovieRatingNode p1 = head;
		MovieRatingNode p2 = head;
		if(head == null){
			return null;
		}
		while(p2 != null && p2.next() != null && p2.next().next() != null){
			p1 = p1.next();
			p2 = p2.next().next();
		}

		// FILL IN CODE
		return p1; // don't forget to change it
	}

    /**
     * Returns the median rating (the number that is halfway into the sorted
     * list). To compute it, find the middle node and return it's rating. If the
     * middle node is null, return -1.
     *
     * @return rating stored in the node in the middle of the list
     */
	public double getMedianRating() {
		// FILL IN CODE
		MovieRatingNode middleNode = getMiddleNode();
		if(middleNode == null){
			return -1;
		}

		return middleNode.getMovieRating(); // don't forget to change it
	}

    /**
     * Returns a RatingsList that contains n best rated movies. These are
     * essentially first n movies from the beginning of the list. If the list is
     * shorter than size n, it will return the whole list.
     *
     * @param n the maximum number of movies to return
     * @return MovieRatingsList containing movies rated as 5
     */
	public MovieRatingsList getNBestRankedMovies(int n) {
		// FILL IN CODE
		MovieRatingsList result = new MovieRatingsList();

		MovieRatingNode cur = head;
		int iter = 0;
		while(cur != null && iter < n){
			result.insertByRating(cur.getMovieId(), cur.getMovieRating());
			cur = cur.next();
			iter++;
		}

//		result.print();

		return result; // don't forget to change
	}

    /**
     * * Returns a RatingsList that contains n worst rated movies for this user.
     * Essentially, these are the last n movies from the end of the list. You are required to
	 * use slow & fast pointers to find the n-th node from the end (as discussed in class).
	 * Note: This method should compute the result in one pass. Do not use size variable
	 * Do NOT use reverse(). Do not destroy the list.
     *
     * @param n the maximum number of movies to return
     * @return MovieRatingsList containing movies rated as 1
     */
	public MovieRatingsList getNWorstRankedMovies(int n) {
		MovieRatingsList result = new MovieRatingsList();
		MovieRatingNode p1 = head;
		MovieRatingNode p2 = head;
		int iter = 0;
		while(p2 != null && iter < n){
			p2 = p2.next();
			iter++;
		}
		while(p1 != null && p2 != null){
			p1 = p1.next();
			p2 = p2.next();
		}

		while(p1 != null){
			result.insertByRating(p1.getMovieId(), p1.getMovieRating());
			p1 = p1.next();
		}
		// FILL IN CODE
		result.print();
		return result; // don't forget to change
	}

	/**
	 * get the head of this list
	 * @return
	 */
	public MovieRatingNode getHead() {
		return head;
	}

	/**
     * Return a new list that is the reverse of the original list. The returned
     * list is sorted from lowest ranked movies to the highest rated movies.
     * Use only one additional MovieRatingsList (the one you return) and constant amount
     * of memory. You may NOT use arrays, ArrayList and other built-in Java Collections classes.
     * Read description carefully for requirements regarding implementation of this method.
	 *
     * @param h head of the MovieRatingList to reverse
     * @return reversed list
     */
	public MovieRatingsList reverse(MovieRatingNode h) {
		MovieRatingsList r = new MovieRatingsList();
		MovieRatingNode current = h;

		if(current == null){
			r.head = current;
			return r;
		}

		MovieRatingNode prev = null;
		while(current != null){
			int idCopy = current.getMovieId();
			double rateCopy = current.getMovieRating();
			MovieRatingNode newNode = new MovieRatingNode(idCopy, rateCopy);
			newNode.setNext(prev);
			prev = newNode;
			current = current.next();
		}
		r.head = prev;
		return r;
	}

	public Iterator<MovieRatingNode> iterator() {

		return new MovieRatingsListIterator(0);
	}

	/**
	 * Inner class, MovieRatingsListIterator
	 * The iterator for the ratings list. Allows iterating over the MovieRatingNode-s of
	 * the list.
	 * FILL IN CODE
	 */
	private class MovieRatingsListIterator implements Iterator<MovieRatingNode> {

		MovieRatingNode curr = null;

		public MovieRatingsListIterator(int index) {
			// FILL IN CODE
			if(index == 0){
				curr = new MovieRatingNode(-1, 3);
				curr.setNext(head);
				return;
			}
			curr = head;
			for(int i = 1; i < index; i++){
				curr = curr.next();
			}

		}

		@Override
		public boolean hasNext() {
			// FILL IN CODE

			return curr != null && curr.next() != null; // don't forget to change
		}

		@Override
		public MovieRatingNode next() {
			// FILL IN CODE
			if (!hasNext()) {
				System.out.println("No next element");
				return null;
			}
			curr = curr.next();
			return curr;
			// don't forget to change
		}

	}

}
