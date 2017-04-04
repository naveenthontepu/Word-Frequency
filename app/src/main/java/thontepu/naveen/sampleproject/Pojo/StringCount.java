package thontepu.naveen.sampleproject.Pojo;

/**
 * Created by mac on 4/3/17
 */

public class StringCount {
    private String string;
    private int count;
    private boolean header;

    public StringCount(String string, int count) {
        this.string = string;
        this.count = count;
        header = false;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public int getCount() {
        return count;
    }

    public boolean isHeader() {
        return header;
    }

    public String getShowText() {
        return header ? string : (string + "  " + count);
    }

    @Override
    public String toString() {
        return string + "  " + count + "  " + header;
    }
}
