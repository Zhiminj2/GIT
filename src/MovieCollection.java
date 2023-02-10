import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Set;
import java.util.List;


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

        String title = movieFromCSV[0];
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

        Movie newMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
        movies.add(newMovie);
      }
      bufferedReader.close();
    } catch (IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
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
    System.out.print("Enter a keyword search term: ");
    String key = scanner.nextLine();
    key = key.toLowerCase();

    ArrayList<Movie> result = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getKeywords();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(key) != -1) {
        result.add(movies.get(i));
      }
    }
    if (movies.size() > 0) {
      // sort the results by title
      sortResults(movies);

      // now, display them all to the user
      for (int i = 0; i < result.size(); i++) {
        String title = result.get(i).getCast();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = result.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else
    {
      System.out.println("\nNo movie Keyword match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void searchCast() {
    System.out.print("Enter the name of a cast: ");
    String searchTerm = scanner.nextLine();
    searchTerm = searchTerm.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();
    ArrayList<String> cast = new ArrayList<String>();

    // Find all matching cast
    for (Movie movie : movies) {
      String[] actors = movie.getCast().split("\\|");
      for (String actor : actors) {
        String name = actor.toLowerCase();
        if (name.contains(searchTerm) && !cast.contains(actor)) {
          cast.add(actor);
        }
      }
    }

    if (cast.size() > 0) {
      Collections.sort(cast);

      // Display matching cast to the user
      for (int i = 0; i < cast.size(); i++) {
        String person = cast.get(i);
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + person);
      }

      System.out.println("Which cast would you like the find the movies of? ");
      System.out.print("Enter the number next to the cast: ");
      int searchNum = scanner.nextInt();
      String selectedCast = cast.get(searchNum - 1);
      ArrayList<String> titles = new ArrayList<>();

      // Find all matching movies for selected cast
      for (Movie movie : movies) {
        if (movie.getCast().contains(selectedCast) && !titles.contains(movie.getTitle())) {
          results.add(movie);
          titles.add(movie.getTitle());
        }
      }

      if (results.size() > 0) {
        sortResults(results);

        // Display matching movie titles to the user
        for (int i = 0; i < results.size(); i++) {
          String title = results.get(i).getTitle();
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
        System.out.println("\nNo movie titles match that keyword!");
        System.out.println("** Press Enter to Return to Main Menu **");
        scanner.nextLine();
      }
    } else {
      System.out.println("\nNo cast match that keyword!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }




  private void listGenres() {
    ArrayList<String> genres = getAllGenres();
    if (genres.size() > 0) {
      Collections.sort(genres);
      displayGenreChoices(genres);
      int searchNum = getSearchNum();
      String selectedGenre = genres.get(searchNum - 1);
      ArrayList<Movie> results = getMoviesByGenre(selectedGenre);
      if (results.size() > 0) {
        sortResults(results);
        displayMovieChoices(results);
        int choice = getMovieChoice();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
      } else {
        System.out.println("\nNo movie titles match that keyword!");
      }
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private ArrayList<String> getAllGenres() {
    ArrayList<String> genres = new ArrayList<>();
    for (Movie movie : movies) {
      String[] movieGenres = movie.getGenres().split("\\|");
      for (String genre : movieGenres) {
        if (!genres.contains(genre)) {
          genres.add(genre);
        }
      }
    }
    return genres;
  }

  private void displayGenreChoices(ArrayList<String> genres) {
    for (int i = 0; i < genres.size(); i++) {
      int choiceNum = i + 1;
      System.out.println("" + choiceNum + ". " + genres.get(i));
    }
    System.out.println("Which genre of movies would you like to find? ");
    System.out.print("Enter the number next to the genre: ");
  }

  private int getSearchNum() {
    return scanner.nextInt();
  }

  private ArrayList<Movie> getMoviesByGenre(String selectedGenre) {
    ArrayList<Movie> results = new ArrayList<>();
    for (Movie movie : movies) {
      if (movie.getGenres().contains(selectedGenre)) {
        results.add(movie);
      }
    }
    return results;
  }

  private void displayMovieChoices(ArrayList<Movie> results) {
    for (int i = 0; i < results.size(); i++) {
      int choiceNum = i + 1;
      System.out.println("" + choiceNum + ". " + results.get(i).getTitle());
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
  }

  private int getMovieChoice() {
    return scanner.nextInt();
  }


  private void listHighestRated() {
    ArrayList<Movie> allMovies = movies;
    ArrayList<Movie> highestRated = new ArrayList<Movie>();


    for (int i = 0; i < 50; i++) {
      double maxRating = allMovies.get(0).getUserRating();
      int maxIndex = 0;

      for (int j = 1; j < allMovies.size(); j++) {
        double currentRating = allMovies.get(j).getUserRating();
        if (currentRating > maxRating) {
          maxRating = currentRating;
          maxIndex = j;
        }
      }

      highestRated.add(allMovies.remove(maxIndex));
    }

    for (int i = 0; i < highestRated.size(); i++) {
      System.out.println((i + 1) + ". " + highestRated.get(i).getTitle() + " " + highestRated.get(i).getUserRating());
    }
  }


  private void listHighestRevenue() {
    ArrayList<Movie> allMovies = movies;
    ArrayList<Movie> highestRev = new ArrayList<Movie>();
    int rev = 50;

    for (int i = 0; i < rev; i++) {
      double maxRev = allMovies.get(0).getRevenue();
      int maxInd = 0;

      for (int j = 1; j < allMovies.size(); j++) {
        double currentREV = allMovies.get(j).getRevenue();
        if (currentREV > maxRev) {
          maxRev = currentREV;
          maxInd = j;
        }
      }

      highestRev.add(allMovies.remove(maxInd));
    }

    for (int i = 0; i < highestRev.size(); i++) {
      System.out.println((i + 1) + ". " + highestRev.get(i).getTitle() + " $" + highestRev.get(i).getRevenue());
    }
  }
  }


