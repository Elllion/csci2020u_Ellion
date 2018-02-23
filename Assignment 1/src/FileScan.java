public class FileScan {
    private String fileName;
    private String fileType;
    private float  spamProb;

    public FileScan(){
        setFileName("null");
        setFileType("null");
        setSpamProb(0);
    }

    public FileScan(String fn, String ft, float sp){
        setFileName(fn);
        setFileType(ft);
        setSpamProb(sp);
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public float getSpamProb() {
        return spamProb;
    }

    public void setSpamProb(float spamProb) {
        this.spamProb = spamProb;
    }
}
