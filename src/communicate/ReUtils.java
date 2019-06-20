package communicate;

import java.io.Closeable;

/**
 * @author Prongs
 */
class ReUtils {
    static void close(Closeable... targets) {
        for (Closeable target : targets) {
            try {
                if (null != target) {
                    target.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
