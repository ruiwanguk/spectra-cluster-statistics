package uk.ac.ebi.pride.spectracluster.statistics.listener;

import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.IClusterSourceListener;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ClusteringFileCluster;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ICluster;
import uk.ac.ebi.pride.spectracluster.statistics.collect.ClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.collect.OverallClusterStatisticsCollector;
import uk.ac.ebi.pride.spectracluster.statistics.report.ClusterStatisticsReporter;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.util.function.IFunction;
import uk.ac.ebi.pride.spectracluster.util.predicate.IPredicate;

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
    private final IPredicate<ClusterStatistics> clusterFilter;
    private final IFunction<ClusterStatistics, ClusterStatistics> clusterFunction;

    public ClusterSourceListener(ClusterStatisticsCollector clusterStatisticsCollector,
                                 OverallClusterStatisticsCollector overallClusterStatisticsCollector,
                                 ClusterStatisticsReporter clusterStatisticsReporter,
                                 IPredicate<ClusterStatistics> clusterFilter,
                                 IFunction<ClusterStatistics, ClusterStatistics> clusterFunction) {

        this.clusterStatisticsCollector = clusterStatisticsCollector;
        this.overallClusterStatisticsCollector = overallClusterStatisticsCollector;
        this.clusterStatisticsReporter = clusterStatisticsReporter;
        this.clusterFilter = clusterFilter;
        this.clusterFunction = clusterFunction;
    }

    @Override
    public void onNewClusterRead(ICluster newCluster) {
        ClusterStatistics clusterStatistics = clusterStatisticsCollector.collect((ClusteringFileCluster) newCluster);

        if (clusterFilter.apply(clusterStatistics)) {

            ClusterStatistics appliedClusterStatistics = clusterFunction.apply(clusterStatistics);

            // update overall statistics
            overallClusterStatisticsCollector.collect((ClusteringFileCluster) newCluster);

            // report statistics for a single cluster
            clusterStatisticsReporter.report(appliedClusterStatistics);
        }
    }
}
