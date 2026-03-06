import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StepProblem2 {

    // Product stock stored in hash table for O(1) lookup
    private static ConcurrentHashMap<String, AtomicInteger> inventory = new ConcurrentHashMap<>();

    // Waiting list for products when stock is exhausted
    private static ConcurrentHashMap<String, Queue<String>> waitingList = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        // Initialize product with 100 units
        inventory.put("IPHONE15", new AtomicInteger(100));
        waitingList.put("IPHONE15", new ConcurrentLinkedQueue<>());

        // Simulate 50000 concurrent customers
        int customers = 50000;
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 1; i <= customers; i++) {
            String customerId = "Customer-" + i;
            executor.execute(() -> purchaseProduct(customerId, "IPHONE15"));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Final Stock: " + checkStock("IPHONE15"));
        System.out.println("Waiting List Size: " + waitingList.get("IPHONE15").size());
    }

    // Purchase product safely under concurrency
    public static void purchaseProduct(String customerId, String productId) {

        AtomicInteger stock = inventory.get(productId);

        if (stock == null) {
            System.out.println("Product not found.");
            return;
        }

        while (true) {
            int currentStock = stock.get();

            if (currentStock <= 0) {
                waitingList.get(productId).add(customerId);
                return;
            }

            // Atomic decrement to avoid overselling
            if (stock.compareAndSet(currentStock, currentStock - 1)) {
                System.out.println(customerId + " purchased " + productId +
                        " | Remaining Stock: " + (currentStock - 1));
                return;
            }
        }
    }

    // Instant stock availability check
    public static int checkStock(String productId) {
        AtomicInteger stock = inventory.get(productId);
        return stock != null ? stock.get() : 0;
    }
}