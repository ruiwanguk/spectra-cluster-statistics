package uk.ac.ebi.pride.spectracluster.statistics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.spectracluster.statistics.collect.ClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.collect.OverallClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.function.IllegalSpeciesFunction;
import uk.ac.ebi.pride.spectracluster.statistics.io.ClusteringFileFilter;
import uk.ac.ebi.pride.spectracluster.statistics.predicate.NumberOfSpectraPredicate;
import uk.ac.ebi.pride.spectracluster.statistics.predicate.PeptideSequenceLengthPredicate;
import uk.ac.ebi.pride.spectracluster.statistics.predicate.PeptideSequencePredicate;
import uk.ac.ebi.pride.spectracluster.statistics.report.ClusterStatisticsHeaderReporter;
import uk.ac.ebi.pride.spectracluster.statistics.report.ClusterStatisticsReporter;
import uk.ac.ebi.pride.spectracluster.statistics.report.OverallStatisticsReporter;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.statistics.stat.OverallClusterStatistics;
import uk.ac.ebi.pride.spectracluster.util.predicate.IPredicate;
import uk.ac.ebi.pride.spectracluster.util.predicate.Predicates;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ClusterStatisticsRunnerTest {

    File inputDirectory = null;
    public static final Logger logger = LoggerFactory.getLogger(ClusterStatisticsRunnerTest.class);

    @Before
    public void setup(){
        URL resource = ClusterStatisticsRunnerTest.class.getClassLoader().getResource("folder-analysis/");

        try {
            inputDirectory = new File(resource.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

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
            overallStatisticWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File("output-text-cluster.csv"))));
            clusterStatisticWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File("output-text-cluster.stasts.csv"))));


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

            // short peptide sequence filter
            PeptideSequenceLengthPredicate peptideSequenceLengthPredicate = new PeptideSequenceLengthPredicate();

            IPredicate<ClusterStatistics> predicate = Predicates.and(peptideSequencePredicate, peptideSequenceLengthPredicate, numberOfSpectraPredicate);

            // function to clean species
            IllegalSpeciesFunction illegalSpeciesFunction = new IllegalSpeciesFunction();

            ClusterStatisticsRunner clusterStatisticsRunner = new ClusterStatisticsRunner(clusterStatisticsCollector,
                    overallClusterStatisticsCollector, clusterStatisticsReporter, overallStatisticsReporter, predicate, illegalSpeciesFunction);

            clusterStatisticsRunner.run(Arrays.asList(clusteringFiles));

            overallStatisticWriter.flush();
            clusterStatisticWriter.flush();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        finally {
            if (overallStatisticWriter != null)
                overallStatisticWriter.close();

            if (clusterStatisticWriter != null)
                clusterStatisticWriter.close();
        }
        logger.info("Statistics has been collected");
    }

    @Test
    public void checkHeaders(){

        String csvFile = "output-text-cluster.stasts.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            String[] headers = line.split(cvsSplitBy);
            assertTrue(headers.length == 37);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");
    }

    @After
    public void onClose(){
        File fileExt = new File("output-text-cluster.csv");
        if(fileExt.exists())
            fileExt.delete();

        fileExt = new File("output-text-cluster.stasts.csv");
        if(fileExt.exists())
            fileExt.delete();

    }

}