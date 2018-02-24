import java.text.DecimalFormat;

public class FileScan {
    private String fileName;
    private String fileType;
    private String fileGuess;
    private float  spamProb;

    public FileScan(){
        setFileName("null");
        setFileType("null");
        setFileGuess("null");
        setSpamProb(0);
    }

    public FileScan(String fn, String ft, String fg, float sp){
        setFileName(fn);
        setFileType(ft);
        setFileGuess(fg);
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
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return Float.parseFloat(df.format(spamProb));
    }

    public void setSpamProb(float spamProb) {
        this.spamProb = spamProb;
    }

    public String getFileGuess() {
        return fileGuess;
    }

    public void setFileGuess(String fileGuess) {
        this.fileGuess = fileGuess;
    }
}
