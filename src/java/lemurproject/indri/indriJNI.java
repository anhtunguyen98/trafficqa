package lemurproject.indri;

import java.util.Map;

public class indriJNI {

    public static final native QueryAnnotationNode QueryAnnotation_getQueryTree(long paramLong, QueryAnnotation paramQueryAnnotation) throws Exception;

    public static final native Map QueryAnnotation_getAnnotations(long paramLong, QueryAnnotation paramQueryAnnotation) throws Exception;

    public static final native ScoredExtentResult[] QueryAnnotation_getResults(long paramLong, QueryAnnotation paramQueryAnnotation) throws Exception;

    public static final native long new_QueryAnnotation();

    public static final native void delete_QueryAnnotation(long paramLong);

    public static final native long new_QueryEnvironment();

    public static final native void QueryEnvironment_addServer(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native void QueryEnvironment_addIndex(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native void QueryEnvironment_removeServer(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native void QueryEnvironment_removeIndex(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native void QueryEnvironment_close(long paramLong, QueryEnvironment paramQueryEnvironment) throws Exception;

    public static final native void QueryEnvironment_setMemory(long paramLong1, QueryEnvironment paramQueryEnvironment, long paramLong2) throws Exception;

    public static final native void QueryEnvironment_setScoringRules(long paramLong, QueryEnvironment paramQueryEnvironment, String[] paramArrayOfString) throws Exception;

    public static final native void QueryEnvironment_setStopwords(long paramLong, QueryEnvironment paramQueryEnvironment, String[] paramArrayOfString) throws Exception;

    public static final native ScoredExtentResult[] QueryEnvironment_runQuery__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString, int paramInt) throws Exception;

    public static final native ScoredExtentResult[] QueryEnvironment_runQuery__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString, int[] paramArrayOfInt, int paramInt) throws Exception;

    public static final native QueryResults QueryEnvironment_runQuery__SWIG_2(long paramLong, QueryEnvironment paramQueryEnvironment, QueryRequest paramQueryRequest) throws Exception;

    public static final native long QueryEnvironment_runAnnotatedQuery__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString, int paramInt) throws Exception;

    public static final native long QueryEnvironment_runAnnotatedQuery__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString, int[] paramArrayOfInt, int paramInt) throws Exception;

