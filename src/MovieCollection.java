import javax.naming.PartialResultException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        /* TASK 1: FINISH THE CODE BELOW */
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        // keywords, overview, runtime (int), genres,
        // user rating (double), year (int), and revenue (int)


        // create a Movie object with the row data:
        String name = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        Movie nextMovie = new Movie(name, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // add the Movie to movies:
        movies.add(nextMovie);

      }
      bufferedReader.close();
    } catch (IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();
      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortString(ArrayList<String> listToSort) {
    Collections.sort(listToSort);
  }

  private void sortRatings(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      Double tempRating = temp.getUserRating();
      int possibleIndex = j;
      while (possibleIndex > 0 && tempRating > listToSort.get(possibleIndex - 1).getUserRating()) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortRevenue(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      int tempRevenue = temp.getRevenue();
      int possibleIndex = j;
      while (possibleIndex > 0 && tempRevenue > listToSort.get(possibleIndex - 1).getRevenue()) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieKeyword = movies.get(i).getKeywords();
      movieKeyword = movieKeyword.toLowerCase();

      if (movieKeyword.indexOf(searchTerm) != -1) {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movies match that keyword term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }

  }

  private void searchCast() {
    System.out.print("Enter a person to search for (first and last name): ");
    String name = scanner.nextLine();

    // prevent case sensitivity
    name = name.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<>();


    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieCast = movies.get(i).getCast();
      movieCast = movieCast.toLowerCase();

      if (movieCast.contains(name)) {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }
    // now, display them all to the user
    String temp = "";
    for (int i = 0; i < results.size(); i++) {
      temp += results.get(i).getCast() + "|";
    }
    ArrayList<String> array = new ArrayList<String>(Arrays.asList(temp.split("\\|")));
    int idx = 0;
    ArrayList<String> searchedCast = new ArrayList<String>();
    while (idx < array.size()) {
      if (array.get(idx).toLowerCase().contains(name) && !searchedCast.contains(array.get(idx))) {
        searchedCast.add(array.get(idx));
      }
      idx++;
    }
    if (searchedCast.size() > 0) {
      // sort the results by title
      sortString(searchedCast);

      for (int j = 0; j < searchedCast.size(); j++) {
        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = j + 1;
        System.out.println("" + choiceNum + ". " + searchedCast.get(j));
      }
      System.out.println("Which would you like to see all movies for?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      String castMember = searchedCast.get(choice - 1);
      scanner.nextLine();
      ArrayList <Movie> allMovies = new ArrayList<>();
      for (int i = 0; i < movies.size(); i++) {
        if (movies.get(i).getCast().contains(castMember)) {
          allMovies.add(movies.get(i));
        }
      }
      sortResults(allMovies);
      for (int j = 0; j < allMovies.size(); j++) {
        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = j + 1;
        System.out.println("" + choiceNum + ". " + allMovies.get(j).getTitle());
      }
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int num = scanner.nextInt();
      Movie currentMovie = allMovies.get(num - 1);
      scanner.nextLine();
      displayMovieInfo(currentMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo actors with that word!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }


  private void listGenres() {
    String temp = "";
    for (int i = 0; i < movies.size(); i++) {
      temp += movies.get(i).getGenres() + "|";
    }
    ArrayList<String> totalGenres = new ArrayList<String>(Arrays.asList(temp.split("\\|")));
    int idx = 0;
    ArrayList<String> removeDupes = new ArrayList<>();
    while (idx < totalGenres.size()) {
      if (!removeDupes.contains(totalGenres.get(idx))) {
        removeDupes.add(totalGenres.get(idx));
      }
      idx++;
    }
    sortString(removeDupes);
    totalGenres = removeDupes;

    for (int j = 0; j < totalGenres.size(); j++) {
      int choiceNum = j + 1;
      System.out.println("" + choiceNum + ". " + totalGenres.get(j));
    }
    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    String currentGenre = totalGenres.get(choice - 1);
    scanner.nextLine();
    ArrayList <Movie> allMovies = new ArrayList<>();
    for (int k = 0; k < movies.size(); k++) {
      if (movies.get(k).getGenres().contains(currentGenre)) {
        allMovies.add(movies.get(k));
      }
    }
    sortResults(allMovies);
    for (int l = 0; l < allMovies.size(); l++) {
      int choiceNum = l + 1;
      System.out.println("" + choiceNum + ". " + allMovies.get(l).getTitle());
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int num = scanner.nextInt();
    Movie currentMovie = allMovies.get(num - 1);
    scanner.nextLine();
    displayMovieInfo(currentMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  
  private void listHighestRated() {
    ArrayList<Movie> rated = movies;
    sortResults(rated);
    sortRatings(rated);
    for (int i = 0; i < 50; i++) {
      System.out.println( i + 1 + ". " + rated.get(i).getTitle() + ": " + rated.get(i).getUserRating());
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int num = scanner.nextInt();
    Movie currentMovie = rated.get(num - 1);
    scanner.nextLine();
    displayMovieInfo(currentMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
    }

  
  private void listHighestRevenue() {
    ArrayList<Movie> revenue = movies;
    sortResults(revenue);
    sortRevenue(revenue);
    for (int i = 0; i < 50; i++) {
      System.out.println( i + 1 + ". " + revenue.get(i).getTitle() + ": " + revenue.get(i).getRevenue());
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int num = scanner.nextInt();
    Movie currentMovie = revenue.get(num - 1);
    scanner.nextLine();
    displayMovieInfo(currentMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();  }
}