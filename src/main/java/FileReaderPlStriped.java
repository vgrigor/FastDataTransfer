import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sun.nio.ch.IOStatus.EOF;

public class FileReaderPlStriped {

    //public static String FileName = "c:\\PROJECTS\\COURSES\\test_6\\DATA\\20120901_122335_Export_demo_date_OpenCart_products_utf8.csv" ;
    //public static String FileName = "c:\\download\\gradle-4.10.2-bin.zip" ;
    public static String FileName = ".\\DATA\\business-price-indexes-june-2018-quarter.csv" ;



    public static void main(String... args) throws IOException {

        boolean testOriginal = false;

        //Тестируем чтение
        testFileReader();

        for(int i=0; i < 20; i++) {
            read3();
            read4();
            read5();


            System.out.println("-----") ;
        }

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


    static void read3()  {

        // прочитать данные из файла, используя буферизацию
        // и прямой доступ к буферу
        try {

        File file = new File(FileName);
        FileInputStream fis = null;

            fis = new FileInputStream(file );
        long start2 = System.nanoTime();


        long cnt3 = 0;
        final int BUFSIZE = 1024*4;//1024;
        byte buf[] = new byte[BUFSIZE];
        int len;
        for (int n=0;(len = fis.read(buf)) != -1 &&  n< 10000; n++) {
            for (int i = 0; i < len; i++) {
                if (buf[i] == 'A' || buf[i] == EOF) {
                    cnt3++;
                }
            }
        }
        fis.close();

        long time2 = System.nanoTime() - start2;
        System.out.printf("read3 ==>> Took %.3f seconds to read to a %d MB file, rate: %.1f MB/s%n",
                time2 / 1e9, file.length() >> 20, file.length() * 1000.0 / time2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    static void read4() throws IOException {

        // прочитать данные из файла, используя буферизацию
        // и прямой доступ к буферу

        File file = new File(FileName);
        FileInputStream fis = new FileInputStream(file );
        long start2 = System.nanoTime();


        long cnt3 = 0;
        final int BUFSIZE = 1024*8;//1024;
        byte buffer[][] = new byte[10000][BUFSIZE];
        byte buf[] = new byte[BUFSIZE];
        byte new_buf[] = new byte[BUFSIZE];
        byte str[] = new byte[BUFSIZE];

        int len;
        //buf = buffer[0];
        for (int n=0;  (len = fis.read(buf)) != -1 &&  n< 10000; n++) {
            int p1 =0, p2 =0;
            for (int i = 0; i < len; i++) {

                if(true)
                    continue;

                if (buf[i] == '\r' || buf[i] == EOF) {
                    p2=i;
                    cnt3++;

                    getString(p1, p2, buf, str);
//                    str = Arrays.copyOfRange(buf, p1,p2);
//                    String new_str = new String(str, StandardCharsets.UTF_8);
//                    System.out.println("STR =" + new_str);

                    p1 = p2+2;

                }
            }
            if(p1 < len-2 && p2 < len-1) {
                p2 = len - 1;

                getString(p1, p2, buf, str);

//                str = Arrays.copyOfRange(buf, p1,p2);
//                String new_str = new String(str, StandardCharsets.UTF_8);
//                System.out.println("STR =" + new_str);

            }

            buf = buffer[n];
        }

        //System.out.println("cnt=" + cnt3);
        fis.close();

        long time2 = System.nanoTime() - start2;
        System.out.printf("read4 ==>> Took %.3f seconds to read to a %d MB file, rate: %.1f MB/s%n",
                time2 / 1e9, file.length() >> 20, file.length() * 1000.0 / time2);


    }


    static void read5() throws IOException {

        String fileName = "file:\\c:\\PROJECTS\\COURSES\\FastDataTransfer\\DATA\\business-price-indexes-june-2018-quarter.csv";
        File file = new File(FileName);
        //FileInputStream fis = new FileInputStream(FileName );

        long start2 = System.nanoTime();

        InputStream in = new URL( fileName ).openStream();
        long i=0;
        try {
            InputStreamReader inR = new InputStreamReader( in );
            BufferedReader buf = new BufferedReader( inR );
            String line;
            for (int n=0; ( line = buf.readLine() ) != null && n < 100000; n++ ) {
                i+= line.length();
            }
        } finally {
            in.close();
            //fis.close();
        }


        long time2 = System.nanoTime() - start2;
        System.out.printf("read5 ==>> Took %.3f seconds to read to a %d MB file, rate: %.1f MB/s%n",
                time2 / 1e9, file.length() >> 20, file.length() * 1000.0 / time2);

        System.out.println( "" + i );

    }



    static void getString(int p1, int p2, byte[] buf, byte str[]){
        str = Arrays.copyOfRange(buf, p1,p2);

//        String new_str = new String(str, StandardCharsets.UTF_8);
//        System.out.println("STR =" + new_str);

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
