package uk.ac.ebi.pride.spectracluster.statistics.stat;

import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Collecting over statistics about the clustering results
 *
 * @author Rui Wang
 * @version $Id$
 */
@ThreadSafe
public class OverallClusterStatistics {
    private final AtomicLong numberOfClusters = new AtomicLong(0);
    private final AtomicLong numberOfMultiSpectraClusters = new AtomicLong(0);
    private final AtomicLong numberOfReliableClusters = new AtomicLong(0);
    private final AtomicLong numberOfSingleSpeciesReliableClusters = new AtomicLong(0);
    private final AtomicLong numberOfTwoSpeciesReliableClusters = new AtomicLong(0);
    private final Set<String> species = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private final Set<String> spectra = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private final Set<String> clusteredSpectra = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private final Set<String> assays = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private final Set<String> projects = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    public long getNumberOfClusters() {
        return numberOfClusters.get();
    }

    public void incrementNumberOfClusters() {
        numberOfClusters.incrementAndGet();
    }

    public long getNumberOfMultiSpectraClusters() {
        return numberOfMultiSpectraClusters.get();
    }

    public void incrementNumberOfMultiSpectraClusters() {
        numberOfMultiSpectraClusters.incrementAndGet();
    }

    public long getNumberOfReliableClusters() {
        return numberOfReliableClusters.get();
    }

    public void incrementNumberOfReliableClusters() {
        numberOfReliableClusters.incrementAndGet();
    }

    public long getNumberOfSingleSpeciesReliableClusters() {
        return numberOfSingleSpeciesReliableClusters.get();
    }

    public void incrementNumberOfSingleSpeciesMultiSpectraClusters() {
        numberOfSingleSpeciesReliableClusters.incrementAndGet();
    }

    public long getNumberOfTwoSpeciesReliableClusters() {
        return numberOfTwoSpeciesReliableClusters.get();
    }

    public void incrementNumberOfTwoSpeciesMultiSpectraClusters() {
        numberOfTwoSpeciesReliableClusters.incrementAndGet();
    }

    public Set<String> getSpecies() {
        return species;
    }

    public long getNumberOfSpecies() {
        return species.size();
    }

    public void addSpecies(String species) {
        this.species.add(species);
    }

    public Set<String> getSpectra() {
        return spectra;
    }

    public long getNumberOfSpectra() {
        return spectra.size();
    }

    public void addSpectra(String spectrum) {
        this.spectra.add(spectrum);
    }

    public Set<String> getClusteredSpectra() {
        return clusteredSpectra;
    }

    public long getNumberOfClusteredSpectra() {
        return clusteredSpectra.size();
    }

    public void addClusteredSpectra(String spectrum) {
        this.clusteredSpectra.add(spectrum);
    }

    public Set<String> getAssays() {
        return assays;
    }

    public long getNumberOfAssays() {
        return assays.size();
    }

    public void addAssay(String assay) {
        this.assays.add(assay);
    }

    public Set<String> getProjects() {
        return projects;
    }

    public long getNumberOfProjects() {
        return projects.size();
    }

    public void addProject(String project) {
        this.projects.add(project);
    }
}
