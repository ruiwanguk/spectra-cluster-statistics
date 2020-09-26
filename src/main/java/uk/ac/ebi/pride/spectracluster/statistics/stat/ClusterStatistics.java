package uk.ac.ebi.pride.spectracluster.statistics.stat;

import net.jcip.annotations.NotThreadSafe;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Statistics of a single cluster
 *
 * @author Rui Wang
 * @version $Id$
 */
@NotThreadSafe
public class ClusterStatistics {
    private String id;

    private Float averagePrecursorMz;

    private Float averagePrecursorMzWithHighestRatio;

    private Integer averagePrecursorCharge;

    private Integer averagePrecursorChargeWithHighestRatio;

    private Integer maxPrecursorCharge;

    private Integer minPrecursorCharge;

    private Integer maxPrecursorChargeWithHighestRatio;

    private Integer minPrecursorChargeWithHighestRatio;

    private Float maxPrecursorMz;

    private Float minPrecursorMz;

    private Float precursorMzRange;

    private Float maxPrecursorMzWithHighestRatio;

    private Float minPrecursorMzWithHighestRatio;

    private Float precursorMzRangeOnPeptideWithHighestRatio;

    private Integer numberOfSpectra;

    private Set<String> projects;

    private Set<String> projectOnPeptideWithHighestRatio;

    private boolean multipleHighRankingPeptideSequences;

    private Integer numberOfDistinctPeptideSequences;

    private Integer numberOfPsms;

    private Set<String> speciesInTaxonomyId;

    private Set<String> speciesOnPeptideWithHighestRatioInTaxonomyId;

    private Float highestRatio;

    private String peptideSequenceWithHighestRatio;

    private Integer peptideCountWithHighestRatio;

    private String fileName;

    private String  secondPeptideSequenceWithHighestRatio = null;

    private Integer secondPeptideCountWithHighestRatio = null;

    private String  thirdPeptideSequenceWithHighestRatio = null;

    private Integer thirdPeptideCountWithHighestRatio = null;

    private String  fourPeptideSequenceWithHighestRatio = null;

    private Integer fourPeptideCountWithHighestRatio = null;


    public ClusterStatistics() {
    }

