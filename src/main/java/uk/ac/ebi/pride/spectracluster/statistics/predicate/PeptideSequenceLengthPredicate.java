package uk.ac.ebi.pride.spectracluster.statistics.predicate;

import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.util.predicate.IPredicate;

/**
 * Check whether the length of the peptide sequence is >= 6 amino acids
 *
 * @author Rui Wang
 * @version $Id$
 */
public class PeptideSequenceLengthPredicate implements IPredicate<ClusterStatistics> {

    @Override
    public boolean apply(ClusterStatistics cluster) {
        return cluster.getPeptideSequenceWithHighestRatio().length() > 5;
    }
}
