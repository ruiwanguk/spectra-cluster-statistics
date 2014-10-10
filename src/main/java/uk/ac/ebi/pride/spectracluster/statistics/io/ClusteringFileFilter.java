package uk.ac.ebi.pride.spectracluster.statistics.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Rui Wang
 * @version $Id$
 */
public class ClusteringFileFilter implements FilenameFilter {
    public static final String CLUSTERING_FILE_EXTENSION = ".clustering";

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(CLUSTERING_FILE_EXTENSION);
    }
}