    public static final native ParsedDocument[] QueryEnvironment_documents__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment, int[] paramArrayOfInt) throws Exception;

    public static final native ParsedDocument[] QueryEnvironment_documents__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, ScoredExtentResult[] paramArrayOfScoredExtentResult) throws Exception;

    public static final native String[] QueryEnvironment_documentMetadata__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment, int[] paramArrayOfInt, String paramString) throws Exception;

    public static final native String[] QueryEnvironment_documentMetadata__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, ScoredExtentResult[] paramArrayOfScoredExtentResult, String paramString) throws Exception;

    public static final native int[] QueryEnvironment_documentIDsFromMetadata(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString, String[] paramArrayOfString) throws Exception;

    public static final native ParsedDocument[] QueryEnvironment_documentsFromMetadata(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString, String[] paramArrayOfString) throws Exception;

    public static final native long QueryEnvironment_termCount__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment) throws Exception;

    public static final native long QueryEnvironment_termCount__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native long QueryEnvironment_termFieldCount(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString1, String paramString2) throws Exception;

    public static final native String[] QueryEnvironment_fieldList(long paramLong, QueryEnvironment paramQueryEnvironment) throws Exception;

    public static final native long QueryEnvironment_documentCount__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment) throws Exception;

    public static final native long QueryEnvironment_documentCount__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native DocumentVector[] QueryEnvironment_documentVectors(long paramLong, QueryEnvironment paramQueryEnvironment, int[] paramArrayOfInt) throws Exception;

    public static final native double QueryEnvironment_expressionCount__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString1, String paramString2) throws Exception;

    public static final native double QueryEnvironment_expressionCount__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native double QueryEnvironment_documentExpressionCount__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString1, String paramString2) throws Exception;

    public static final native double QueryEnvironment_documentExpressionCount__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native ScoredExtentResult[] QueryEnvironment_expressionList__SWIG_0(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString1, String paramString2) throws Exception;

    public static final native ScoredExtentResult[] QueryEnvironment_expressionList__SWIG_1(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString) throws Exception;

    public static final native int QueryEnvironment_documentLength(long paramLong, QueryEnvironment paramQueryEnvironment, int paramInt) throws Exception;

    public static final native void QueryEnvironment_setFormulationParameters(long paramLong, QueryEnvironment paramQueryEnvironment, Map paramMap);

    public static final native String QueryEnvironment_reformulateQuery(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString);

    public static final native String QueryEnvironment_stemTerm(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString);

    public static final native long QueryEnvironment_termCountUnique(long paramLong, QueryEnvironment paramQueryEnvironment);

    public static final native long QueryEnvironment_stemCount(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString);

    public static final native long QueryEnvironment_stemFieldCount(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString1, String paramString2);

    public static final native long QueryEnvironment_documentStemCount(long paramLong, QueryEnvironment paramQueryEnvironment, String paramString);

    public static final native void delete_QueryEnvironment(long paramLong);

    public static final native void delete_QueryExpander(long paramLong);

    public static final native ScoredExtentResult[] QueryExpander_runExpandedQuery__SWIG_0(long paramLong, QueryExpander paramQueryExpander, String paramString, int paramInt, boolean paramBoolean);

    public static final native ScoredExtentResult[] QueryExpander_runExpandedQuery__SWIG_1(long paramLong, QueryExpander paramQueryExpander, String paramString, int paramInt);

    public static final native String QueryExpander_expand(long paramLong, QueryExpander paramQueryExpander, String paramString, ScoredExtentResult[] paramArrayOfScoredExtentResult);

    public static final native long new_RMExpander(long paramLong, QueryEnvironment paramQueryEnvironment, Map paramMap);

    public static final native String RMExpander_expand(long paramLong, RMExpander paramRMExpander, String paramString, ScoredExtentResult[] paramArrayOfScoredExtentResult);

    public static final native void delete_RMExpander(long paramLong);

    public static final native long new_PonteExpander(long paramLong, QueryEnvironment paramQueryEnvironment, Map paramMap);

    public static final native String PonteExpander_expand(long paramLong, PonteExpander paramPonteExpander, String paramString, ScoredExtentResult[] paramArrayOfScoredExtentResult);

    public static final native void delete_PonteExpander(long paramLong);

    public static final native void delete_IndexStatus(long paramLong);

    public static final native void IndexStatus_status(long paramLong, IndexStatus paramIndexStatus, int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3);

    public static final native long new_IndexStatus();

    public static final native void IndexStatus_director_connect(IndexStatus paramIndexStatus, long paramLong, boolean paramBoolean1, boolean paramBoolean2);

    public static final native void IndexStatus_change_ownership(IndexStatus paramIndexStatus, long paramLong, boolean paramBoolean);

    public static final native long new_IndexEnvironment();

    public static final native void delete_IndexEnvironment(long paramLong);

    public static final native void IndexEnvironment_setDocumentRoot(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_setAnchorTextPath(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_setOffsetMetadataPath(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_setOffsetAnnotationsPath(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_addFileClass__SWIG_0(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, Map paramMap) throws Exception;

    public static final native Specification IndexEnvironment_getFileClassSpec(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_addFileClass__SWIG_1(long paramLong, IndexEnvironment paramIndexEnvironment, Specification paramSpecification) throws Exception;

    public static final native void IndexEnvironment_deleteDocument(long paramLong, IndexEnvironment paramIndexEnvironment, int paramInt) throws Exception;

    public static final native void IndexEnvironment_setIndexedFields(long paramLong, IndexEnvironment paramIndexEnvironment, String[] paramArrayOfString) throws Exception;

    public static final native void IndexEnvironment_setNumericField__SWIG_0(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString1, boolean paramBoolean, String paramString2) throws Exception;

    public static final native void IndexEnvironment_setNumericField__SWIG_1(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString, boolean paramBoolean) throws Exception;

    public static final native void IndexEnvironment_setOrdinalField(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString, boolean paramBoolean) throws Exception;

    public static final native void IndexEnvironment_setParentalField(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString, boolean paramBoolean) throws Exception;

    public static final native void IndexEnvironment_setMetadataIndexedFields(long paramLong, IndexEnvironment paramIndexEnvironment, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;

    public static final native void IndexEnvironment_setStopwords(long paramLong, IndexEnvironment paramIndexEnvironment, String[] paramArrayOfString) throws Exception;

    public static final native void IndexEnvironment_setStemmer(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_setMemory(long paramLong1, IndexEnvironment paramIndexEnvironment, long paramLong2) throws Exception;

    public static final native void IndexEnvironment_setNormalization(long paramLong, IndexEnvironment paramIndexEnvironment, boolean paramBoolean) throws Exception;

    public static final native void IndexEnvironment_setStoreDocs(long paramLong, IndexEnvironment paramIndexEnvironment, boolean paramBoolean) throws Exception;

    public static final native void IndexEnvironment_create__SWIG_0(long paramLong1, IndexEnvironment paramIndexEnvironment, String paramString, long paramLong2, IndexStatus paramIndexStatus) throws Exception;

    public static final native void IndexEnvironment_create__SWIG_1(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_open__SWIG_0(long paramLong1, IndexEnvironment paramIndexEnvironment, String paramString, long paramLong2, IndexStatus paramIndexStatus) throws Exception;

    public static final native void IndexEnvironment_open__SWIG_1(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_close(long paramLong, IndexEnvironment paramIndexEnvironment) throws Exception;

    public static final native void IndexEnvironment_addFile__SWIG_0(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString) throws Exception;

    public static final native void IndexEnvironment_addFile__SWIG_1(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString1, String paramString2) throws Exception;

    public static final native int IndexEnvironment_addString__SWIG_0(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString1, String paramString2, Map paramMap) throws Exception;

    public static final native int IndexEnvironment_addString__SWIG_1(long paramLong, IndexEnvironment paramIndexEnvironment, String paramString1, String paramString2, Map paramMap, TagExtent[] paramArrayOfTagExtent) throws Exception;

    public static final native int IndexEnvironment_addParsedDocument(long paramLong, IndexEnvironment paramIndexEnvironment, ParsedDocument paramParsedDocument) throws Exception;

    public static final native int IndexEnvironment_documentsIndexed(long paramLong, IndexEnvironment paramIndexEnvironment) throws Exception;

    public static final native int IndexEnvironment_documentsSeen(long paramLong, IndexEnvironment paramIndexEnvironment) throws Exception;

    public static final native long RMExpander_SWIGUpcast(long paramLong);

    public static final native long PonteExpander_SWIGUpcast(long paramLong);

    public static void SwigDirector_IndexStatus_status(IndexStatus paramIndexStatus, int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3) {
        paramIndexStatus.status(paramInt1, paramString1, paramString2, paramInt2, paramInt3);
    }

    private static final native void swig_module_init();

    static {
        System.loadLibrary("indri_jni");

        swig_module_init();
    }
}
