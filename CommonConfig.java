public class CommonConfig
{
    private int numPreferredNeighbours;
    private int unchokingInterval;
    private int optimisticUnchokingInterval;
    private String fileName;
    private int fileSize;
    private int pieceSize;

    public CommonConfig(int numPreferredNeighbours, int unchokingInterval,
            int optimisticUnchokingInterval, String fileName, int fileSize,
            int pieceSize)
    {
        this.numPreferredNeighbours = numPreferredNeighbours;
        this.unchokingInterval = unchokingInterval;
        this.optimisticUnchokingInterval = optimisticUnchokingInterval;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.pieceSize = pieceSize;
    }

    public CommonConfig()
    {
    } // default ctor

    protected void setNumPreferredNeighbours(int numPreferredNeighbours)
    {
        this.numPreferredNeighbours = numPreferredNeighbours;
    }

    protected void setUnchokingInterval(int unchokingInterval)
    {
        this.unchokingInterval = unchokingInterval;
    }

    protected void setOptimisticUnchokingInterval(
            int optimisticUnchokingInterval)
    {
        this.optimisticUnchokingInterval = optimisticUnchokingInterval;
    }

    protected void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    protected void setFileSize(int fileSize)
    {
        this.fileSize = fileSize;
    }

    protected void setPieceSize(int pieceSize)
    {
        this.pieceSize = pieceSize;
    }

    public int getNumPreferredNeighbours()
    {
        return numPreferredNeighbours;
    }

    public int getUnchokingInterval()
    {
        return unchokingInterval;
    }

    public int getOptimisticUnchokingInterval()
    {
        return optimisticUnchokingInterval;
    }

    public String getFileName()
    {
        return fileName;
    }

    public int getFileSize()
    {
        return fileSize;
    }

    public int getPieceSize()
    {
        return pieceSize;
    }
}

