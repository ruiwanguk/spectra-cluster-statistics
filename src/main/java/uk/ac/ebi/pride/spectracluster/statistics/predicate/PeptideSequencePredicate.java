package uk.ac.ebi.pride.spectracluster.statistics.predicate;

import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.util.predicate.IPredicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * check whether the peptide with highest ratio
 *
 * @author Rui Wang
 * @version $Id$
 */
public class PeptideSequencePredicate implements IPredicate<ClusterStatistics> {

    private static Pattern aminoAcidPattern = Pattern.compile("[ABRNDCEQGHILJKMFPSTUVWXYZ]+");

    @Override
    public boolean apply(ClusterStatistics stats) {
        String maxSequence = stats.getPeptideSequenceWithHighestRatio();

        Matcher matcher = aminoAcidPattern.matcher(maxSequence);

        return matcher.matches();
    }
}
