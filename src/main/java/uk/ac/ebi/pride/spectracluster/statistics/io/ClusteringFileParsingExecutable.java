package uk.ac.ebi.pride.spectracluster.statistics.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.ClusteringFileReader;
import uk.ac.ebi.pride.spectracluster.clusteringfilereader.io.IClusterSourceListener;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

/**
 * Executable for reading clustering file
 *
 * @author Rui Wang
 * @version $Id$
 */
public class ClusteringFileParsingExecutable implements Runnable{
    public static final Logger logger = LoggerFactory.getLogger(ClusteringFileParsingExecutable.class);

    private final File clusteringFile;
    private final Collection<IClusterSourceListener> clusterSourceListeners;
    private final CountDownLatch countDown;

    public ClusteringFileParsingExecutable(File clusteringFile,
                                           Collection<IClusterSourceListener> clusterSourceListeners,
                                           CountDownLatch countDown) {
        this.clusteringFile = clusteringFile;
        this.clusterSourceListeners = clusterSourceListeners;
        this.countDown = countDown;
    }

    @Override
    public void run() {
        try {
            doWork();
        } catch (InterruptedException ie) {
            // do nothing with interrupted exception
            logger.warn("Clustering file parsing thread interrupted: {}", clusteringFile.getAbsolutePath());
        } catch (Exception e) {
            String msg = "Failed to parse clustering file: " + clusteringFile.getAbsolutePath();
            logger.error(msg, e);
            throw new IllegalStateException(msg, e);
        } finally {
            // count down when work has been done
            countDown.countDown();
        }
    }

    private void doWork() throws Exception {
        ClusteringFileReader clusteringFileReader = new ClusteringFileReader(clusteringFile);
        clusteringFileReader.readClustersIteratively(clusterSourceListeners);
    }
}
