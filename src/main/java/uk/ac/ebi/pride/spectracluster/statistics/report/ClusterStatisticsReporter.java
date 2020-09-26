package uk.ac.ebi.pride.spectracluster.statistics.report;

import net.jcip.annotations.GuardedBy;
import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

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
        ID ("CLUSTER_ID", "Cluster ID"),
        AVG_PRECURSOR_MZ("AVG_PMZ", "Average precursor m/z for the cluster"),
        AVG_PRECURSOR_MZ_HIGHEST("AVG_PMZ_HIGHEST", "Average precursor m/z for spectra with highest peptide identification ratio in the cluster"),
        AVG_PRECURSOR_CHARGE("AVG_PCHARGE", "Average precursor charge for the cluster"),
        AVG_PRECURSOR_CHARGE_HIGHEST("AVG_PCHARGE_HIGHEST", "Average precursor charge for spectra with highest peptide identification ratio in the cluster"),
        MAX_PRECURSOR_CHARGE("MAX_PCHARGE", "Maximum precursor charge for the cluster"),
        MIN_PRECURSOR_CHARGE("MIN_PCHARGE", "Minimum precursor charge for the cluster"),
        MAX_PRECURSOR_CHARGE_HIGHEST("MAX_PCHARGE_HIGHEST", "Maximum precursor charge for the cluster"),
        MIN_PRECURSOR_CHARGE_HIGHEST("MIN_PCHARGE_HIGHEST", "Minimum precursor charge for the cluster"),
        MAX_PRECURSOR_MZ("MAX_PMZ", "Maximum precursor m/z for the cluster"),
        MIN_PRECURSOR_MZ("MIN_PMZ", "Minimum precursor m/z for the cluster"),
        PRECURSOR_MZ_RANGE("PMZ_RANGE", "The range of the precursor m/z for all the spectra in the cluster, it represents difference between the lowest precursor m/z and the highest precursor m/z"),
        MAX_PRECURSOR_MZ_HIGHEST("MAX_PMZ_HIGHEST", "Maximum precursor m/z for spectra with highest peptide identification ratio in the cluster"),
        MIN_PRECURSOR_MZ_HIGHEST("MIN_PMZ_HIGHEST", "Minimum precursor m/z for spectra with highest peptide identification ratio in the cluster"),
        PRECURSOR_MZ_RANGE_HIGHEST("PMZ_RANGE_HIGHEST", "The range of the precursor m/z for the spectra with highest peptide identification ratio in the cluster, it represents the difference between the lowest precursor m/z and the highest precursor m/z"),
        NUMBER_OF_SPECTRA("NUM_SPECTRA", "Total number of spectra within the cluster"),
        NUMBER_OF_PROJECTS("NUM_PROJECTS", "Total number of COMPLETE projects from PRIDE Archive within the cluster"),
        PROJECT("PROJECT", "Project accessions from PRIDE Archive, separated by semicolon"),
        NUMBER_OF_PROJECTS_HIGHEST("NUM_PROJECTS_HIGHEST", "Total number of COMPLETE projects from PRIDE Archive for spectra with highest peptide identification ratio within the cluster"),
        PROJECT_HIGHEST("PROJECT_HIGHEST", "Project accessions from PRIDE Archive for spectra with highest peptide identification ratio, separated by semicolon"),
        MULTI_HIGHEST_PEPTIDES("MULTI_HIGHEST", "Boolean indicates whether there are multiple peptides that have the highest ratios"),
        NUMBER_OF_DISTINCT_PEPTIDES("NUM_PEPTIDES", "Total number of unique peptide sequences within the cluster"),
        NUMBER_OF_PSMS("NUM_PSMS", "Total number of PSMs within the cluster"),
        NUMBER_OF_SPECIES("NUM_SPECIES", "Total number of species within the cluster"),
        SPECIES("SPECIES", "Species within the cluster in taxonomy ID, separated by semicolon."),
        NUMBER_OF_SPECIES_HIGHEST("NUM_SPECIES_HIGHEST", "Total number of species for peptides that have the highest ratios within the cluster"),
        SPECIES_HIGHEST("SPECIES_HIGHEST", "Species for peptides that have the highest ratios within the cluster, in taxonomy ID, separated by semicolon."),
        MAX_RATIO("MAX_RATIO", "The highest ratio within the cluster"),

        PEPTIDE_HIGHEST("PEP_SEQ", "The peptide sequence for the peptide with the highest ratio within the cluster"),
        PEPTIDE_COUNT_HIGHEST("PEP_COUNT", "The peptide count  for the peptide with the highest ratio within the cluster"),

        PEPTIDE_HIGHEST_SECOND("PEP_SEQ_SECOND", "The peptide sequence for the second peptide with the highest ratio within the cluster"),
        PEPTIDE_COUNT_HIGHEST_SECOND("PEP_COUNT_SECOND", "The peptide count for the second peptide with the highest ratio within the cluster"),

        PEPTIDE_HIGHEST_THIRD("PEP_SEQ_THIRD", "The peptide sequence for the third peptide with the highest ratio within the cluster"),
        PEPTIDE_COUNT_HIGHEST_THIRD("PEP_COUNT_THIRD", "The peptide count for the third peptide with the highest ratio within the cluster"),

        PEPTIDE_HIGHEST_FOUR("PEP_SEQ_FOUR", "The peptide sequence for the four peptide with the highest ratio within the cluster"),
        PEPTIDE_COUNT_HIGHEST_FOUR("PEP_COUNT_FOUR", "The peptide count for the four peptide with the highest ratio within the cluster"),

        FILE_NAME("FILE_NAME", "The original clustering result file that contains this cluster");

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
        appendObject(line, stats.getId());

        // average precursor m/z
        appendFloat(line, stats.getAveragePrecursorMz());

        // average precursor m/z on peptide with highest ratio
        appendFloat(line, stats.getAveragePrecursorMzWithHighestRatio());

        // average precursor charge
        appendObject(line, stats.getAveragePrecursorCharge());

        // average precursor charge on peptide with highest ratio
        appendObject(line, stats.getAveragePrecursorChargeWithHighestRatio());

        // max precursor charge
        appendObject(line, stats.getMaxPrecursorCharge());

        // min precursor charge
        appendObject(line, stats.getMinPrecursorCharge());

        // max precursor charge on peptide with highest ratio
        appendObject(line, stats.getMaxPrecursorChargeWithHighestRatio());

        // min precursor charge on peptide with highest ratio
        appendObject(line, stats.getMinPrecursorChargeWithHighestRatio());

        // maximum precursor m/z
        appendFloat(line, stats.getMaxPrecursorMz());

        // minimum precursor m/z
        appendFloat(line, stats.getMinPrecursorMz());

        // precursor m/z range
        appendFloat(line, stats.getPrecursorMzRange());

        // max precursor m/z on peptide with highest ratio
        appendFloat(line, stats.getMaxPrecursorMzWithHighestRatio());

        // min precursor m/z on peptide with highest ratio
        appendFloat(line, stats.getMinPrecursorMzWithHighestRatio());

        // precursor m/z range on peptide with highest ratio
        appendFloat(line, stats.getPrecursorMzRangeOnPeptideWithHighestRatio());

        // Number of spectra
        appendObject(line, stats.getNumberOfSpectra());

        // Number of projects
        Set<String> projects = stats.getProjects();
        appendObject(line, projects.size());

        // projects
        appendObject(line, collectionToString(projects));

        // Number of projects on peptide with highest ratio
        Set<String> projectOnPeptideWithHighestRatio = stats.getProjectOnPeptideWithHighestRatio();
        appendObject(line, projectOnPeptideWithHighestRatio.size());

        // projects on peptide with highest ratio
        appendObject(line, collectionToString(projectOnPeptideWithHighestRatio));

        // multiple high ranking peptide sequences
        appendObject(line, stats.isMultipleHighRankingPeptideSequences());

        // Number of distinct peptide sequences
        appendObject(line, stats.getNumberOfDistinctPeptideSequences());

        // Number of PSMs
        appendObject(line, stats.getNumberOfPsms());

        // Number of species
        Set<String> speciesInTaxonomyId = stats.getSpeciesInTaxonomyId();
        appendObject(line, speciesInTaxonomyId.size());

        // species
        appendObject(line, collectionToString(speciesInTaxonomyId));

        // Number of species on peptide with highest ratio
        Set<String> speciesOnPeptideWithHighestRatioInTaxonomyId = stats.getSpeciesOnPeptideWithHighestRatioInTaxonomyId();
        appendObject(line, speciesOnPeptideWithHighestRatioInTaxonomyId.size());

        // species on peptide with highest ratio
        appendObject(line, collectionToString(speciesOnPeptideWithHighestRatioInTaxonomyId));

        // highest ratio
        appendFloat(line, stats.getHighestRatio());

        // peptide sequence with highest ratio
        appendObject(line, stats.getPeptideSequenceWithHighestRatio());
        appendObject(line, stats.getPeptideCountWithHighestRatio());

        //add second sequence
        appendObject(line, stats.getSecondPeptideSequenceWithHighestRatio());
        appendObject(line, stats.getSecondPeptideCountWithHighestRatio());

        //add third sequence
        appendObject(line, stats.getThirdPeptideSequenceWithHighestRatio());
        appendObject(line, stats.getThirdPeptideCountWithHighestRatio());

        //add four sequence
        appendObject(line, stats.getFourPeptideSequenceWithHighestRatio());
        appendObject(line, stats.getFourPeptideCountWithHighestRatio());

        // clustering file name
        appendObject(line, stats.getFileName());

        // remove the last tab
        line = line.delete(line.length() - 1, line.length());

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

    private String collectionToString(Collection objs) {
        StringBuilder appender = new StringBuilder();

        if (objs == null || objs.isEmpty())
            return NOT_AVAILABLE;

        for (Object obj : objs) {
            appender.append(obj).append(";");
        }

        String content = appender.toString();
        return content.substring(0, content.length() - 1);
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