    public ClusterStatistics(ClusterStatistics cluster) {
        this.setId(cluster.getId());
        this.setAveragePrecursorMz(cluster.getAveragePrecursorMz());
        this.setAveragePrecursorMzWithHighestRatio(cluster.getAveragePrecursorMzWithHighestRatio());
        this.setAveragePrecursorCharge(cluster.getAveragePrecursorCharge());
        this.setAveragePrecursorChargeWithHighestRatio(cluster.getAveragePrecursorChargeWithHighestRatio());
        this.setMaxPrecursorCharge(cluster.getMaxPrecursorCharge());
        this.setMinPrecursorCharge(cluster.getMinPrecursorCharge());
        this.setMaxPrecursorChargeWithHighestRatio(cluster.getMaxPrecursorChargeWithHighestRatio());
        this.setMinPrecursorChargeWithHighestRatio(cluster.getMinPrecursorChargeWithHighestRatio());
        this.setMaxPrecursorMz(cluster.getMaxPrecursorMz());
        this.setMinPrecursorMz(cluster.getMinPrecursorMz());
        this.setPrecursorMzRange(cluster.getPrecursorMzRange());
        this.setMaxPrecursorMzWithHighestRatio(cluster.getMaxPrecursorMzWithHighestRatio());
        this.setMinPrecursorMzWithHighestRatio(cluster.getMinPrecursorMzWithHighestRatio());
        this.setPrecursorMzRangeOnPeptideWithHighestRatio(cluster.getPrecursorMzRangeOnPeptideWithHighestRatio());
        this.setNumberOfSpectra(cluster.getNumberOfSpectra());
        this.setProjects(cluster.getProjects());
        this.setProjectOnPeptideWithHighestRatio(cluster.getProjectOnPeptideWithHighestRatio());
        this.setMultipleHighRankingPeptideSequences(cluster.isMultipleHighRankingPeptideSequences());
        this.setNumberOfDistinctPeptideSequences(cluster.getNumberOfDistinctPeptideSequences());
        this.setNumberOfPsms(cluster.getNumberOfPsms());
        this.setSpeciesInTaxonomyId(cluster.getSpeciesInTaxonomyId());
        this.setSpeciesOnPeptideWithHighestRatioInTaxonomyId(cluster.getSpeciesOnPeptideWithHighestRatioInTaxonomyId());
        this.setHighestRatio(cluster.getHighestRatio());

        this.setPeptideSequenceWithHighestRatio(cluster.getPeptideSequenceWithHighestRatio());
        this.setPeptideCountWithHighestRatio(cluster.getPeptideCountWithHighestRatio());

        this.setFileName(cluster.getFileName());

        this.setSecondPeptideSequenceWithHighestRatio(cluster.getSecondPeptideSequenceWithHighestRatio());
        this.setSecondPeptideCountWithHighestRatio(cluster.getSecondPeptideCountWithHighestRatio());

        this.setThirdPeptideSequenceWithHighestRatio(cluster.getThirdPeptideSequenceWithHighestRatio());
        this.setThirdPeptideCountWithHighestRatio(cluster.getThirdPeptideCountWithHighestRatio());

        this.setFourPeptideSequenceWithHighestRatio(cluster.getFourPeptideSequenceWithHighestRatio());
        this.setFourPeptideCountWithHighestRatio(cluster.getFourPeptideCountWithHighestRatio());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getAveragePrecursorMz() {
        return averagePrecursorMz;
    }

    public void setAveragePrecursorMz(Float averagePrecursorMz) {
        this.averagePrecursorMz = averagePrecursorMz;
    }

    public Integer getAveragePrecursorCharge() {
        return averagePrecursorCharge;
    }

    public void setAveragePrecursorCharge(Integer averagePrecursorCharge) {
        this.averagePrecursorCharge = averagePrecursorCharge;
    }

    public boolean isMultipleHighRankingPeptideSequences() {
        return multipleHighRankingPeptideSequences;
    }

    public void setMultipleHighRankingPeptideSequences(boolean multipleHighRankingPeptideSequences) {
        this.multipleHighRankingPeptideSequences = multipleHighRankingPeptideSequences;
    }

    public Float getPrecursorMzRange() {
        return precursorMzRange;
    }

    public void setPrecursorMzRange(Float precursorMzRange) {
        this.precursorMzRange = precursorMzRange;
    }

    public Float getPrecursorMzRangeOnPeptideWithHighestRatio() {
        return precursorMzRangeOnPeptideWithHighestRatio;
    }

    public void setPrecursorMzRangeOnPeptideWithHighestRatio(Float precursorMzRangeOnPeptideWithHighestRatio) {
        this.precursorMzRangeOnPeptideWithHighestRatio = precursorMzRangeOnPeptideWithHighestRatio;
    }

    public Integer getNumberOfSpectra() {
        return numberOfSpectra;
    }

    public void setNumberOfSpectra(Integer numberOfSpectra) {
        this.numberOfSpectra = numberOfSpectra;
    }

    public Integer getNumberOfDistinctPeptideSequences() {
        return numberOfDistinctPeptideSequences;
    }

    public void setNumberOfDistinctPeptideSequences(Integer numberOfDistinctPeptideSequences) {
        this.numberOfDistinctPeptideSequences = numberOfDistinctPeptideSequences;
    }

    public Integer getNumberOfPsms() {
        return numberOfPsms;
    }

    public void setNumberOfPsms(Integer numberOfPsms) {
        this.numberOfPsms = numberOfPsms;
    }

    public Float getHighestRatio() {
        return highestRatio;
    }

    public void setHighestRatio(Float highestRatio) {
        this.highestRatio = highestRatio;
    }

    public String getPeptideSequenceWithHighestRatio() {
        return peptideSequenceWithHighestRatio;
    }

    public void setPeptideSequenceWithHighestRatio(String peptideSequenceWithHighestRatio) {
        this.peptideSequenceWithHighestRatio = peptideSequenceWithHighestRatio;
    }

    public Integer getMaxPrecursorCharge() {
        return maxPrecursorCharge;
    }

    public void setMaxPrecursorCharge(Integer maxPrecursorCharge) {
        this.maxPrecursorCharge = maxPrecursorCharge;
    }

    public Integer getMinPrecursorCharge() {
        return minPrecursorCharge;
    }

    public void setMinPrecursorCharge(Integer minPrecursorCharge) {
        this.minPrecursorCharge = minPrecursorCharge;
    }

    public Float getMaxPrecursorMz() {
        return maxPrecursorMz;
    }

    public void setMaxPrecursorMz(Float maxPrecursorMz) {
        this.maxPrecursorMz = maxPrecursorMz;
    }

    public Float getMinPrecursorMz() {
        return minPrecursorMz;
    }

    public void setMinPrecursorMz(Float minPrecursorMz) {
        this.minPrecursorMz = minPrecursorMz;
    }

    public Float getMaxPrecursorMzWithHighestRatio() {
        return maxPrecursorMzWithHighestRatio;
    }

    public void setMaxPrecursorMzWithHighestRatio(Float maxPrecursorMzWithHighestRatio) {
        this.maxPrecursorMzWithHighestRatio = maxPrecursorMzWithHighestRatio;
    }

    public Float getMinPrecursorMzWithHighestRatio() {
        return minPrecursorMzWithHighestRatio;
    }

    public void setMinPrecursorMzWithHighestRatio(Float minPrecursorMzWithHighestRatio) {
        this.minPrecursorMzWithHighestRatio = minPrecursorMzWithHighestRatio;
    }

    public Set<String> getSpeciesInTaxonomyId() {
        return speciesInTaxonomyId;
    }

    public void setSpeciesInTaxonomyId(Set<String> speciesInTaxonomyId) {
        this.speciesInTaxonomyId = new LinkedHashSet<>(speciesInTaxonomyId);
    }

    public Set<String> getSpeciesOnPeptideWithHighestRatioInTaxonomyId() {
        return speciesOnPeptideWithHighestRatioInTaxonomyId;
    }

    public void setSpeciesOnPeptideWithHighestRatioInTaxonomyId(Set<String> speciesOnPeptideWithHighestRatioInTaxonomyId) {
        this.speciesOnPeptideWithHighestRatioInTaxonomyId = new LinkedHashSet<>(speciesOnPeptideWithHighestRatioInTaxonomyId);
    }

    public Set<String> getProjects() {
        return projects;
    }

    public void setProjects(Set<String> projects) {
        this.projects = new LinkedHashSet<>(projects);
    }

    public Float getAveragePrecursorMzWithHighestRatio() {
        return averagePrecursorMzWithHighestRatio;
    }

    public void setAveragePrecursorMzWithHighestRatio(Float averagePrecursorMzWithHighestRatio) {
        this.averagePrecursorMzWithHighestRatio = averagePrecursorMzWithHighestRatio;
    }

    public Integer getAveragePrecursorChargeWithHighestRatio() {
        return averagePrecursorChargeWithHighestRatio;
    }

    public void setAveragePrecursorChargeWithHighestRatio(Integer averagePrecursorChargeWithHighestRatio) {
        this.averagePrecursorChargeWithHighestRatio = averagePrecursorChargeWithHighestRatio;
    }

    public Integer getMaxPrecursorChargeWithHighestRatio() {
        return maxPrecursorChargeWithHighestRatio;
    }

    public void setMaxPrecursorChargeWithHighestRatio(Integer maxPrecursorChargeWithHighestRatio) {
        this.maxPrecursorChargeWithHighestRatio = maxPrecursorChargeWithHighestRatio;
    }

    public Integer getMinPrecursorChargeWithHighestRatio() {
        return minPrecursorChargeWithHighestRatio;
    }

    public void setMinPrecursorChargeWithHighestRatio(Integer minPrecursorChargeWithHighestRatio) {
        this.minPrecursorChargeWithHighestRatio = minPrecursorChargeWithHighestRatio;
    }

    public Set<String> getProjectOnPeptideWithHighestRatio() {
        return projectOnPeptideWithHighestRatio;
    }

    public void setProjectOnPeptideWithHighestRatio(Set<String> projectOnPeptideWithHighestRatio) {
        this.projectOnPeptideWithHighestRatio = new LinkedHashSet<>(projectOnPeptideWithHighestRatio);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSecondPeptideSequenceWithHighestRatio() {
        return secondPeptideSequenceWithHighestRatio;
    }

    public void setSecondPeptideSequenceWithHighestRatio(String secondPeptideSequenceWithHighestRatio) {
        this.secondPeptideSequenceWithHighestRatio = secondPeptideSequenceWithHighestRatio;
    }

    public String getThirdPeptideSequenceWithHighestRatio() {
        return thirdPeptideSequenceWithHighestRatio;
    }

    public void setThirdPeptideSequenceWithHighestRatio(String thirdPeptideSequenceWithHighestRatio) {
        this.thirdPeptideSequenceWithHighestRatio = thirdPeptideSequenceWithHighestRatio;
    }

    public String getFourPeptideSequenceWithHighestRatio() {
        return fourPeptideSequenceWithHighestRatio;
    }

    public void setFourPeptideSequenceWithHighestRatio(String fourPeptideSequenceWithHighestRatio) {
        this.fourPeptideSequenceWithHighestRatio = fourPeptideSequenceWithHighestRatio;
    }

    public Integer getFourPeptideCountWithHighestRatio() {
        return fourPeptideCountWithHighestRatio;
    }

    public void setFourPeptideCountWithHighestRatio(Integer fourPeptideCountWithHighestRatio) {
        this.fourPeptideCountWithHighestRatio = fourPeptideCountWithHighestRatio;
    }

    public Integer getSecondPeptideCountWithHighestRatio() {
        return secondPeptideCountWithHighestRatio;
    }

    public void setSecondPeptideCountWithHighestRatio(Integer secondPeptideCountWithHighestRatio) {
        this.secondPeptideCountWithHighestRatio = secondPeptideCountWithHighestRatio;
    }

    public Integer getThirdPeptideCountWithHighestRatio() {
        return thirdPeptideCountWithHighestRatio;
    }

    public void setThirdPeptideCountWithHighestRatio(Integer thirdPeptideCountWithHighestRatio) {
        this.thirdPeptideCountWithHighestRatio = thirdPeptideCountWithHighestRatio;
    }

    public Integer getPeptideCountWithHighestRatio() {
        return peptideCountWithHighestRatio;
    }

    public void setPeptideCountWithHighestRatio(Integer peptideCountWithHighestRatio) {
        this.peptideCountWithHighestRatio = peptideCountWithHighestRatio;
    }
}
