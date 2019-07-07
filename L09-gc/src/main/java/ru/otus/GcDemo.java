package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tully.
 * <p>
 * Updated by Sergey
 */
/*
О формате логов
http://openjdk.java.net/jeps/158
-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
    iOld: 111
    i:2405
    time:450
    ---without label1
    iOld: 109
    i:2552
    time:451

2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
    iOld: 200
    i:2597
    time:670
    ---without label1
    iOld: 200
    i:2597
    time:493


3)
    -XX:MaxGCPauseMillis=10, time: 91 sec
    iOld: 333
    i:5998
    time:527
     ---without label1
    iOld: 333
    i:5994
    time:594


4)
  -Xms2048m
  -Xmx2048m
    time: 81 sec
    iOld: 30
    i:1360
    time:621
    ---without label1
    iOld: 29
    i:1358
    time:787

5)
-Xms5120m
-Xmx5120m
    time: 80 sec
    iOld: 0
i:555
time:718
---without label1
iOld: 0
i:555
time:611


5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)
    iOld: 0
    i:138
    time:1439
    ---without label1
    не дождалась-  время между итерациями от 20000ms

*/

public class GcDemo {
    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        int size = 5 * 1000 * 1000;
        int loopCounter = 1000;
        //int loopCounter = 100000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");

        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
        mbean.run();

        System.out.println("time:" + (System.currentTimeMillis() - beginTime)/1000);
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        AtomicInteger i = new AtomicInteger();
        AtomicInteger iOld = new AtomicInteger();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause  = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    if (gcName.equals("G1 Old Generation"))
                        iOld.getAndIncrement();
                    System.out.println("iOld: " +iOld);

                }
                i.getAndIncrement();
                System.out.println("i:" + i.get());
            };
            emitter.addNotificationListener(listener, null, null);

        }
    }

}