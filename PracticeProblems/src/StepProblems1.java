import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StepProblems {

    // Store existing usernames (O(1) lookup)
    private static Set<String> usernames = ConcurrentHashMap.newKeySet();

    // Track popularity of attempted usernames
    private static ConcurrentHashMap<String, AtomicInteger> usernameAttempts = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        // Simulate existing users
        usernames.add("john");
        usernames.add("john123");
        usernames.add("alice");
        usernames.add("david");

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter username to check:");
        String input = sc.nextLine();

        boolean available = isUsernameAvailable(input);

        if (available) {
            System.out.println("Username available: " + input);
        } else {
            System.out.println("Username already taken.");
            System.out.println("Suggestions: " + suggestUsernames(input));
        }

        sc.close();
    }

    // O(1) username availability check
    public static boolean isUsernameAvailable(String username) {

        // Track popularity
        usernameAttempts
                .computeIfAbsent(username, k -> new AtomicInteger(0))
                .incrementAndGet();

        return !usernames.contains(username);
    }

    // Suggest similar usernames
    public static List<String> suggestUsernames(String username) {

        List<String> suggestions = new ArrayList<>();
        Random random = new Random();

        int count = 0;

        while (suggestions.size() < 5 && count < 20) {

            String suggestion = username + (random.nextInt(900) + 100);

            if (!usernames.contains(suggestion)) {
                suggestions.add(suggestion);
            }

            count++;
        }

        return suggestions;
    }

    // Optional: show most popular attempted usernames
    public static void printPopularAttempts() {

        usernameAttempts.forEach((user, count) -> {
            System.out.println(user + " attempted " + count.get() + " times");
        });
    }
}