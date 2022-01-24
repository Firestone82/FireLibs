package cz.devfire.firelibs.Shared.Utils;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class SystemUtils {
    private SystemUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     *
     * @return
     */
    public static double getProcessCpuLoad() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            Attribute att = (Attribute) mbs.getAttribute(ObjectName.getInstance("java.lang:type=OperatingSystem"), "ProcessCpuLoad");
            Double value = (Double) att.getValue();

            if (value.doubleValue() == -1.0D) {
                return 0.0D;
            }

            return (int) (value.doubleValue() * 1000.0D) / 10.0D;
        } catch (Exception e) {
            return 0.0D;
        }
    }

    /**
     *
     * @param folderPath
     * @param expirationInDays
     * @return
     */
    public static Integer cleanUpOldFiles(String folderPath, Integer expirationInDays) {
        return cleanUpOldFiles(folderPath,expirationInDays,".*");
    }

    /**
     *
     * @param folderPath
     * @param expirationInDays
     * @param regex
     * @return
     */
    public static Integer cleanUpOldFiles(String folderPath, Integer expirationInDays, String regex) {
        File targetDir = new File(folderPath);
        File[] files = targetDir.listFiles();
        Integer i = 0;

        for (File file : files) {
            if (file.getName().matches(regex)) {
                long time = TimeUnit.DAYS.toSeconds(expirationInDays);

                if ((file.lastModified() / 1000 + time) < (System.currentTimeMillis() / 1000)) {
                    file.delete();
                    i++;
                }
            }
        }

        return i;
    }
}
