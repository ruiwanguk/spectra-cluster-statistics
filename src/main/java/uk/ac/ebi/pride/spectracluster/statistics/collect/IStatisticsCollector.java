package uk.ac.ebi.pride.spectracluster.statistics.collect;

/**
 * Statistics collector
 *
 * @author Rui Wang
 * @version $Id$
 */
public interface IStatisticsCollector<V, T> {

    /**
     * Collect statistics from a given source
     *
     * @param source    the source of the statistics
     * @return  collected statistics
     */
    T collect(V source);
}
