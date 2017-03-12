package lemurproject.indri;

import java.util.Map;

public class QueryEnvironment {

    private long swigCPtr;
    protected boolean swigCMemOwn;

    public QueryEnvironment(long paramLong, boolean paramBoolean) {
        this.swigCMemOwn = paramBoolean;
        this.swigCPtr = paramLong;
    }

    public static long getCPtr(QueryEnvironment paramQueryEnvironment) {
        return paramQueryEnvironment == null ? 0L : paramQueryEnvironment.swigCPtr;
    }

    protected void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0L) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                indriJNI.delete_QueryEnvironment(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
    }

    public QueryEnvironment() {
        this(indriJNI.new_QueryEnvironment(), true);
    }

    public void addServer(String paramString)
            throws Exception {
        indriJNI.QueryEnvironment_addServer(this.swigCPtr, this, paramString);
    }

    public void addIndex(String paramString)
            throws Exception {
        indriJNI.QueryEnvironment_addIndex(this.swigCPtr, this, paramString);
    }

    public void removeServer(String paramString)
            throws Exception {
        indriJNI.QueryEnvironment_removeServer(this.swigCPtr, this, paramString);
    }

    public void removeIndex(String paramString)
            throws Exception {
        indriJNI.QueryEnvironment_removeIndex(this.swigCPtr, this, paramString);
    }

    public void close()
            throws Exception {
        indriJNI.QueryEnvironment_close(this.swigCPtr, this);
    }

    public void setMemory(long paramLong)
            throws Exception {
        indriJNI.QueryEnvironment_setMemory(this.swigCPtr, this, paramLong);
    }

    public void setScoringRules(String[] paramArrayOfString)
            throws Exception {
        indriJNI.QueryEnvironment_setScoringRules(this.swigCPtr, this, paramArrayOfString);
    }

    public void setStopwords(String[] paramArrayOfString)
            throws Exception {
        indriJNI.QueryEnvironment_setStopwords(this.swigCPtr, this, paramArrayOfString);
    }

    public ScoredExtentResult[] runQuery(String paramString, int paramInt)
            throws Exception {
        return indriJNI.QueryEnvironment_runQuery__SWIG_0(this.swigCPtr, this, paramString, paramInt);
    }

    public ScoredExtentResult[] runQuery(String paramString, int[] paramArrayOfInt, int paramInt)
            throws Exception {
        return indriJNI.QueryEnvironment_runQuery__SWIG_1(this.swigCPtr, this, paramString, paramArrayOfInt, paramInt);
    }

    public QueryResults runQuery(QueryRequest paramQueryRequest)
            throws Exception {
        return indriJNI.QueryEnvironment_runQuery__SWIG_2(this.swigCPtr, this, paramQueryRequest);
    }

    public QueryAnnotation runAnnotatedQuery(String paramString, int paramInt)
            throws Exception {
        long l = indriJNI.QueryEnvironment_runAnnotatedQuery__SWIG_0(this.swigCPtr, this, paramString, paramInt);
        return l == 0L ? null : new QueryAnnotation(l, true);
    }

    public QueryAnnotation runAnnotatedQuery(String paramString, int[] paramArrayOfInt, int paramInt)
            throws Exception {
        long l = indriJNI.QueryEnvironment_runAnnotatedQuery__SWIG_1(this.swigCPtr, this, paramString, paramArrayOfInt, paramInt);
        return l == 0L ? null : new QueryAnnotation(l, true);
    }

    public ParsedDocument[] documents(int[] paramArrayOfInt)
            throws Exception {
        return indriJNI.QueryEnvironment_documents__SWIG_0(this.swigCPtr, this, paramArrayOfInt);
    }

    public ParsedDocument[] documents(ScoredExtentResult[] paramArrayOfScoredExtentResult)
            throws Exception {
        return indriJNI.QueryEnvironment_documents__SWIG_1(this.swigCPtr, this, paramArrayOfScoredExtentResult);
    }

    public String[] documentMetadata(int[] paramArrayOfInt, String paramString)
            throws Exception {
        return indriJNI.QueryEnvironment_documentMetadata__SWIG_0(this.swigCPtr, this, paramArrayOfInt, paramString);
    }

    public String[] documentMetadata(ScoredExtentResult[] paramArrayOfScoredExtentResult, String paramString)
            throws Exception {
        return indriJNI.QueryEnvironment_documentMetadata__SWIG_1(this.swigCPtr, this, paramArrayOfScoredExtentResult, paramString);
    }

    public int[] documentIDsFromMetadata(String paramString, String[] paramArrayOfString)
            throws Exception {
        return indriJNI.QueryEnvironment_documentIDsFromMetadata(this.swigCPtr, this, paramString, paramArrayOfString);
    }

    public ParsedDocument[] documentsFromMetadata(String paramString, String[] paramArrayOfString)
            throws Exception {
        return indriJNI.QueryEnvironment_documentsFromMetadata(this.swigCPtr, this, paramString, paramArrayOfString);
    }

    public long termCount()
            throws Exception {
        return indriJNI.QueryEnvironment_termCount__SWIG_0(this.swigCPtr, this);
    }

    public long termCount(String paramString)
            throws Exception {
        return indriJNI.QueryEnvironment_termCount__SWIG_1(this.swigCPtr, this, paramString);
    }

    public long termFieldCount(String paramString1, String paramString2)
            throws Exception {
        return indriJNI.QueryEnvironment_termFieldCount(this.swigCPtr, this, paramString1, paramString2);
    }

    public String[] fieldList()
            throws Exception {
        return indriJNI.QueryEnvironment_fieldList(this.swigCPtr, this);
    }

    public long documentCount()
            throws Exception {
        return indriJNI.QueryEnvironment_documentCount__SWIG_0(this.swigCPtr, this);
    }

    public long documentCount(String paramString)
            throws Exception {
        return indriJNI.QueryEnvironment_documentCount__SWIG_1(this.swigCPtr, this, paramString);
    }

    public DocumentVector[] documentVectors(int[] paramArrayOfInt)
            throws Exception {
        return indriJNI.QueryEnvironment_documentVectors(this.swigCPtr, this, paramArrayOfInt);
    }

    public double expressionCount(String paramString1, String paramString2)
            throws Exception {
        return indriJNI.QueryEnvironment_expressionCount__SWIG_0(this.swigCPtr, this, paramString1, paramString2);
    }

    public double expressionCount(String paramString)
            throws Exception {
        return indriJNI.QueryEnvironment_expressionCount__SWIG_1(this.swigCPtr, this, paramString);
    }

    public double documentExpressionCount(String paramString1, String paramString2)
            throws Exception {
        return indriJNI.QueryEnvironment_documentExpressionCount__SWIG_0(this.swigCPtr, this, paramString1, paramString2);
    }

    public double documentExpressionCount(String paramString)
            throws Exception {
        return indriJNI.QueryEnvironment_documentExpressionCount__SWIG_1(this.swigCPtr, this, paramString);
    }

    public ScoredExtentResult[] expressionList(String paramString1, String paramString2)
            throws Exception {
        return indriJNI.QueryEnvironment_expressionList__SWIG_0(this.swigCPtr, this, paramString1, paramString2);
    }

    public ScoredExtentResult[] expressionList(String paramString)
            throws Exception {
        return indriJNI.QueryEnvironment_expressionList__SWIG_1(this.swigCPtr, this, paramString);
    }

    public int documentLength(int paramInt)
            throws Exception {
        return indriJNI.QueryEnvironment_documentLength(this.swigCPtr, this, paramInt);
    }

    public void setFormulationParameters(Map paramMap) {
        indriJNI.QueryEnvironment_setFormulationParameters(this.swigCPtr, this, paramMap);
    }

    public String reformulateQuery(String paramString) {
        return indriJNI.QueryEnvironment_reformulateQuery(this.swigCPtr, this, paramString);
    }

    public String stemTerm(String paramString) {
        return indriJNI.QueryEnvironment_stemTerm(this.swigCPtr, this, paramString);
    }

    public long termCountUnique() {
        return indriJNI.QueryEnvironment_termCountUnique(this.swigCPtr, this);
    }

    public long stemCount(String paramString) {
        return indriJNI.QueryEnvironment_stemCount(this.swigCPtr, this, paramString);
    }

    public long stemFieldCount(String paramString1, String paramString2) {
        return indriJNI.QueryEnvironment_stemFieldCount(this.swigCPtr, this, paramString1, paramString2);
    }

    public long documentStemCount(String paramString) {
        return indriJNI.QueryEnvironment_documentStemCount(this.swigCPtr, this, paramString);
    }
}


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\QueryEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
