package uk.ac.ebi.pride.spectracluster.statistics;

import uk.ac.ebi.pride.spectracluster.statistics.report.ClusterStatisticsReporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Cluster statistics output code book generator
 * This writes out a code book which explains the meaning of each data column
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusterStatisticsCodeBookGenerator {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: <output code book file>");
            System.exit(1);
        }

        File outputFile = new File(args[0]);

        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));

        for (ClusterStatisticsReporter.HEADER header : ClusterStatisticsReporter.HEADER.values()) {
            writer.println(header.getHeader() + ":");
            writer.println(header.getDescription());
            writer.println();
        }

        writer.flush();
        writer.close();
    }
}
