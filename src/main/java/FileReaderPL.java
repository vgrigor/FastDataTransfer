import java.io.*;
import java.util.Arrays;

public class FileReaderPL {

    //public static String FileName = "c:\\PROJECTS\\COURSES\\test_6\\DATA\\20120901_122335_Export_demo_date_OpenCart_products_utf8.csv" ;
    public static String FileName = "c:\\PROJECTS\\COURSES\\test_6\\DATA\\sss.rar" ;

    public static void main(String... args) throws IOException {

        boolean testOriginal = false;

        //Тестируем чтение
        testFileReader();

        if(testOriginal)
        for (int mb : new int[]{50, 100, 250, 500, 1000, 2000})
            testFileSize_Original(mb);
    }

    private static void testFileSize_Original(int mb) throws IOException {
        File file = File.createTempFile("test", ".txt");
        file.deleteOnExit();
        char[] chars = new char[1024];
        Arrays.fill(chars, 'A');
        String longLine = new String(chars);
        long start1 = System.nanoTime();
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        for (int i = 0; i < mb * 1024; i++)
            pw.println(longLine);
        pw.close();

        long time1 = System.nanoTime() - start1;
        System.out.printf("Took %.3f seconds to write to a %d MB, file rate: %.1f MB/s%n",
                time1 / 1e9, file.length() >> 20, file.length() * 1000.0 / time1);

        long start2 = System.nanoTime();

        BufferedReader br = new BufferedReader(new FileReader(file));
        for (String line; (line = br.readLine()) != null; ) {
        }

        br.close();
        long time2 = System.nanoTime() - start2;
        System.out.printf("Took %.3f seconds to read to a %d MB file, rate: %.1f MB/s%n",
                time2 / 1e9, file.length() >> 20, file.length() * 1000.0 / time2);
        file.delete();
    }


    private static void testFileReader() throws IOException {
        File file = new File(FileName);//File.createTempFile("test", ".txt");
        //file.deleteOnExit();
/*        char[] chars = new char[1024];
        Arrays.fill(chars, 'A');
        String longLine = new String(chars);
        long start1 = System.nanoTime();
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        for (int i = 0; i < mb * 1024; i++)
            pw.println(longLine);
        pw.close();

        long time1 = System.nanoTime() - start1;
        System.out.printf("Took %.3f seconds to write to a %d MB, file rate: %.1f MB/s%n",
                time1 / 1e9, file.length() >> 20, file.length() * 1000.0 / time1);
*/
        long start2 = System.nanoTime();

        BufferedReader br = new BufferedReader(new FileReader(file));
        for (String line; (line = br.readLine()) != null; ) {
        }

        br.close();
        long time2 = System.nanoTime() - start2;
        System.out.printf("Took %.3f seconds to read to a %d MB file, rate: %.1f MB/s%n",
                time2 / 1e9, file.length() >> 20, file.length() * 1000.0 / time2);
        //file.delete();
    }

    private static void testFileSizeWriter(int mb) throws IOException {
        File file = new File(FileName);//File.createTempFile("test", ".txt");
        //file.deleteOnExit();
        char[] chars = new char[1024];
        Arrays.fill(chars, 'A');
        String longLine = new String(chars);
        long start1 = System.nanoTime();
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        for (int i = 0; i < mb * 1024; i++)
            pw.println(longLine);
        pw.close();

        long time1 = System.nanoTime() - start1;
        System.out.printf("Took %.3f seconds to write to a %d MB, file rate: %.1f MB/s%n",
                time1 / 1e9, file.length() >> 20, file.length() * 1000.0 / time1);

        long start2 = System.nanoTime();

        BufferedReader br = new BufferedReader(new FileReader(file));
        for (String line; (line = br.readLine()) != null; ) {
        }

        br.close();
        long time2 = System.nanoTime() - start2;
        System.out.printf("Took %.3f seconds to read to a %d MB file, rate: %.1f MB/s%n",
                time2 / 1e9, file.length() >> 20, file.length() * 1000.0 / time2);
        file.delete();
    }
}
