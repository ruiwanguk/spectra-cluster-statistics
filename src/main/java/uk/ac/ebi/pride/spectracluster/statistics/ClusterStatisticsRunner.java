package uk.ac.ebi.pride.spectracluster.statistics;

import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.IClusterSourceListener;
import uk.ac.ebi.pride.spectracluster.statistics.collect.ClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.collect.OverallClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.io.ClusteringFileFilter;
import uk.ac.ebi.pride.spectracluster.statistics.io.ClusteringFileParsingExecutable;
import uk.ac.ebi.pride.spectracluster.statistics.listener.ClusterSourceListener;
import uk.ac.ebi.pride.spectracluster.statistics.report.ClusterStatisticsReporter;
import uk.ac.ebi.pride.spectracluster.statistics.report.OverallStatisticsReporter;
import uk.ac.ebi.pride.spectracluster.statistics.stat.OverallClusterStatistics;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Main class to call to collect cluster statistics
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusterStatisticsRunner {
    // fixed sized thread pool that run 10 threads at a time
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private final ClusterStatisticsCollector clusterStatisticsCollector;
    private final OverallClusterStatisticsCollector overallClusterStatisticsCollector;
    private final ClusterStatisticsReporter clusterStatisticsReporter;
    private final OverallStatisticsReporter overallStatisticsReporter;
    private final ClusterSourceListener clusterSourceListener;

    public ClusterStatisticsRunner(ClusterStatisticsCollector clusterStatisticsCollector,
                                   OverallClusterStatisticsCollector overallClusterStatisticsCollector,
                                   ClusterStatisticsReporter clusterStatisticsReporter,
                                   OverallStatisticsReporter overallStatisticsReporter) {

        this.clusterStatisticsCollector = clusterStatisticsCollector;
        this.overallClusterStatisticsCollector = overallClusterStatisticsCollector;
        this.clusterStatisticsReporter = clusterStatisticsReporter;
        this.overallStatisticsReporter = overallStatisticsReporter;
        this.clusterSourceListener = new ClusterSourceListener(clusterStatisticsCollector, overallClusterStatisticsCollector, clusterStatisticsReporter);
    }

    public void run(List<File> clusteringFiles) {
        List<Future> taskResults = submitTasks(clusteringFiles);

        try {
            for (;;) {
                // wait for one minute
                Thread.sleep(60000l);

                // remove all the completed tasks
                Iterator<Future> iterator = taskResults.iterator();
                while(iterator.hasNext()) {
                    if (iterator.next().isDone()) {
                        iterator.remove();
                    }
                }

                // check whether all the tasks have been completed
                if (taskResults.size() == 0) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Cluster statistic runner was interrupted", e);
        } finally {
            threadPool.shutdown();
        }

        OverallClusterStatistics overallClusterStatistics = overallClusterStatisticsCollector.getOverallClusterStatistics();
        overallStatisticsReporter.report(overallClusterStatistics);
    }

    private List<Future> submitTasks(List<File> clusteringFiles) {
        List<Future> taskResults = new ArrayList<>();

        for (File clusteringFile : clusteringFiles) {
            ClusteringFileParsingExecutable clusteringFileParsingExecutable =
                    new ClusteringFileParsingExecutable(clusteringFile, Arrays.asList((IClusterSourceListener) clusterSourceListener));
            Future<Void> task = threadPool.submit(clusteringFileParsingExecutable);
            taskResults.add(task);
        }

        return taskResults;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: <directory contains .clustering files> <output file for overall statistics> <output file for cluster statistics>");
            System.exit(1);
        }

        File inputDirectory = new File(args[0]);
        File overallStatisticsOutputFile = new File(args[1]);
        File clusterStatisticsOutputFile = new File(args[2]);

        if (!inputDirectory.exists() || !inputDirectory.isDirectory()) {
            System.err.println("Input directory must be an existing directory: " + inputDirectory.getAbsolutePath());
            System.exit(1);
        }

        // find all the .clustering files
        File[] clusteringFiles = inputDirectory.listFiles(new ClusteringFileFilter());



        // prepare for output
        PrintWriter overallStatisticWriter = new PrintWriter(new BufferedWriter(new FileWriter(overallStatisticsOutputFile)));
        PrintWriter clusterStatisticWriter = new PrintWriter(new BufferedWriter(new FileWriter(clusterStatisticsOutputFile)));

        // statistics collector
        ClusterStatisticsCollector clusterStatisticsCollector = new ClusterStatisticsCollector();
        OverallClusterStatisticsCollector overallClusterStatisticsCollector = new OverallClusterStatisticsCollector(new OverallClusterStatistics());

        // statistics reporter
        ClusterStatisticsReporter clusterStatisticsReporter = new ClusterStatisticsReporter(clusterStatisticWriter);
        OverallStatisticsReporter overallStatisticsReporter = new OverallStatisticsReporter(overallStatisticWriter);

        ClusterStatisticsRunner clusterStatisticsRunner = new ClusterStatisticsRunner(clusterStatisticsCollector,
                overallClusterStatisticsCollector, clusterStatisticsReporter, overallStatisticsReporter);

        clusterStatisticsRunner.run(Arrays.asList(clusteringFiles));
    }
}
