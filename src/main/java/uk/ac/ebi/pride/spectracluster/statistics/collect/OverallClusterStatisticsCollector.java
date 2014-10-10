package uk.ac.ebi.pride.spectracluster.statistics.collect;

import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ClusteringFileCluster;
import uk.ac.ebi.pride.spectracluster.statistics.stat.OverallClusterStatistics;
import uk.ac.ebi.pride.spectracluster.statistics.util.ClusteringFileClusterUtils;

import java.util.Set;

/**
 * Collecting overall cluster statistics
 *
 * @author Rui Wang
 * @version $Id$
 */
public class OverallClusterStatisticsCollector implements IStatisticsCollector<ClusteringFileCluster, OverallClusterStatistics> {



    private final OverallClusterStatistics overallClusterStatistics;

    public OverallClusterStatisticsCollector(OverallClusterStatistics overallClusterStatistics) {
        this.overallClusterStatistics = overallClusterStatistics;
    }

    @Override
    public OverallClusterStatistics collect(ClusteringFileCluster source) {
        // cluster count
        overallClusterStatistics.incrementNumberOfClusters();

        // get spectrum count
        int specCount = source.getSpecCount();

        // species
        Set<String> species = ClusteringFileClusterUtils.getSpecies(source);
        for (String specy : species) {
            overallClusterStatistics.addSpecies(specy);
        }

        // spectrum
        Set<String>  spectrumIds = ClusteringFileClusterUtils.getSpectrumIds(source);
        for (String spectrumId : spectrumIds) {
            overallClusterStatistics.addSpectra(spectrumId);
        }

        // assays
        // todo: this is no way to determine assay accession yet

        // projects
        Set<String> projects = ClusteringFileClusterUtils.getProjects(source);
        for (String project : projects) {
            overallClusterStatistics.addProject(project);
        }


        if (specCount > 1) {
            // multi spectra cluster count
            overallClusterStatistics.incrementNumberOfMultiSpectraClusters();

            // reliable cluster count
            if (ClusteringFileClusterUtils.isClusterReliable(source)) {
                overallClusterStatistics.incrementNumberOfReliableClusters();

                // cluster species count
                if (species.size() == 1) {
                    overallClusterStatistics.incrementNumberOfSingleSpeciesMultiSpectraClusters();
                } else if (species.size() == 2) {
                    overallClusterStatistics.incrementNumberOfTwoSpeciesMultiSpectraClusters();
                }
            }

            // clustered spectra
            for (String spectrumId : spectrumIds) {
                overallClusterStatistics.addClusteredSpectra(spectrumId);
            }
        }

        return overallClusterStatistics;
    }

    public OverallClusterStatistics getOverallClusterStatistics() {
        return overallClusterStatistics;
    }
}
