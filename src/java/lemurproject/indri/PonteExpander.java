/*    */ package lemurproject.indri;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PonteExpander
/*    */   extends QueryExpander
/*    */ {
/*    */   private long swigCPtr;
/*    */   
/*    */   public PonteExpander(long paramLong, boolean paramBoolean)
/*    */   {
/* 15 */     super(indriJNI.PonteExpander_SWIGUpcast(paramLong), paramBoolean);
/* 16 */     this.swigCPtr = paramLong;
/*    */   }
/*    */   
/*    */   public static long getCPtr(PonteExpander paramPonteExpander) {
/* 20 */     return paramPonteExpander == null ? 0L : paramPonteExpander.swigCPtr;
/*    */   }
/*    */   
/*    */   protected void finalize() {
/* 24 */     delete();
/*    */   }
/*    */   
/*    */   public synchronized void delete() {
/* 28 */     if (this.swigCPtr != 0L) {
/* 29 */       if (this.swigCMemOwn) {
/* 30 */         this.swigCMemOwn = false;
/* 31 */         indriJNI.delete_PonteExpander(this.swigCPtr);
/*    */       }
/* 33 */       this.swigCPtr = 0L;
/*    */     }
/* 35 */     super.delete();
/*    */   }
/*    */   
/*    */   public PonteExpander(QueryEnvironment paramQueryEnvironment, Map paramMap) {
/* 39 */     this(indriJNI.new_PonteExpander(QueryEnvironment.getCPtr(paramQueryEnvironment), paramQueryEnvironment, paramMap), true);
/*    */   }
/*    */   
/*    */   public String expand(String paramString, ScoredExtentResult[] paramArrayOfScoredExtentResult) {
/* 43 */     return indriJNI.PonteExpander_expand(this.swigCPtr, this, paramString, paramArrayOfScoredExtentResult);
/*    */   }
/*    */ }


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\PonteExpander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */