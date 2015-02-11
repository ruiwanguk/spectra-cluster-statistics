package uk.ac.ebi.pride.spectracluster.statistics.collect;

import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ClusteringFileCluster;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.SequenceCount;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.statistics.util.ClusteringFileClusterUtils;

import java.util.List;
import java.util.Set;

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
        clusterStatistics.setId(source.getId());

        // average precursor m/z
        clusterStatistics.setAveragePrecursorMz(source.getAvPrecursorMz());

        // average precursor m/z on peptides with highest ratio
        float averagePrecursorMzForHighRatioPeptide = ClusteringFileClusterUtils.getAveragePrecursorMzForHighRatioPeptide(source);
        clusterStatistics.setAveragePrecursorMzWithHighestRatio(averagePrecursorMzForHighRatioPeptide);

        // average precursor charge
        int averagePrecursorCharge = ClusteringFileClusterUtils.getAveragePrecursorCharge(source);
        clusterStatistics.setAveragePrecursorCharge(averagePrecursorCharge);

        // average precursor charge on peptides with highest ratio
        int averagePrecursorChargeForHighRatioPeptide = ClusteringFileClusterUtils.getAveragePrecursorChargeForHighRatioPeptide(source);
        clusterStatistics.setAveragePrecursorChargeWithHighestRatio(averagePrecursorChargeForHighRatioPeptide);

        // maximum precursor charge
        int maxPrecursorCharge = ClusteringFileClusterUtils.getMaxPrecursorCharge(source);
        clusterStatistics.setMaxPrecursorCharge(maxPrecursorCharge);

        // minimum precursor charge
        int minPrecursorCharge = ClusteringFileClusterUtils.getMinPrecursorCharge(source);
        clusterStatistics.setMinPrecursorCharge(minPrecursorCharge);

        // maximum precursor charge on peptides with highest ratio
        int maxPrecursorChargeForHighRatioPeptide = ClusteringFileClusterUtils.getMaxPrecursorChargeForHighRatioPeptide(source);
        clusterStatistics.setMaxPrecursorChargeWithHighestRatio(maxPrecursorChargeForHighRatioPeptide);

        // minimum precursor charge on peptides with highest ratio
        int minPrecursorChargeForHighRatioPeptide = ClusteringFileClusterUtils.getMinPrecursorChargeForHighRatioPeptide(source);
        clusterStatistics.setMinPrecursorChargeWithHighestRatio(minPrecursorChargeForHighRatioPeptide);

        // maximum precursor m/z
        float maxPrecursorMz = ClusteringFileClusterUtils.getMaxPrecursorMz(source);
        clusterStatistics.setMaxPrecursorMz(maxPrecursorMz);

        // minimum precursor m/z
        float minPrecursorMz = ClusteringFileClusterUtils.getMinPrecursorMz(source);
        clusterStatistics.setMinPrecursorMz(minPrecursorMz);

        // precursor m/z range
        clusterStatistics.setPrecursorMzRange(source.getSpectrumPrecursorMzRange());

        // maximum precursor charge on peptides with highest ratio
        float maxPrecursorMzForHighRatioPeptide = ClusteringFileClusterUtils.getMaxPrecursorMzForHighRatioPeptide(source);
        clusterStatistics.setMaxPrecursorMzWithHighestRatio(maxPrecursorMzForHighRatioPeptide);

        // minimum precursor charge on peptides with highest ratio
        float minPrecursorMzForHighRatioPeptide = ClusteringFileClusterUtils.getMinPrecursorMzForHighRatioPeptide(source);
        clusterStatistics.setMinPrecursorMzWithHighestRatio(minPrecursorMzForHighRatioPeptide);

        // precursor m/z range on peptide with highest ratio
        float precursorMzRangeForHighRatioPeptide = ClusteringFileClusterUtils.getPrecursorMzRangeForHighRatioPeptide(source);
        clusterStatistics.setPrecursorMzRangeOnPeptideWithHighestRatio(precursorMzRangeForHighRatioPeptide);

        // multiple high ranking peptide sequences
        boolean hasMultipleHighRankingPeptides = ClusteringFileClusterUtils.hasMultipleHighRankingPeptides(source);
        clusterStatistics.setMultipleHighRankingPeptideSequences(hasMultipleHighRankingPeptides);

        // Number of spectra
        int specCount = source.getSpecCount();
        clusterStatistics.setNumberOfSpectra(specCount);

        // projects
        Set<String> projects = ClusteringFileClusterUtils.getProjects(source);
        clusterStatistics.setProjects(projects);

        // projects on peptide with highest ratio
        Set<String> projectsOnPeptideWithHighRatio = ClusteringFileClusterUtils.getProjectsOnPeptideWithHighRatio(source);
        clusterStatistics.setProjectOnPeptideWithHighestRatio(projectsOnPeptideWithHighRatio);

        // Number of distinct peptide sequences
        List<SequenceCount> sequenceCounts = source.getSequenceCounts();
        clusterStatistics.setNumberOfDistinctPeptideSequences(sequenceCounts.size());

        // Number of PSMs
        int numberOfPSMs = source.getPsmCount();
        clusterStatistics.setNumberOfPsms(numberOfPSMs);

        // species
        Set<String> species = ClusteringFileClusterUtils.getSpecies(source);
        clusterStatistics.setSpeciesInTaxonomyId(species);

        // species on peptides with highest ratio
        Set<String> speciesForHighRatioPeptide = ClusteringFileClusterUtils.getSpeciesOnPeptideWithHighRatio(source);
        clusterStatistics.setSpeciesOnPeptideWithHighestRatioInTaxonomyId(speciesForHighRatioPeptide);

        // highest ratio
        float maxRatio = source.getMaxRatio();
        clusterStatistics.setHighestRatio(maxRatio);

        // peptide sequence with highest ratio
        String maxSequence = source.getMaxSequence();
        clusterStatistics.setPeptideSequenceWithHighestRatio(maxSequence);

        // file name
        clusterStatistics.setFileName(source.getFileName());

        return clusterStatistics;
    }
}
