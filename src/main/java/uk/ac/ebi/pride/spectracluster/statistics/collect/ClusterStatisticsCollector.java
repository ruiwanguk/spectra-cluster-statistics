package uk.ac.ebi.pride.spectracluster.statistics.collect;

import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ClusteringFileCluster;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.SequenceCount;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.statistics.util.ClusteringFileClusterUtils;

import java.util.List;

/**
 * Collect statistics for a given cluster
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusterStatisticsCollector implements IStatisticsCollector<ClusteringFileCluster, ClusterStatistics> {
    @Override
    public ClusterStatistics collect(ClusteringFileCluster source) {
        ClusterStatistics clusterStatistics = new ClusterStatistics();

        // cluster id
        // todo: cluster id is not provided yet

        // average precursor m/z
        clusterStatistics.setAveragePrecursorMz(source.getAvPrecursorMz());

        // average precursor charge
        float averagePrecursorCharge = ClusteringFileClusterUtils.getAveragePrecursorCharge(source);
        clusterStatistics.setAveragePrecursorCharge(averagePrecursorCharge);

        // precursor m/z range
        clusterStatistics.setPrecursorMzRange(source.getSpectrumPrecursorMzRange());

        // precursor m/z range on peptide with highest ratio
        float precursorMzRangeForHighRatioPeptide = ClusteringFileClusterUtils.getPrecursorMzRangeForHighRatioPeptide(source);
        clusterStatistics.setPrecursorMzRangeOnPeptideWithHighestRatio(precursorMzRangeForHighRatioPeptide);

        // multiple high ranking peptide sequences
        boolean hasMultipleHighRankingPeptides = ClusteringFileClusterUtils.hasMultipleHighRankingPeptides(source);
        clusterStatistics.setMultipleHighRankingPeptideSequences(hasMultipleHighRankingPeptides);

        // Number of spectra
        int specCount = source.getSpecCount();
        clusterStatistics.setNumberOfSpectra(specCount);

        // Number of assays
        // todo: assay accessions not provided yet

        // Number of projects
        int numberOfProject = ClusteringFileClusterUtils.getNumberOfProjects(source);
        clusterStatistics.setNumberOfProjects(numberOfProject);

        // Number of distinct peptide sequences
        List<SequenceCount> sequenceCounts = source.getSequenceCounts();
        clusterStatistics.setNumberOfDistinctPeptideSequences(sequenceCounts.size());

        // Number of PSMs
        int numberOfPSMs = source.getPsmCount();
        clusterStatistics.setNumberOfPsms(numberOfPSMs);

        // Number of species
        int numberOfSpecies = ClusteringFileClusterUtils.getNumberOfSpecies(source);
        clusterStatistics.setNumberOfSpecies(numberOfSpecies);

        // Number of species on peptide with highest ratio
        int numberOfSpeciesWithHighestRatio = ClusteringFileClusterUtils.getNumberOfSpeciesOnPeptideWithHighRatio(source);
        clusterStatistics.setNumberOfSpeciesOnPeptideWithHighestRatio(numberOfSpeciesWithHighestRatio);

        // highest ratio
        float maxRatio = source.getMaxRatio();
        clusterStatistics.setHighestRatio(maxRatio);

        // peptide sequence with highest ratio
        String maxSequence = source.getMaxSequence();
        clusterStatistics.setPeptideSequenceWithHighestRatio(maxSequence);

        return clusterStatistics;
    }


}
