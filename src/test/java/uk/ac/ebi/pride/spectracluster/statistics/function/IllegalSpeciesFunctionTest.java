package uk.ac.ebi.pride.spectracluster.statistics.function;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IllegalSpeciesFunctionTest {

    @Test
    public void testFilterIllegalSpecies() throws Exception {
        Set<String> species = new HashSet<String>();

        species.add("NCBI: 263");
        species.add("unknown");
        species.add("9606");

        IllegalSpeciesFunction illegalSpeciesFunction = new IllegalSpeciesFunction();
        Set<String> filteredSpecies = illegalSpeciesFunction.filterIllegalSpecies(species);

        assertEquals(2, filteredSpecies.size());
        assertTrue(filteredSpecies.contains("9606"));
    }
}