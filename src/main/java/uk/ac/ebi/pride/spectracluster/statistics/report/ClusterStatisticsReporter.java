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
