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
    public static final int MINIMUM_NUMBER_OF_PROJECTS = 2;

    /**
     * Check whether a cluster is reliable or not
     * @param source    clustering file cluster
     * @return  true means reliable, false means unreliable
     */
    public static boolean isClusterReliable(ClusteringFileCluster source) {
        return source.getSpecCount() >= MINIMUM_RELIABLE_CLUSTER_SIZE &&
                source.getMaxRatio() >= MINIMUM_RELIABLE_RATIO &&
                getNumberOfProjectsOnPeptideWithHighRatio(source) >= MINIMUM_NUMBER_OF_PROJECTS;
    }

    /**
     * Calculate the average precursor charge
     *
     * @param source clustering file cluster
     * @return average precursor charge
     */
    public static int getAveragePrecursorCharge(ClusteringFileCluster source) {
        int sumOfCharge = 0;

        List<ISpectrumReference> spectrumReferences = source.getSpectrumReferences();
        for (ISpectrumReference spectrumReference : spectrumReferences) {
            sumOfCharge += spectrumReference.getCharge();
        }

        return sumOfCharge / spectrumReferences.size();
    }


    /**
     * Calculate the average precursor charge for peptides with highest ratio
     *
     * @param source clustering file cluster
     * @return average precursor charge
     */
    public static int getAveragePrecursorChargeForHighRatioPeptide(ClusteringFileCluster source) {
        int sumOfCharge = 0;
        int spectrumCount = 0;
        String maxSequence = source.getMaxSequence();

        List<ISpectrumReference> spectrumReferences = source.getSpectrumReferences();
        for (ISpectrumReference spectrumReference : spectrumReferences) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                sumOfCharge += spectrumReference.getCharge();
                spectrumCount++;
            }
        }

        return sumOfCharge / spectrumCount;
    }

    /**
     * Calculate the average precursor m/z for peptides with highest ratio
     *
     * @param source clustering file cluster
     * @return average precursor charge
     */
    public static float getAveragePrecursorMzForHighRatioPeptide(ClusteringFileCluster source) {
        float sumOfMz = 0f;
        int spectrumCount = 0;
        String maxSequence = source.getMaxSequence();

        List<ISpectrumReference> spectrumReferences = source.getSpectrumReferences();
        for (ISpectrumReference spectrumReference : spectrumReferences) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                sumOfMz += spectrumReference.getPrecursorMz();
                spectrumCount++;
            }
        }

        return sumOfMz / spectrumCount;
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
            boolean match = containMaxSequence(maxSequence, spectrumReference);

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
     * Get the maximum precursor charge
     *
     * @param source clustering file cluster
     * @return maximum precursor charge
     */
    public static int getMaxPrecursorCharge(ClusteringFileCluster source) {
        int maxCharge = Integer.MIN_VALUE;

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            int charge = spectrumReference.getCharge();
            if (charge > maxCharge) {
                maxCharge = charge;
            }
        }

        return maxCharge;
    }

    /**
     * Get the minimum precursor charge
     *
     * @param source clustering file cluster
     * @return minimum precursor charge
     */
    public static int getMinPrecursorCharge(ClusteringFileCluster source) {
        int minCharge = Integer.MAX_VALUE;

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            int charge = spectrumReference.getCharge();
            if (charge < minCharge) {
                minCharge = charge;
            }
        }

        return minCharge;
    }

    /**
     * Get the maximum precursor m/z
     *
     * @param source clustering file cluster
     * @return maximum precursor m/z
     */
    public static float getMaxPrecursorMz(ClusteringFileCluster source) {
        float maxMz = Float.MIN_VALUE;

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            float mz = spectrumReference.getPrecursorMz();
            if (mz > maxMz) {
                maxMz = mz;
            }
        }

        return maxMz;
    }

    /**
     * Get the minimum precursor m/z
     *
     * @param source clustering file cluster
     * @return minimum precursor m/z
     */
    public static float getMinPrecursorMz(ClusteringFileCluster source) {
        float minMz = Float.MAX_VALUE;

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            float mz = spectrumReference.getPrecursorMz();
            if (mz < minMz) {
                minMz = mz;
            }
        }

        return minMz;
    }

    /**
     * Get the maximum precursor charge for peptide with highest ratio
     *
     * @param source clustering file cluster
     * @return maximum precursor charge
     */
    public static int getMaxPrecursorChargeForHighRatioPeptide(ClusteringFileCluster source) {
        int maxCharge = Integer.MIN_VALUE;
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                int charge = spectrumReference.getCharge();
                if (charge > maxCharge) {
                    maxCharge = charge;
                }
            }
        }

        return maxCharge;
    }

    /**
     * Get the minimum precursor charge for peptide with highest ratio
     *
     * @param source clustering file cluster
     * @return minimum precursor charge
     */
    public static int getMinPrecursorChargeForHighRatioPeptide(ClusteringFileCluster source) {
        int minCharge = Integer.MAX_VALUE;
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                int charge = spectrumReference.getCharge();
                if (charge < minCharge) {
                    minCharge = charge;
                }
            }
        }

        return minCharge;
    }


    /**
     * Get the maximum precursor m/z for peptide with the highest ratio
     *
     * @param source clustering file cluster
     * @return maximum precursor m/z
     */
    public static float getMaxPrecursorMzForHighRatioPeptide(ClusteringFileCluster source) {
        float maxMz = Float.MIN_VALUE;
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                float mz = spectrumReference.getPrecursorMz();
                if (mz > maxMz) {
                    maxMz = mz;
                }
            }
        }

        return maxMz;
    }

    /**
     * Get the minimum precursor m/z for peptide with the highest ratio
     *
     * @param source clustering file cluster
     * @return minimum precursor m/z
     */
    public static float getMinPrecursorMzForHighRatioPeptide(ClusteringFileCluster source) {
        float minMz = Float.MAX_VALUE;
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                float mz = spectrumReference.getPrecursorMz();
                if (mz < minMz) {
                    minMz = mz;
                }
            }
        }

        return minMz;
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
     *
     * @param source clustering file cluster
     * @return species
     */
    public static Set<String> getSpecies(ClusteringFileCluster source) {
        Set<String> species = new HashSet<>();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            String specs = spectrumReference.getSpecies();
            if (specs != null) {
                String[] specsParts = specs.split(",");
                species.addAll(Arrays.asList(specsParts));
            }
        }

        return species;
    }

    /**
     * Get the number of species on peptide sequence with highest ratio
     *
     * @param source clustering file cluster
     * @return number of species
     */
    public static int getNumberOfSpeciesOnPeptideWithHighRatio(ClusteringFileCluster source) {
        return getSpeciesOnPeptideWithHighRatio(source).size();
    }

    /**
     * Get species on peptide with highest ratio
     *
     * @param source clustering file cluster
     * @return a set of species
     */
    public static Set<String> getSpeciesOnPeptideWithHighRatio(ClusteringFileCluster source) {
        Set<String> species = new HashSet<>();
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                String specs = spectrumReference.getSpecies();
                if (specs != null) {
                    String[] specsParts = specs.split(",");
                    species.addAll(Arrays.asList(specsParts));
                }
            }
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
     * <p/>
     * todo: this information should be explicitly provided in the cluster rather than inferred
     *
     * @param source clustering file cluster
     * @return a set of project accessions
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
     * Get the number of projects within a cluster
     *
     * @param source clustering file cluster
     * @return number of projects
     */
    public static int getNumberOfProjectsOnPeptideWithHighRatio(ClusteringFileCluster source) {
        return getProjectsOnPeptideWithHighRatio(source).size();
    }


    /**
     * Get projects within a cluster
     * <p/>
     * todo: this information should be explicitly provided in the cluster rather than inferred
     *
     * @param source clustering file cluster
     * @return a set of project accessions
     */
    public static Set<String> getProjectsOnPeptideWithHighRatio(ClusteringFileCluster source) {
        Set<String> projects = new HashSet<>();
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                String spectrumId = spectrumReference.getSpectrumId();
                String[] spectrumIdParts = spectrumId.split(";");
                projects.add(spectrumIdParts[0]);
            }
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

    /**
     * Get spectrum ids within a cluster on the peptide with highest ratio
     *
     * @param source clustering file cluster
     * @return a set of spectrum ids
     */
    public static Set<String> getSpectrumIdsOnPeptideWithHighRatio(ClusteringFileCluster source) {
        Set<String> spectrumIds = new HashSet<>();
        String maxSequence = source.getMaxSequence();

        for (ISpectrumReference spectrumReference : source.getSpectrumReferences()) {
            boolean match = containMaxSequence(maxSequence, spectrumReference);

            if (match) {
                String spectrumId = spectrumReference.getSpectrumId();
                spectrumIds.add(spectrumId);
            }
        }

        return spectrumIds;
    }


    /**
     * Check whether a spectrum identification contains the peptide with highest ratio
     *
     * @param maxSequence       peptide with highest ratio
     * @param spectrumReference spectrum
     * @return true means contains the peptide with highest ratio, false otherwise
     */
    private static boolean containMaxSequence(String maxSequence, ISpectrumReference spectrumReference) {
        boolean match = false;
        List<IPeptideSpectrumMatch> psms = spectrumReference.getPSMs();
        for (IPeptideSpectrumMatch psm : psms) {
            if (psm.getSequence().equals(maxSequence)) {
                match = true;
                break;
            }
        }
        return match;
    }

    /**
     * Sort a map by Values
     * @param map The map to be sorted
     * @param <K>
     * @param <V>
     * @return
     */

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 0;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

}
