package uk.ac.ebi.pride.spectracluster.statistics.listener;

import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.IClusterSourceListener;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ClusteringFileCluster;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ICluster;
import uk.ac.ebi.pride.spectracluster.statistics.collect.ClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.collect.OverallClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.report.ClusterStatisticsReporter;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;

/**
 * Listener for incoming clusters
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusterSourceListener implements IClusterSourceListener {

    private final ClusterStatisticsCollector clusterStatisticsCollector;
    private final OverallClusterStatisticsCollector overallClusterStatisticsCollector;
    private final ClusterStatisticsReporter clusterStatisticsReporter;

    public ClusterSourceListener(ClusterStatisticsCollector clusterStatisticsCollector,
                                 OverallClusterStatisticsCollector overallClusterStatisticsCollector,
                                 ClusterStatisticsReporter clusterStatisticsReporter) {

        this.clusterStatisticsCollector = clusterStatisticsCollector;
        this.overallClusterStatisticsCollector = overallClusterStatisticsCollector;
        this.clusterStatisticsReporter = clusterStatisticsReporter;
    }

    @Override
    public void onNewClusterRead(ICluster newCluster) {
        ClusterStatistics clusterStatistics = clusterStatisticsCollector.collect((ClusteringFileCluster) newCluster);
        overallClusterStatisticsCollector.collect((ClusteringFileCluster) newCluster);

        clusterStatisticsReporter.report(clusterStatistics);
    }
}
