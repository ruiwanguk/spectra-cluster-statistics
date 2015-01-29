package uk.ac.ebi.pride.spectracluster.statistics.predicate;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PeptideSequencePredicateTest {

    private PeptideSequencePredicate peptideSequencePredicate;

    @Before
    public void setUp() throws Exception {
        peptideSequencePredicate = new PeptideSequencePredicate();
    }

    @Test
    public void testLegalSequence() throws Exception {
        ClusterStatistics clusterStatistics = new ClusterStatistics();
        clusterStatistics.setPeptideSequenceWithHighestRatio("ABRNDCEQGHILJKMFPSTUVWXYZ");
        assertTrue(peptideSequencePredicate.apply(clusterStatistics));
    }

    @Test
    public void testAnotherLegalSequence() throws Exception {
        ClusterStatistics clusterStatistics = new ClusterStatistics();
        clusterStatistics.setPeptideSequenceWithHighestRatio("AAAAACEQGHILJKMFPSTUVWXYZ");
        assertTrue(peptideSequencePredicate.apply(clusterStatistics));
    }

    @Test
    public void testIllegalSequence() throws Exception {
        ClusterStatistics clusterStatistics = new ClusterStatistics();
        clusterStatistics.setPeptideSequenceWithHighestRatio("ABRNDCEQGHILJKMFPVersionWXYZ");
        assertFalse(peptideSequencePredicate.apply(clusterStatistics));

    }
}