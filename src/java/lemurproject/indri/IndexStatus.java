/*     */ package lemurproject.indri;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexStatus
/*     */ {
/*     */   private long swigCPtr;
/*     */   
/*     */ 
/*     */   protected boolean swigCMemOwn;
/*     */   
/*     */ 
/*     */ 
/*     */   public IndexStatus(long paramLong, boolean paramBoolean)
/*     */   {
/*  16 */     this.swigCMemOwn = paramBoolean;
/*  17 */     this.swigCPtr = paramLong;
/*     */   }
/*     */   
/*     */   public static long getCPtr(IndexStatus paramIndexStatus) {
/*  21 */     return paramIndexStatus == null ? 0L : paramIndexStatus.swigCPtr;
/*     */   }
/*     */   
/*     */   protected void finalize() {
/*  25 */     delete();
/*     */   }
/*     */   
/*     */   public synchronized void delete() {
/*  29 */     if (this.swigCPtr != 0L) {
/*  30 */       if (this.swigCMemOwn) {
/*  31 */         this.swigCMemOwn = false;
/*  32 */         indriJNI.delete_IndexStatus(this.swigCPtr);
/*     */       }
/*  34 */       this.swigCPtr = 0L;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void swigDirectorDisconnect() {
/*  39 */     this.swigCMemOwn = false;
/*  40 */     delete();
/*     */   }
/*     */   
/*     */   public void swigReleaseOwnership() {
/*  44 */     this.swigCMemOwn = false;
/*  45 */     indriJNI.IndexStatus_change_ownership(this, this.swigCPtr, false);
/*     */   }
/*     */   
/*     */   public void swigTakeOwnership() {
/*  49 */     this.swigCMemOwn = true;
/*  50 */     indriJNI.IndexStatus_change_ownership(this, this.swigCPtr, true);
/*     */   }
/*     */   
/*     */   public void status(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3) {
/*  54 */     indriJNI.IndexStatus_status(this.swigCPtr, this, paramInt1, paramString1, paramString2, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public IndexStatus() {
/*  58 */     this(indriJNI.new_IndexStatus(), true);
/*  59 */     indriJNI.IndexStatus_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
/*     */   }
/*     */   
/*     */   public static final class action_code {
/*  63 */     public static final action_code FileOpen = new action_code("FileOpen");
/*  64 */     public static final action_code FileSkip = new action_code("FileSkip");
/*  65 */     public static final action_code FileError = new action_code("FileError");
/*  66 */     public static final action_code FileClose = new action_code("FileClose");
/*  67 */     public static final action_code DocumentCount = new action_code("DocumentCount");
/*     */     
/*     */     public final int swigValue() {
/*  70 */       return this.swigValue;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  74 */       return this.swigName;
/*     */     }
/*     */     
/*     */     public static action_code swigToEnum(int paramInt) {
/*  78 */       if ((paramInt < swigValues.length) && (paramInt >= 0) && (swigValues[paramInt].swigValue == paramInt))
/*  79 */         return swigValues[paramInt];
/*  80 */       for (int i = 0; i < swigValues.length; i++)
/*  81 */         if (swigValues[i].swigValue == paramInt)
/*  82 */           return swigValues[i];
/*  83 */       throw new IllegalArgumentException("No enum " + action_code.class + " with value " + paramInt);
/*     */     }
/*     */     
/*     */     private action_code(String paramString) {
/*  87 */       this.swigName = paramString;
/*  88 */       this.swigValue = (swigNext++);
/*     */     }
/*     */     
/*     */     private action_code(String paramString, int paramInt) {
/*  92 */       this.swigName = paramString;
/*  93 */       this.swigValue = paramInt;
/*  94 */       swigNext = paramInt + 1;
/*     */     }
/*     */     
/*     */     private action_code(String paramString, action_code paramaction_code) {
/*  98 */       this.swigName = paramString;
/*  99 */       this.swigValue = paramaction_code.swigValue;
/* 100 */       swigNext = this.swigValue + 1;
/*     */     }
/*     */     
/* 103 */     private static action_code[] swigValues = { FileOpen, FileSkip, FileError, FileClose, DocumentCount };
/* 104 */     private static int swigNext = 0;
/*     */     private final int swigValue;
/*     */     private final String swigName;
/*     */   }
/*     */ }


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\IndexStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */