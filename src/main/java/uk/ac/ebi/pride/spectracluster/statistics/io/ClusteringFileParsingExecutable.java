package uk.ac.ebi.pride.spectracluster.statistics.io;

import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.ClusteringFileReader;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.IClusterSourceListener;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Executable for reading clustering file
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusteringFileParsingExecutable implements Callable<Void> {

    private final File clusteringFile;
    private final Collection<IClusterSourceListener> clusterSourceListeners;

    public ClusteringFileParsingExecutable(File clusteringFile,
                                           Collection<IClusterSourceListener> clusterSourceListeners) {
        this.clusteringFile = clusteringFile;
        this.clusterSourceListeners = clusterSourceListeners;
    }

    @Override
    public Void call() throws Exception {
        ClusteringFileReader clusteringFileReader = new ClusteringFileReader(clusteringFile);

        clusteringFileReader.readClustersIteratively(clusterSourceListeners);

        return null;
    }

}
