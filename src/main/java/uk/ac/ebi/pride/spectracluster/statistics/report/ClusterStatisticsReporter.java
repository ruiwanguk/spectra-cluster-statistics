package uk.ac.ebi.pride.spectracluster.statistics.report;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;

/**
 * Report the statistics of single cluster
 *
 * @author Rui Wang
 * @version $Id$
 */
@ThreadSafe
public class ClusterStatisticsReporter implements IStatisticsReporter<ClusterStatistics> {

    @GuardedBy("this")
    private final Appendable output;

    public ClusterStatisticsReporter(Appendable output) {
        this.output = output;
    }

    @Override
    public synchronized void report(ClusterStatistics stats) {
        // cluster id
        // todo: cluster id is not provided yet

        // average precursor m/z

        // average precursor charge

        // precursor m/z range

        // precursor m/z range on peptide with highest ratio

        // multiple high ranking peptide sequences

        // Number of spectra

        // Number of assays
        // todo: assay accessions not provided yet

        // Number of projects

        // Number of distinct peptide sequences

        // Number of PSMs

        // Number of species

        // Number of species on peptide with highest ratio

        // highest ratio

        // peptide sequence with highest ratio
    }
}
