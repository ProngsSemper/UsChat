package communicate;

import java.io.Closeable;

public class ReUtils {
    public static void close(Closeable... targets) {
        for (Closeable target : targets) {
            try {
                if (null != target) {
                    target.close();
                }
            } catch (Exception e) {

            }
        }
    }
}
