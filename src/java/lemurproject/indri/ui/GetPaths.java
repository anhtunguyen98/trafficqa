package lemurproject.indri.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class GetPaths {

    private static final String REGQUERY_UTIL = "reg query ";
    private static final String REGSTR_TOKEN = "REG_SZ";
    private static final String REGDWORD_TOKEN = "REG_DWORD";
    private static final String PATH_CMD = "reg query \"HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\";

    public static String getPath(String paramString) {
        try {
            String str1 = "reg query \"HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\" + paramString + "\"";
            Process localProcess = Runtime.getRuntime().exec(str1);
            StreamReader localStreamReader = new StreamReader(localProcess.getInputStream());
            localStreamReader.start();
            localProcess.waitFor();
            localStreamReader.join();
            String str2 = localStreamReader.getResult();
            int i = str2.indexOf("<NO NAME>");
            str2 = str2.substring(i + 4).trim();
            int j = str2.indexOf(".exe");
            if (j == -1) {
                j = str2.indexOf(".EXE");
            }
            str2 = str2.substring(0, j + 4).trim();
            int k = str2.indexOf("REG_SZ");
            if (k == -1) {
                return null;
            }
            return str2.substring(k + "REG_SZ".length()).trim();
        } catch (Exception localException) {
        }
        return null;
    }

    static class StreamReader
            extends Thread {

        private InputStream is;
        private StringWriter sw;

        StreamReader(InputStream paramInputStream) {
            this.is = paramInputStream;
            this.sw = new StringWriter();
        }

        public void run() {
            try {
                int i;
                while ((i = this.is.read()) != -1) {
                    this.sw.write(i);
                }
            } catch (IOException localIOException) {
            }
        }

        String getResult() {
            return this.sw.toString();
        }
    }
}
