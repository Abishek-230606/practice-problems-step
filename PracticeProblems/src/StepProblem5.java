import java.util.*;

public class StepProblem5 {

    // page -> total visits
    static HashMap<String, Integer> pageVisits = new HashMap<>();

    // page -> unique visitors
    static HashMap<String, HashSet<String>> uniqueVisitors = new HashMap<>();

    // traffic source -> count
    static HashMap<String, Integer> trafficSource = new HashMap<>();

    // process page view event
    public static void processEvent(String page, String userId, String source) {

        // count page visits
        pageVisits.put(page, pageVisits.getOrDefault(page, 0) + 1);

        // track unique visitors
        uniqueVisitors.putIfAbsent(page, new HashSet<>());
        uniqueVisitors.get(page).add(userId);

        // count traffic sources
        trafficSource.put(source, trafficSource.getOrDefault(source, 0) + 1);
    }

    // display dashboard
    public static void showDashboard() {

        System.out.println("\n--- Dashboard ---");

        // top pages
        System.out.println("Page Visits:");
        for (String page : pageVisits.keySet()) {
            System.out.println(page + " : " + pageVisits.get(page));
        }

        // unique visitors
        System.out.println("\nUnique Visitors:");
        for (String page : uniqueVisitors.keySet()) {
            System.out.println(page + " : " + uniqueVisitors.get(page).size());
        }

        // traffic sources
        System.out.println("\nTraffic Sources:");
        for (String src : trafficSource.keySet()) {
            System.out.println(src + " : " + trafficSource.get(src));
        }
    }

    public static void main(String[] args) {

        // simulate page view events
        processEvent("Home", "U1", "Google");
        processEvent("Sports", "U2", "Facebook");
        processEvent("Home", "U3", "Google");
        processEvent("Politics", "U1", "Direct");
        processEvent("Home", "U1", "Google");

        // show analytics dashboard
        showDashboard();
    }
}