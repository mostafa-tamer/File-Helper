public class Util {
    public static Double calculateTimeSeconds(Runnable task) {
        double start = System.currentTimeMillis();
        task.run();
        return (System.currentTimeMillis() - start) / 1_000;
    }
}
