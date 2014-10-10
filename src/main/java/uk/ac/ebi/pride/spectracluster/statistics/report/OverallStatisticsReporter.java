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
    public void report(OverallClusterStatistics stats) {
        try {
            // number of cluster
            long numberOfClusters = stats.getNumberOfClusters();
            output.append("Number of clusters: ");
            output.append(numberOfClusters + "");
            output.append("\n");

            // number of multi spectra clusters
            long numberOfMultiSpectraClusters = stats.getNumberOfMultiSpectraClusters();
            output.append("Number of multi spectra clusters: ");
            output.append(numberOfMultiSpectraClusters + "");
            output.append("\n");

            // Ratio of multi spectra clusters
            output.append("Ratio of multi spectra clusters: ");
            output.append(String.format("%10.3f", (numberOfMultiSpectraClusters * 1.0f) / numberOfClusters).trim());
            output.append("\n");

            // number of reliable clusters
            long numberOfReliableClusters = stats.getNumberOfReliableClusters();
            output.append("Number of reliable clusters: ");
            output.append(numberOfReliableClusters + "");
            output.append("\n");

            // ratio of reliable clusters
            output.append("Ratio of reliable clusters: ");
            output.append(String.format("%10.3f", (numberOfReliableClusters * 1.0f) / numberOfClusters).trim());
            output.append("\n");

            // number of single species reliable clusters
            long numberOfSingleSpeciesReliableClusters = stats.getNumberOfSingleSpeciesReliableClusters();
            output.append("Number of single species reliable clusters: ");
            output.append(numberOfSingleSpeciesReliableClusters + "");
            output.append("\n");

            // ratio of single species reliable clusters
            output.append("Ratio of single species reliable clusters: ");
            output.append(String.format("%10.3f", (numberOfSingleSpeciesReliableClusters * 1.0f) / numberOfReliableClusters).trim());
            output.append("\n");

            // number of two species reliable clusters
            long numberOfTwoSpeciesReliableClusters = stats.getNumberOfTwoSpeciesReliableClusters();
            output.append("Number of two species reliable clusters: ");
            output.append(numberOfTwoSpeciesReliableClusters + "");
            output.append("\n");

            // ratio of single species reliable clusters
            output.append("Ratio of two species reliable clusters: ");
            output.append(String.format("%10.3f", (numberOfTwoSpeciesReliableClusters * 1.0f) / numberOfReliableClusters).trim());
            output.append("\n");

            // number of multi species reliable clusters
            long numberOfMultiSpeciesReliableClusters = numberOfReliableClusters - numberOfSingleSpeciesReliableClusters;
            output.append("Number of multi species reliable clusters: ");
            output.append(numberOfMultiSpeciesReliableClusters + "");
            output.append("\n");

            // ratio of multi species reliable clusters
            output.append("Ratio of multi species reliable clusters: ");
            output.append(String.format("%10.3f", (numberOfMultiSpeciesReliableClusters * 1.0f) / numberOfReliableClusters).trim());
            output.append("\n");

            // number of species
            long numberOfSpecies = stats.getNumberOfSpecies();
            output.append("Number of species: ");
            output.append(numberOfSpecies + "");
            output.append("\n");

            // number of spectra
            long numberOfSpectra = stats.getNumberOfSpectra();
            output.append("Number of spectra: ");
            output.append(numberOfSpectra + "");
            output.append("\n");

            // number of clustered spectra
            long numberOfClusteredSpectra = stats.getNumberOfClusteredSpectra();
            output.append("Number of clustered spectra: ");
            output.append(numberOfClusteredSpectra + "");
            output.append("\n");

            // ratio of clustered spectra
            output.append("Ratio of clustered spectra: ");
            output.append(String.format("%10.3f", (numberOfClusteredSpectra * 1.0f) / numberOfSpectra).trim());
            output.append("\n");

            // number of not clustered spectra
            long numberOfNotClusteredSpectra = numberOfSpectra - numberOfClusteredSpectra;
            output.append("Number of not clustered spectra: ");
            output.append(numberOfNotClusteredSpectra + "");
            output.append("\n");

            // ratio of not clustered spectra
            output.append("Ratio of not clustered spectra: ");
            output.append(String.format("%10.3f", (numberOfNotClusteredSpectra * 1.0f) / numberOfSpectra).trim());
            output.append("\n");

            // number of assays
            // todo: assay accession is not included yet

            // number of projects
            long numberOfProjects = stats.getNumberOfProjects();
            output.append("Number of projects: ");
            output.append(numberOfProjects + "");
            output.append("\n");

        } catch (IOException e) {
            throw new IllegalStateException("Failed to report overall statistics", e);
        }
    }
}
