package LemurDemo;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import lemurproject.indri.IndexEnvironment;
import lemurproject.indri.Specification;

public class LemurDemo {

    public static void main(String[] args) {

        try {
            String indexFolderPath = "law indexing";

            IndexEnvironment indexEnvironment = new IndexEnvironment();
//            indexEnvironment.setStoreDocs(true);
            indexEnvironment.setMetadataIndexedFields(new String[]{"docno"}, new String[]{"docno"});
            indexEnvironment.setStemmer("krovetz");
            Specification localSpecification = indexEnvironment.getFileClassSpec("xml");
            indexEnvironment.addFileClass(localSpecification);
            File indexFolder = new File(indexFolderPath);
            
            if (!indexFolder.exists()) {
                indexFolder.mkdir();
            }

            File manifest = new File(indexFolderPath + "/manifest");
            if (manifest.exists()) {
                indexEnvironment.open(indexFolderPath);
            } else {
                indexEnvironment.create(indexFolderPath);
            }

//            indexEnvironment.create("law indexing");
            File files[] = new File("law").listFiles();

            for (File file : files) {
                indexEnvironment.addFile(file.getAbsolutePath(), "xml");
                indexEnvironment.setOffsetAnnotationsPath(file.getAbsolutePath());
            }

//            indexEnvironment.setAnchorTextPath("test");
            indexEnvironment.documentsIndexed();
            indexEnvironment.close();
        } catch (Exception ex) {
            Logger.getLogger(LemurDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
