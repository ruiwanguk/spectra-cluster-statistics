package uk.ac.ebi.pride.spectracluster.statistics.function;

import uk.ac.ebi.pride.spectracluster.statistics.stat.ClusterStatistics;
import uk.ac.ebi.pride.spectracluster.util.function.IFunction;

import java.util.HashSet;
import java.util.Set;

/**
 * Function to remove illegal species
 *
 * @author Rui Wang
 * @version $Id$
 */
public class IllegalSpeciesFunction implements IFunction<ClusterStatistics, ClusterStatistics> {

    @Override
    public ClusterStatistics apply(ClusterStatistics inputCluster) {
        ClusterStatistics outputCluster = new ClusterStatistics(inputCluster);

        Set<String> speciesInTaxonomyId = filterIllegalSpecies(outputCluster.getSpeciesInTaxonomyId());
        outputCluster.setSpeciesInTaxonomyId(speciesInTaxonomyId);

        Set<String> speciesOnPeptideWithHighestRatioInTaxonomyId = filterIllegalSpecies(outputCluster.getSpeciesOnPeptideWithHighestRatioInTaxonomyId());
        outputCluster.setSpeciesOnPeptideWithHighestRatioInTaxonomyId(speciesOnPeptideWithHighestRatioInTaxonomyId);

        return outputCluster;
    }

    protected Set<String> filterIllegalSpecies(Set<String> species) {
        Set<String> results = new HashSet<>();

        for (String specy : species) {
            String replacedSpecy = specy.replaceAll("([a-zA-Z: ]+)", "");
            if (replacedSpecy.length() > 0) {
                results.add(replacedSpecy);
            }
        }

        return results;
    }
}
