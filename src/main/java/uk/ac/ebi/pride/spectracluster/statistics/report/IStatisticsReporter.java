package uk.ac.ebi.pride.spectracluster.statistics.report;

/**
 * Report knows how to handle a set of statistics and report them in the required format
 *
 * The format could be: standard output, csv file, pdf files and etc
 *
 * @author Rui Wang
 * @version $Id$
 */
public interface IStatisticsReporter<T> {

    /**
     * Report statistics
     *
     * @param stats object that contains statistics and its related values
     */
    void report(T stats);
}
