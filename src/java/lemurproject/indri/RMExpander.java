/*    */ package lemurproject.indri;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RMExpander
/*    */   extends QueryExpander
/*    */ {
/*    */   private long swigCPtr;
/*    */   
/*    */   public RMExpander(long paramLong, boolean paramBoolean)
/*    */   {
/* 15 */     super(indriJNI.RMExpander_SWIGUpcast(paramLong), paramBoolean);
/* 16 */     this.swigCPtr = paramLong;
/*    */   }
/*    */   
/*    */   public static long getCPtr(RMExpander paramRMExpander) {
/* 20 */     return paramRMExpander == null ? 0L : paramRMExpander.swigCPtr;
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
/* 31 */         indriJNI.delete_RMExpander(this.swigCPtr);
/*    */       }
/* 33 */       this.swigCPtr = 0L;
/*    */     }
/* 35 */     super.delete();
/*    */   }
/*    */   
/*    */   public RMExpander(QueryEnvironment paramQueryEnvironment, Map paramMap) {
/* 39 */     this(indriJNI.new_RMExpander(QueryEnvironment.getCPtr(paramQueryEnvironment), paramQueryEnvironment, paramMap), true);
/*    */   }
/*    */   
/*    */   public String expand(String paramString, ScoredExtentResult[] paramArrayOfScoredExtentResult) {
/* 43 */     return indriJNI.RMExpander_expand(this.swigCPtr, this, paramString, paramArrayOfScoredExtentResult);
/*    */   }
/*    */ }


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\RMExpander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */