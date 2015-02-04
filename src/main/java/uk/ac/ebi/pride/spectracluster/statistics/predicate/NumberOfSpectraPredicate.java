package uk.ac.ebi.pride.spectracluster.statistics.predicate;

import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.util.predicate.IPredicate;

/**
 * Predicate for filtering clusters that less than given number of spectra
 *
 * @author Rui Wang
 * @version $Id$
 */
public class NumberOfSpectraPredicate implements IPredicate<ClusterStatistics>{

    private final int minOfSpectra;

    public NumberOfSpectraPredicate(int minOfSpectra) {
        this.minOfSpectra = minOfSpectra;
    }

    @Override
    public boolean apply(ClusterStatistics cluster) {
        return cluster.getNumberOfSpectra() > minOfSpectra;
    }
}
