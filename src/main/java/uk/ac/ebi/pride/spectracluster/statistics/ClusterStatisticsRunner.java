package uk.ac.ebi.pride.spectracluster.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.IClusterSourceListener;
import uk.ac.ebi.pride.spectracluster.statistics.collect.ClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.collect.OverallClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.io.ClusteringFileFilter;
import uk.ac.ebi.pride.spectracluster.statistics.io.ClusteringFileParsingExecutable;
import uk.ac.ebi.pride.spectracluster.statistics.listener.ClusterSourceListener;
import uk.ac.ebi.pride.spectracluster.statistics.predicate.NumberOfSpectraPredicate;
import uk.ac.ebi.pride.spectracluster.statistics.predicate.PeptideSequencePredicate;
import uk.ac.ebi.pride.spectracluster.statistics.report.ClusterStatisticsHeaderReporter;
import uk.ac.ebi.pride.spectracluster.statistics.report.ClusterStatisticsReporter;
import uk.ac.ebi.pride.spectracluster.statistics.report.OverallStatisticsReporter;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.statistics.stat.OverallClusterStatistics;
import uk.ac.ebi.pride.spectracluster.util.predicate.IPredicate;
import uk.ac.ebi.pride.spectracluster.util.predicate.Predicates;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main class to call to collect cluster statistics
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusterStatisticsRunner {

    public static final Logger logger = LoggerFactory.getLogger(ClusterStatisticsRunner.class);

    // fixed sized thread pool that run 10 threads at a time
    private final ExecutorService threadPool = Executors.newFixedThreadPool(20);

    private final OverallClusterStatisticsCollector overallClusterStatisticsCollector;
    private final OverallStatisticsReporter overallStatisticsReporter;
    private final ClusterSourceListener clusterSourceListener;

    public ClusterStatisticsRunner(ClusterStatisticsCollector clusterStatisticsCollector,
                                   OverallClusterStatisticsCollector overallClusterStatisticsCollector,
                                   ClusterStatisticsReporter clusterStatisticsReporter,
                                   OverallStatisticsReporter overallStatisticsReporter,
                                   IPredicate<ClusterStatistics> clusterFilter) {

        this.overallClusterStatisticsCollector = overallClusterStatisticsCollector;
        this.overallStatisticsReporter = overallStatisticsReporter;
        this.clusterSourceListener = new ClusterSourceListener(clusterStatisticsCollector,
                                                this.overallClusterStatisticsCollector, clusterStatisticsReporter, clusterFilter);
    }

    public void run(List<File> clusteringFiles) {
        CountDownLatch countDown = new CountDownLatch(clusteringFiles.size());
        submitTasks(clusteringFiles, countDown);

        try {
            countDown.await();
        } catch (InterruptedException e) {
            logger.warn("Program was interrupted", e);
        } finally {
            threadPool.shutdown();
        }

        OverallClusterStatistics overallClusterStatistics = overallClusterStatisticsCollector.getOverallClusterStatistics();
        overallStatisticsReporter.report(overallClusterStatistics);
    }

    private void submitTasks(List<File> clusteringFiles, CountDownLatch countDown) {
        for (File clusteringFile : clusteringFiles) {
            ClusteringFileParsingExecutable clusteringFileParsingExecutable =
                    new ClusteringFileParsingExecutable(clusteringFile, Arrays.asList((IClusterSourceListener) clusterSourceListener), countDown);
            threadPool.submit(clusteringFileParsingExecutable);
        }
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
        logger.info("Found {} clustering files", clusteringFiles.length);

        // prepare for output
        PrintWriter overallStatisticWriter = null;
        PrintWriter clusterStatisticWriter = null;

        try {
            overallStatisticWriter = new PrintWriter(new BufferedWriter(new FileWriter(overallStatisticsOutputFile)));
            clusterStatisticWriter = new PrintWriter(new BufferedWriter(new FileWriter(clusterStatisticsOutputFile)));


            // statistics collector
            ClusterStatisticsCollector clusterStatisticsCollector = new ClusterStatisticsCollector();
            OverallClusterStatisticsCollector overallClusterStatisticsCollector = new OverallClusterStatisticsCollector(new OverallClusterStatistics());

            // statistics reporter
            ClusterStatisticsHeaderReporter clusterStatisticsHeaderReporter = new ClusterStatisticsHeaderReporter(clusterStatisticWriter);
            ClusterStatisticsReporter clusterStatisticsReporter = new ClusterStatisticsReporter(clusterStatisticWriter);
            OverallStatisticsReporter overallStatisticsReporter = new OverallStatisticsReporter(overallStatisticWriter);

            // write out header
            clusterStatisticsHeaderReporter.report(null);

            // add peptide sequence filter
            PeptideSequencePredicate peptideSequencePredicate = new PeptideSequencePredicate();

            // add number of spectra filter
            NumberOfSpectraPredicate numberOfSpectraPredicate = new NumberOfSpectraPredicate(10);

            IPredicate<ClusterStatistics> predicate = Predicates.and(peptideSequencePredicate, numberOfSpectraPredicate);

            ClusterStatisticsRunner clusterStatisticsRunner = new ClusterStatisticsRunner(clusterStatisticsCollector,
                    overallClusterStatisticsCollector, clusterStatisticsReporter, overallStatisticsReporter, predicate);

            clusterStatisticsRunner.run(Arrays.asList(clusteringFiles));

            overallStatisticWriter.flush();
            clusterStatisticWriter.flush();
        } finally {
            if (overallStatisticWriter != null)
                overallStatisticWriter.close();

            if (clusterStatisticWriter != null)
                clusterStatisticWriter.close();
        }

        logger.info("Statistics has been collected");
    }
}
