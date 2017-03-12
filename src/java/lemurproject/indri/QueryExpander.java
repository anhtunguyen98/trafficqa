/*    */ package lemurproject.indri;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QueryExpander
/*    */ {
/*    */   private long swigCPtr;
/*    */   
/*    */ 
/*    */   protected boolean swigCMemOwn;
/*    */   
/*    */ 
/*    */ 
/*    */   public QueryExpander(long paramLong, boolean paramBoolean)
/*    */   {
/* 16 */     this.swigCMemOwn = paramBoolean;
/* 17 */     this.swigCPtr = paramLong;
/*    */   }
/*    */   
/*    */   public static long getCPtr(QueryExpander paramQueryExpander) {
/* 21 */     return paramQueryExpander == null ? 0L : paramQueryExpander.swigCPtr;
/*    */   }
/*    */   
/*    */   protected void finalize() {
/* 25 */     delete();
/*    */   }
/*    */   
/*    */   public synchronized void delete() {
/* 29 */     if (this.swigCPtr != 0L) {
/* 30 */       if (this.swigCMemOwn) {
/* 31 */         this.swigCMemOwn = false;
/* 32 */         indriJNI.delete_QueryExpander(this.swigCPtr);
/*    */       }
/* 34 */       this.swigCPtr = 0L;
/*    */     }
/*    */   }
/*    */   
/*    */   public ScoredExtentResult[] runExpandedQuery(String paramString, int paramInt, boolean paramBoolean) {
/* 39 */     return indriJNI.QueryExpander_runExpandedQuery__SWIG_0(this.swigCPtr, this, paramString, paramInt, paramBoolean);
/*    */   }
/*    */   
/*    */   public ScoredExtentResult[] runExpandedQuery(String paramString, int paramInt) {
/* 43 */     return indriJNI.QueryExpander_runExpandedQuery__SWIG_1(this.swigCPtr, this, paramString, paramInt);
/*    */   }
/*    */   
/*    */   public String expand(String paramString, ScoredExtentResult[] paramArrayOfScoredExtentResult) {
/* 47 */     return indriJNI.QueryExpander_expand(this.swigCPtr, this, paramString, paramArrayOfScoredExtentResult);
/*    */   }
/*    */ }


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\QueryExpander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */