package uk.ac.ebi.pride.spectracluster.statistics.report;

import net.jcip.annotations.NotThreadSafe;
import uk.ac.ebi.pride.spectracluster.statistics.stat.OverallClusterStatistics;

import java.io.IOException;

/**
 * Report overall statistics
 *
 * @author Rui Wang
 * @version $Id$
 */
@NotThreadSafe
public class OverallStatisticsReporter implements IStatisticsReporter<OverallClusterStatistics> {
    private final Appendable output;

    public OverallStatisticsReporter(Appendable output) {
        this.output = output;
    }

    @Override
    public void report(final OverallClusterStatistics stats) {
        try {
            // number of cluster
            long numberOfClusters = stats.getNumberOfClusters();
            appendItem("Number of clusters:", numberOfClusters + "");

            // number of multi spectra clusters
            long numberOfMultiSpectraClusters = stats.getNumberOfMultiSpectraClusters();
            appendItem("Number of multi spectra clusters:", numberOfMultiSpectraClusters + "");

            // Ratio of multi spectra clusters
            appendItem("Ratio of multi spectra clusters:", String.format("%10.3f", (numberOfMultiSpectraClusters * 1.0f) / numberOfClusters).trim());

            // number of reliable clusters
            long numberOfReliableClusters = stats.getNumberOfReliableClusters();
            appendItem("Number of reliable clusters:", numberOfReliableClusters + "");

            // ratio of reliable clusters
            appendItem("Ratio of reliable clusters:", String.format("%10.3f", (numberOfReliableClusters * 1.0f) / numberOfClusters).trim());

            // number of single species reliable clusters
            long numberOfSingleSpeciesReliableClusters = stats.getNumberOfSingleSpeciesReliableClusters();
            appendItem("Number of single species reliable clusters:", numberOfSingleSpeciesReliableClusters + "");

            // ratio of single species reliable clusters
            appendItem("Ratio of single species reliable clusters:", String.format("%10.3f", (numberOfSingleSpeciesReliableClusters * 1.0f) / numberOfReliableClusters).trim());

            // number of two species reliable clusters
            long numberOfTwoSpeciesReliableClusters = stats.getNumberOfTwoSpeciesReliableClusters();
            appendItem("Number of two species reliable clusters:", numberOfTwoSpeciesReliableClusters + "");

            // ratio of single species reliable clusters
            appendItem("Ratio of two species reliable clusters:", String.format("%10.3f", (numberOfTwoSpeciesReliableClusters * 1.0f) / numberOfReliableClusters).trim());

            // number of multi species reliable clusters
            long numberOfMultiSpeciesReliableClusters = numberOfReliableClusters - numberOfSingleSpeciesReliableClusters;
            appendItem("Number of multi species reliable clusters:", numberOfMultiSpeciesReliableClusters + "");

            // ratio of multi species reliable clusters
            appendItem("Ratio of multi species reliable clusters:", String.format("%10.3f", (numberOfMultiSpeciesReliableClusters * 1.0f) / numberOfReliableClusters).trim());

            // number of species
            long numberOfSpecies = stats.getNumberOfSpecies();
            appendItem("Number of species:", numberOfSpecies + "");

            // number of spectra
            long numberOfSpectra = stats.getNumberOfSpectra();
            appendItem("Number of spectra:", numberOfSpectra + "");

            // number of clustered spectra
            long numberOfClusteredSpectra = stats.getNumberOfClusteredSpectra();
            appendItem("Number of clustered spectra:", numberOfClusteredSpectra + "");

            // ratio of clustered spectra
            appendItem("Ratio of clustered spectra:", String.format("%10.3f", (numberOfClusteredSpectra * 1.0f) / numberOfSpectra).trim());

            // number of not clustered spectra
            long numberOfNotClusteredSpectra = numberOfSpectra - numberOfClusteredSpectra;
            appendItem("Number of not clustered spectra:", numberOfNotClusteredSpectra + "");

            // ratio of not clustered spectra
            appendItem("Ratio of not clustered spectra:", String.format("%10.3f", (numberOfNotClusteredSpectra * 1.0f) / numberOfSpectra).trim());

            // number of assays
            // todo: assay accession is not included yet

            // number of projects
            long numberOfProjects = stats.getNumberOfProjects();
            appendItem("Number of projects:", numberOfProjects + "");

        } catch (IOException e) {
            throw new IllegalStateException("Failed to report overall statistics", e);
        }
    }

    private void appendItem(String name, String value) throws IOException {
        output.append(name);
        output.append("\n");
        output.append(value);
        output.append("\n\n");
    }
}
