package lemurproject.indri;

import java.util.Map;

public class QueryAnnotation {

    private long swigCPtr;
    protected boolean swigCMemOwn;

    public QueryAnnotation(long paramLong, boolean paramBoolean) {
        this.swigCMemOwn = paramBoolean;
        this.swigCPtr = paramLong;
    }

    public static long getCPtr(QueryAnnotation paramQueryAnnotation) {
        return paramQueryAnnotation == null ? 0L : paramQueryAnnotation.swigCPtr;
    }

    protected void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0L) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                indriJNI.delete_QueryAnnotation(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
    }

    public QueryAnnotationNode getQueryTree()
            throws Exception {
        return indriJNI.QueryAnnotation_getQueryTree(this.swigCPtr, this);
    }

    public Map getAnnotations()
            throws Exception {
        return indriJNI.QueryAnnotation_getAnnotations(this.swigCPtr, this);
    }

    public ScoredExtentResult[] getResults()
            throws Exception {
        return indriJNI.QueryAnnotation_getResults(this.swigCPtr, this);
    }

    public QueryAnnotation() {
        this(indriJNI.new_QueryAnnotation(), true);
    }
}
