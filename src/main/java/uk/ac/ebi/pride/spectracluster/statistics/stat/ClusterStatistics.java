package uk.ac.ebi.pride.spectracluster.statistics.stat;

import net.jcip.annotations.NotThreadSafe;

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

    private Float averagePrecursorCharge;

    private boolean multipleHighRankingPeptideSequences;

    private Float precursorMzRange;

    private Float precursorMzRangeOnPeptideWithHighestRatio;

    private Integer numberOfSpectra;

    private Integer numberOfAssays;

    private Integer numberOfProjects;

    private Integer numberOfDistinctPeptideSequences;

    private Integer numberOfPsms;

    private Integer numberOfSpecies;

    private Integer numberOfSpeciesOnPeptideWithHighestRatio;

    private Float highestRatio;

    private String peptideSequenceWithHighestRatio;

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

    public Float getAveragePrecursorCharge() {
        return averagePrecursorCharge;
    }

    public void setAveragePrecursorCharge(Float averagePrecursorCharge) {
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

    public Integer getNumberOfAssays() {
        return numberOfAssays;
    }

    public void setNumberOfAssays(Integer numberOfAssays) {
        this.numberOfAssays = numberOfAssays;
    }

    public Integer getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(Integer numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
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

    public Integer getNumberOfSpecies() {
        return numberOfSpecies;
    }

    public void setNumberOfSpecies(Integer numberOfSpecies) {
        this.numberOfSpecies = numberOfSpecies;
    }

    public Integer getNumberOfSpeciesOnPeptideWithHighestRatio() {
        return numberOfSpeciesOnPeptideWithHighestRatio;
    }

    public void setNumberOfSpeciesOnPeptideWithHighestRatio(Integer numberOfSpeciesOnPeptideWithHighestRatio) {
        this.numberOfSpeciesOnPeptideWithHighestRatio = numberOfSpeciesOnPeptideWithHighestRatio;
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
}
