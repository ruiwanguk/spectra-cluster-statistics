package uk.ac.ebi.pride.spectracluster.statistics.util;

import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ClusteringFileCluster;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.IPeptideSpectrumMatch;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ISpectrumReference;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.SequenceCount;

import java.util.*;

/**
 * Utility methods for retrieving information from ClusteringFileCluster
 *
 * @author Rui Wang
 * @version $Id$
 */
public final class ClusteringFileClusterUtils {

    public static final int MINIMUM_RELIABLE_CLUSTER_SIZE = 10;
    public static final float MINIMUM_RELIABLE_RATIO = 0.7f;

    public static boolean isClusterReliable(ClusteringFileCluster source) {
        return source.getSpecCount() >= MINIMUM_RELIABLE_CLUSTER_SIZE && source.getMaxRatio() >= MINIMUM_RELIABLE_RATIO;
    }

    /**
     * calculate the average precursor charge
     *
     * @param source clustering file cluster
     * @return average precursor charge
     */
    public static float getAveragePrecursorCharge(ClusteringFileCluster source) {
        float sumOfCharge = 0.0f;

        List<ISpectrumReference> spectrumReferences = source.getSpectrumReferences();
        for (ISpectrumReference spectrumReference : spectrumReferences) {
            sumOfCharge += spectrumReference.getCharge();
        }

        return sumOfCharge / spectrumReferences.size();
    }

    /**
     * Calculate the precursor m/z range for all the spectra identify high ratio peptides
     *
     * @param source clustering file cluster
     * @return precursor m/z range
     */
    public static float getPrecursorMzRangeForHighRatioPeptide(ClusteringFileCluster source) {
        float maxMz = 0.0f;
        float minMz = 100000.0f;
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            boolean match = false;
            List<IPeptideSpectrumMatch> psms = spectrumReference.getPSMs();
            for (IPeptideSpectrumMatch psm : psms) {
                if (psm.getSequence().equals(maxSequence)) {
                    match = true;
                    break;
                }
            }

            if (match) {
                float precursorMz = spectrumReference.getPrecursorMz();
                if (precursorMz > maxMz) {
                    maxMz = precursorMz;
                }

                if (precursorMz < minMz) {
                    minMz = precursorMz;
                }
            }
        }

        return maxMz - minMz;
    }

    /**
     * Check whether there are multiple peptides sequences have ranking 1
     *
     * @param source clustering file cluster
     * @return true means the cluster has multiple peptides sequences with ranking 1
     * false means otherwise
     */
    public static boolean hasMultipleHighRankingPeptides(ClusteringFileCluster source) {
        // make a copy to avoid concurrent sorting by multiple threads
        List<SequenceCount> sequenceCounts = new ArrayList<>(source.getSequenceCounts());
        if (sequenceCounts.size() <= 1) {
            return false;
        }

        Collections.sort(sequenceCounts, new Comparator<SequenceCount>() {
            @Override
            public int compare(SequenceCount o1, SequenceCount o2) {
                return Integer.compare(o1.getCount(), o2.getCount());
            }
        });

        return sequenceCounts.get(0).getCount() == sequenceCounts.get(1).getCount();
    }

    /**
     * Get the number of species on peptide sequence with highest ratio
     *
     * @param source clustering file cluster
     * @return number of species
     */
    public static int getNumberOfSpeciesOnPeptideWithHighRatio(ClusteringFileCluster source) {
        Set<String> species = new HashSet<>();
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            List<IPeptideSpectrumMatch> psms = spectrumReference.getPSMs();
            boolean match = false;
            for (IPeptideSpectrumMatch psm : psms) {
                if (psm.getSequence().equals(maxSequence)) {
                    match = true;
                    break;
                }
            }

            if (match) {
                String specs = spectrumReference.getSpecies();
                String[] specsParts = specs.split(",");
                species.addAll(Arrays.asList(specsParts));
            }
        }

        return species.size();

    }


    /**
     * Get the number of species
     *
     * @param source clustering file cluster
     * @return number of species
     */
    public static int getNumberOfSpecies(ClusteringFileCluster source) {
        return getSpecies(source).size();
    }

    /**
     * Get all the species within a cluster
     * @param source    clustering file cluster
     * @return  species
     */
    public static Set<String> getSpecies(ClusteringFileCluster source) {
        Set<String> species = new HashSet<>();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            String specs = spectrumReference.getSpecies();
            String[] specsParts = specs.split(",");
            species.addAll(Arrays.asList(specsParts));
        }

        return species;
    }

    /**
     * Get the number of projects within a cluster
     *
     * @param source clustering file cluster
     * @return number of projects
     */
    public static int getNumberOfProjects(ClusteringFileCluster source) {
        return getProjects(source).size();
    }

    /**
     * Get projects within a cluster
     *
     * todo: this information should be explicitly provided in the cluster rather than inferred
     *
     * @param source    clustering file cluster
     * @return  a set of project accessions
     */
    public static Set<String> getProjects(ClusteringFileCluster source) {
        Set<String> projects = new HashSet<>();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            String spectrumId = spectrumReference.getSpectrumId();
            String[] spectrumIdParts = spectrumId.split(";");
            projects.add(spectrumIdParts[0]);
        }

        return projects;
    }


    /**
     * Get spectrum ids within a cluster
     *
     * @param source clustering file cluster
     * @return a set of spectrum ids
     */
    public static Set<String> getSpectrumIds(ClusteringFileCluster source) {
        Set<String> spectrumIds = new HashSet<>();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            String spectrumId = spectrumReference.getSpectrumId();
            spectrumIds.add(spectrumId);
        }

        return spectrumIds;
    }
}
