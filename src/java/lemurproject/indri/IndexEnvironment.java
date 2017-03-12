package lemurproject.indri;

import java.util.Map;

public class IndexEnvironment {

    private long swigCPtr;
    protected boolean swigCMemOwn;

    public IndexEnvironment(long paramLong, boolean paramBoolean) {
        this.swigCMemOwn = paramBoolean;
        this.swigCPtr = paramLong;
    }

    public static long getCPtr(IndexEnvironment paramIndexEnvironment) {
        return paramIndexEnvironment == null ? 0L : paramIndexEnvironment.swigCPtr;
    }

    protected void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0L) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                indriJNI.delete_IndexEnvironment(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
    }

    public IndexEnvironment() {
        this(indriJNI.new_IndexEnvironment(), true);
    }

    public void setDocumentRoot(String paramString)
            throws Exception {
        indriJNI.IndexEnvironment_setDocumentRoot(this.swigCPtr, this, paramString);
    }

    public void setAnchorTextPath(String paramString)
            throws Exception {
        indriJNI.IndexEnvironment_setAnchorTextPath(this.swigCPtr, this, paramString);
    }

    public void setOffsetMetadataPath(String paramString)
            throws Exception {
        indriJNI.IndexEnvironment_setOffsetMetadataPath(this.swigCPtr, this, paramString);
    }

    public void setOffsetAnnotationsPath(String paramString)
            throws Exception {
        indriJNI.IndexEnvironment_setOffsetAnnotationsPath(this.swigCPtr, this, paramString);
    }

    public void addFileClass(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, Map paramMap)
            throws Exception {
        indriJNI.IndexEnvironment_addFileClass__SWIG_0(this.swigCPtr, this, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, paramArrayOfString4, paramMap);
    }

    public Specification getFileClassSpec(String paramString)
            throws Exception {
        return indriJNI.IndexEnvironment_getFileClassSpec(this.swigCPtr, this, paramString);
    }

    public void addFileClass(Specification paramSpecification)
            throws Exception {
        indriJNI.IndexEnvironment_addFileClass__SWIG_1(this.swigCPtr, this, paramSpecification);
    }

    public void deleteDocument(int paramInt)
            throws Exception {
        indriJNI.IndexEnvironment_deleteDocument(this.swigCPtr, this, paramInt);
    }

    public void setIndexedFields(String[] paramArrayOfString)
            throws Exception {
        indriJNI.IndexEnvironment_setIndexedFields(this.swigCPtr, this, paramArrayOfString);
    }

    public void setNumericField(String paramString1, boolean paramBoolean, String paramString2)
            throws Exception {
        indriJNI.IndexEnvironment_setNumericField__SWIG_0(this.swigCPtr, this, paramString1, paramBoolean, paramString2);
    }

    public void setNumericField(String paramString, boolean paramBoolean)
            throws Exception {
        indriJNI.IndexEnvironment_setNumericField__SWIG_1(this.swigCPtr, this, paramString, paramBoolean);
    }

    public void setOrdinalField(String paramString, boolean paramBoolean)
            throws Exception {
        indriJNI.IndexEnvironment_setOrdinalField(this.swigCPtr, this, paramString, paramBoolean);
    }

    public void setParentalField(String paramString, boolean paramBoolean)
            throws Exception {
        indriJNI.IndexEnvironment_setParentalField(this.swigCPtr, this, paramString, paramBoolean);
    }

    public void setMetadataIndexedFields(String[] paramArrayOfString1, String[] paramArrayOfString2)
            throws Exception {
        indriJNI.IndexEnvironment_setMetadataIndexedFields(this.swigCPtr, this, paramArrayOfString1, paramArrayOfString2);
    }

    public void setStopwords(String[] paramArrayOfString)
            throws Exception {
        indriJNI.IndexEnvironment_setStopwords(this.swigCPtr, this, paramArrayOfString);
    }

    public void setStemmer(String paramString)
            throws Exception {
        indriJNI.IndexEnvironment_setStemmer(this.swigCPtr, this, paramString);
    }

    public void setMemory(long paramLong)
            throws Exception {
        indriJNI.IndexEnvironment_setMemory(this.swigCPtr, this, paramLong);
    }

    public void setNormalization(boolean paramBoolean)
            throws Exception {
        indriJNI.IndexEnvironment_setNormalization(this.swigCPtr, this, paramBoolean);
    }

    public void setStoreDocs(boolean paramBoolean)
            throws Exception {
        indriJNI.IndexEnvironment_setStoreDocs(this.swigCPtr, this, paramBoolean);
    }

    public void create(String paramString, IndexStatus paramIndexStatus)
            throws Exception {
        indriJNI.IndexEnvironment_create__SWIG_0(this.swigCPtr, this, paramString, IndexStatus.getCPtr(paramIndexStatus), paramIndexStatus);
    }

    public void create(String paramString)
            throws Exception {
        indriJNI.IndexEnvironment_create__SWIG_1(this.swigCPtr, this, paramString);
    }

    public void open(String paramString, IndexStatus paramIndexStatus)
            throws Exception {
        indriJNI.IndexEnvironment_open__SWIG_0(this.swigCPtr, this, paramString, IndexStatus.getCPtr(paramIndexStatus), paramIndexStatus);
    }

    public void open(String paramString)
            throws Exception {
        indriJNI.IndexEnvironment_open__SWIG_1(this.swigCPtr, this, paramString);
    }

    public void close()
            throws Exception {
        indriJNI.IndexEnvironment_close(this.swigCPtr, this);
    }

    public void addFile(String paramString)
            throws Exception {
        indriJNI.IndexEnvironment_addFile__SWIG_0(this.swigCPtr, this, paramString);
    }

    public void addFile(String paramString1, String paramString2)
            throws Exception {
        indriJNI.IndexEnvironment_addFile__SWIG_1(this.swigCPtr, this, paramString1, paramString2);
    }

    public int addString(String paramString1, String paramString2, Map paramMap)
            throws Exception {
        return indriJNI.IndexEnvironment_addString__SWIG_0(this.swigCPtr, this, paramString1, paramString2, paramMap);
    }

    public int addString(String paramString1, String paramString2, Map paramMap, TagExtent[] paramArrayOfTagExtent)
            throws Exception {
        return indriJNI.IndexEnvironment_addString__SWIG_1(this.swigCPtr, this, paramString1, paramString2, paramMap, paramArrayOfTagExtent);
    }

    public int addParsedDocument(ParsedDocument paramParsedDocument)
            throws Exception {
        return indriJNI.IndexEnvironment_addParsedDocument(this.swigCPtr, this, paramParsedDocument);
    }

    public int documentsIndexed()
            throws Exception {
        return indriJNI.IndexEnvironment_documentsIndexed(this.swigCPtr, this);
    }

    public int documentsSeen()
            throws Exception {
        return indriJNI.IndexEnvironment_documentsSeen(this.swigCPtr, this);
    }
}


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\IndexEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
