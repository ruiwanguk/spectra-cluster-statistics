package uk.ac.ebi.pride.spectracluster.statistics.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Reporter for writing the headers for cluster statistics report
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusterStatisticsHeaderReporter implements IStatisticsReporter<Void> {
    public static final Logger logger = LoggerFactory.getLogger(ClusterStatisticsHeaderReporter.class);

    private final Appendable output;

    public ClusterStatisticsHeaderReporter(Appendable output) {
        this.output = output;
    }

    @Override
    public void report(Void stats) {
        StringBuilder headers = new StringBuilder();

        for (ClusterStatisticsReporter.HEADER header : ClusterStatisticsReporter.HEADER.values()) {
            headers.append(header.getHeader()).append("\t");
        }

        headers.delete(headers.length() - 1, headers.length());

        try {
            String headerStr = headers.toString();
            output.append(headerStr).append("\n");
        } catch (IOException e) {
            String msg = "Failed to write cluster statistics headers";
            logger.error(msg, e);
            throw new IllegalStateException(msg, e);
        }
    }
}
