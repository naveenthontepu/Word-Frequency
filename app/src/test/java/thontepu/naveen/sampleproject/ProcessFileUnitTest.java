package thontepu.naveen.sampleproject;

import org.junit.Test;

import java.util.List;

import thontepu.naveen.sampleproject.Pojo.StringCount;
import thontepu.naveen.sampleproject.Utils.ProcessFile;

import static org.junit.Assert.assertEquals;

/**
 * Created by mac on 4/4/17
 */

public class ProcessFileUnitTest {
    @Test
    public void processString(){
        ProcessFile processFile = new ProcessFile(null);
        List<StringCount> stringCounts = processFile.getStringFrequency("a a b b b b b b b c c c c ab ab ab ab");
        assertEquals(stringCounts.size(),4);
        assertEquals(processFile.getStringFrequency("").size(),0);
        assertEquals(4,processFile.getStringFrequency("asdf\nasdf ASDF asdf asdf ;lkj ilk ;lkj").size());
        assertEquals(4,processFile.getStringFrequency("asdf\nasdf\nASDF \nasdf\n asdf\n ;lkj\n ilk\n ;lkj").size());
    }
}
