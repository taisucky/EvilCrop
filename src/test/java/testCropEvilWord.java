import com.taisucky.demo.EvilWordCrop;
import org.junit.Test;

public class testCropEvilWord {
        @Test
        public void testCrop(){
            String inputString = "you are a nice person , and very friendly!";
            EvilWordCrop evilWordCrop = new EvilWordCrop();
            evilWordCrop.crop(inputString);
      }
}
