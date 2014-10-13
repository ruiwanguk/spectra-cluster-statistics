package uk.ac.ebi.pride.spectracluster.statistics.report;

import net.jcip.annotations.GuardedBy;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;

import java.io.IOException;

/**
 * Report the statistics of single cluster
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusterStatisticsReporter implements IStatisticsReporter<ClusterStatistics> {

    public static final String NOT_AVAILABLE = "NA";

    /**
     * Headers for the cluster statistics report
     * NOTE: Please keep this updated if you have changed content of the report
     * NOTE: The order of this enumeration is important, please keep the order in accordance of the output content
     */
    public static enum HEADER {
        ID ("ID", "Cluster ID"),
        AVG_PRECURSOR_MZ("AVG_PMZ", "Average precursor m/z for the cluster"),
        AVG_PRECURSOR_CHARGE("AVG_PCHARGE", "Average precursor charge for the cluster"),
        PRECURSOR_MZ_RANGE("PMZ_RANGE", "The range of the precursor m/z for the cluster"),
        PRECURSOR_MZ_RANGE_HIGHEST("PMZ_RANGE_HIGHEST", "The range of the precursor m/z for the peptide with highest ratio within the cluster"),
        MULTI_HIGHEST_PEPTIDES("MULTI_HIGHEST", "Whether there are multiple peptides that have the highest ratios"),
        NUMBER_OF_SPECTRA("#SPECTRUM", "Total number of unique spectra within the cluster"),
        NUMBER_OF_PROJECTS("#PROJECT", "Total number of unique projects within the cluster"),
        NUMBER_OF_DISTINCT_PEPTIDES("#PEPTIDE", "Total number of unique peptide sequences within the cluster"),
        NUMBER_OF_PSMS("#PSM", "Total number of PSMs within the cluster"),
        NUMBER_OF_SPECIES("#SPECIES", "Total number of species within the cluster"),
        NUMBER_OF_SPECIES_HIGHEST("#SPECIES_HIGHEST", "Total number of species for peptides that have the highest rations within the cluster"),
        MAX_RATIO("MAX_RATIO", "The highest ratio within the cluster"),
        PEPTIDE_HIGHEST("PEP_SEQ", "The peptide sequence for the peptide with the highest ratio within the cluster");

        String header;
        String description;

        HEADER(String header, String description) {
            this.header = header;
            this.description = description;
        }

        public String getHeader() {
            return header;
        }

        public String getDescription() {
            return description;
        }
    }

    @GuardedBy("this")
    private final Appendable output;

    public ClusterStatisticsReporter(Appendable output) {
        this.output = output;
    }

    @Override
    public void report(final ClusterStatistics stats) {
        StringBuilder line = new StringBuilder();

        // cluster id
        // todo: cluster id is not provided yet
        appendObject(line, stats.getId());

        // average precursor m/z
        appendFloat(line, stats.getAveragePrecursorMz());

        // average precursor charge
        appendFloat(line, stats.getAveragePrecursorCharge());

        // precursor m/z range
        appendFloat(line, stats.getPrecursorMzRange());

        // precursor m/z range on peptide with highest ratio
        appendFloat(line, stats.getPrecursorMzRangeOnPeptideWithHighestRatio());

        // multiple high ranking peptide sequences
        appendObject(line, stats.isMultipleHighRankingPeptideSequences());

        // Number of spectra
        appendObject(line, stats.getNumberOfSpectra());

        // Number of assays
        // todo: assay accessions not provided yet

        // Number of projects
        appendObject(line, stats.getNumberOfProjects());

        // Number of distinct peptide sequences
        appendObject(line, stats.getNumberOfDistinctPeptideSequences());

        // Number of PSMs
        appendObject(line, stats.getNumberOfPsms());

        // Number of species
        appendObject(line, stats.getNumberOfSpecies());

        // Number of species on peptide with highest ratio
        appendObject(line, stats.getNumberOfSpeciesOnPeptideWithHighestRatio());

        // highest ratio
        appendFloat(line, stats.getHighestRatio());

        // peptide sequence with highest ratio
        appendObject(line, stats.getPeptideSequenceWithHighestRatio());

        // write out the statistics
        output(line);
    }

    private void appendObject(StringBuilder line, Object s) {
        String str = s == null ? NOT_AVAILABLE : s.toString();
        line.append(str).append("\t");
    }

    private void appendFloat(StringBuilder line, Float f) {
        String fString = f == null ? NOT_AVAILABLE : String.format("%10.3f", f).trim();
        line.append(fString).append("\t");
    }

    private synchronized void output(StringBuilder line) {
        try {
            output.append(line.toString());
            output.append("\n");
        } catch (IOException e) {
            throw new IllegalStateException("Failed append cluster statistics", e);
        }
    }
}
