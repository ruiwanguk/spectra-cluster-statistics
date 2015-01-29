package uk.ac.ebi.pride.spectracluster.statistics.util;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.ClusteringFileReader;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.objects.ClusteringFileCluster;

import java.io.File;
import java.net.URL;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class ClusteringFileClusterUtilsTest {
    private ClusteringFileCluster cluster;

    @Before
    public void setUp() throws Exception {
        URL resource = ClusteringFileClusterUtilsTest.class.getClassLoader().getResource("ClusteringBin4563.clustering");
        ClusteringFileReader clusteringFileReader = new ClusteringFileReader(new File(resource.toURI()));
        cluster = (ClusteringFileCluster)clusteringFileReader.readAllClusters().get(0);
    }

    @Test
    public void testIsClusterReliable() throws Exception {
        boolean clusterReliable = ClusteringFileClusterUtils.isClusterReliable(cluster);
        assertTrue(clusterReliable);
    }


    @Test
    public void testGetAveragePrecursorCharge() throws Exception {
        int averagePrecursorCharge = ClusteringFileClusterUtils.getAveragePrecursorCharge(cluster);
        assertEquals(3, averagePrecursorCharge);
    }

    @Test
    public void testGetPrecursorMzRangeForHighRatioPeptide() throws Exception {
        float precursorMzRangeForHighRatioPeptide = ClusteringFileClusterUtils.getPrecursorMzRangeForHighRatioPeptide(cluster);
        assertEquals(2.0f, precursorMzRangeForHighRatioPeptide, 0.0001);
    }

    @Test
    public void testHasMultipleHighRankingPeptides() throws Exception {
        boolean hasMultipleHighRankingPeptides = ClusteringFileClusterUtils.hasMultipleHighRankingPeptides(cluster);
        assertFalse(hasMultipleHighRankingPeptides);
    }

    @Test
    public void testGetNumberOfSpeciesOnPeptideWithHighRatio() throws Exception {
        int numberOfSpeciesOnPeptideWithHighRatio = ClusteringFileClusterUtils.getNumberOfSpeciesOnPeptideWithHighRatio(cluster);
        assertEquals(4, numberOfSpeciesOnPeptideWithHighRatio);
    }

    @Test
    public void testGetNumberOfSpecies() throws Exception {
        int numberOfSpecies = ClusteringFileClusterUtils.getNumberOfSpecies(cluster);
        assertEquals(5, numberOfSpecies);
    }

    @Test
    public void testGetSpecies() throws Exception {
        Set<String> species = ClusteringFileClusterUtils.getSpecies(cluster);
        assertThat(species, hasItem("9606"));
        assertThat(species, hasItem("9607"));
        assertThat(species, hasItem("9608"));
        assertThat(species, hasItem("9609"));
    }

    @Test
    public void testGetNumberOfProjects() throws Exception {
        int numberOfProjects = ClusteringFileClusterUtils.getNumberOfProjects(cluster);
        assertEquals(3, numberOfProjects);
    }

    @Test
    public void testGetProjects() throws Exception {
        Set<String> projects = ClusteringFileClusterUtils.getProjects(cluster);
        assertThat(projects, hasItem("PRD000523"));
        assertThat(projects, hasItem("PRD000524"));
        assertThat(projects, hasItem("PRD000525"));
    }

    @Test
    public void testGetSpectrumIds() throws Exception {
        Set<String> spectrumIds = ClusteringFileClusterUtils.getSpectrumIds(cluster);
        assertEquals(10, spectrumIds.size());

        assertThat(spectrumIds, hasItem("PRD000524;PRIDE_Exp_Complete_Ac_18469.xml;spectrum=8564"));
    }

    @Test
    public void testGetAveragePrecursorChargeForHighRatioPeptide() throws Exception {
        int averagePrecursorChargeForHighRatioPeptide = ClusteringFileClusterUtils.getAveragePrecursorChargeForHighRatioPeptide(cluster);
        assertEquals(2, averagePrecursorChargeForHighRatioPeptide);
    }

    @Test
    public void testGetAveragePrecursorMzForHighRatioPeptide() throws Exception {
        float averagePrecursorMzForHighRatioPeptide = ClusteringFileClusterUtils.getAveragePrecursorMzForHighRatioPeptide(cluster);
        assertEquals(4564.594, averagePrecursorMzForHighRatioPeptide, 0.001);
    }

    @Test
    public void testGetMaxPrecursorCharge() throws Exception {
        int maxPrecursorCharge = ClusteringFileClusterUtils.getMaxPrecursorCharge(cluster);
        assertEquals(12, maxPrecursorCharge);
    }

    @Test
    public void testGetMinPrecursorCharge() throws Exception {
        int minPrecursorCharge = ClusteringFileClusterUtils.getMinPrecursorCharge(cluster);
        assertEquals(1, minPrecursorCharge);
    }

    @Test
    public void testGetMaxPrecursorMz() throws Exception {
        float maxPrecursorMz = ClusteringFileClusterUtils.getMaxPrecursorMz(cluster);
        assertEquals(4565.594, maxPrecursorMz, 0.001);
    }

    @Test
    public void testGetMinPrecursorMz() throws Exception {
        float minPrecursorMz = ClusteringFileClusterUtils.getMinPrecursorMz(cluster);
        assertEquals(4563.594, minPrecursorMz, 0.001);
    }

    @Test
    public void testGetMaxPrecursorMzForHighRatioPeptide() throws Exception {
        float maxPrecursorMz = ClusteringFileClusterUtils.getMaxPrecursorMzForHighRatioPeptide(cluster);
        assertEquals(4565.594, maxPrecursorMz, 0.001);
    }

    @Test
    public void testGetMinPrecursorMzForHighRatioPeptide() throws Exception {
        float minPrecursorMz = ClusteringFileClusterUtils.getMinPrecursorMzForHighRatioPeptide(cluster);
        assertEquals(4563.594, minPrecursorMz, 0.001);
    }

    @Test
    public void testGetSpeciesOnPeptideWithHighRatio() throws Exception {
        Set<String> speciesOnPeptideWithHighRatio = ClusteringFileClusterUtils.getSpeciesOnPeptideWithHighRatio(cluster);
        assertEquals(4, speciesOnPeptideWithHighRatio.size());
    }
}