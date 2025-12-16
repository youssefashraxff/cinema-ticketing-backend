// import com.example.cinematicketingbackend.repository.MovieRepository;
// import com.example.cinematicketingbackend.model.Movie;
// import java.util.List;

// public class TestMovieRepository {
//     public static void main(String[] args) {
//         System.out.println("Testing MovieRepository...");

//         try {
//             MovieRepository repo = new MovieRepository();

//             // Test load
//             System.out.println("Loading movies...");
//             List<Movie> movies = repo.load();
//             System.out.println("Loaded " + (movies != null ? movies.size() : 0) + " movies");

//             if (movies != null && !movies.isEmpty()) {
//                 Movie firstMovie = movies.get(0);
//                 System.out.println("First movie: " + firstMovie.getName());
//             }

//             // Test save
//             System.out.println("Saving movies...");
//             repo.save(movies);
//             System.out.println("Movies saved successfully!");

//         } catch (Exception e) {
//             System.err.println("Error: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }
// }
