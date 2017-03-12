/*    */ package lemurproject.indri;
/*    */ 
/*    */ public class QueryAnnotationNode
/*    */ {
/*    */   public String name;
/*    */   public String type;
/*    */   public String queryText;
/*    */   public QueryAnnotationNode[] children;
/*    */   
/*    */   public QueryAnnotationNode(String paramString1, String paramString2, String paramString3, QueryAnnotationNode[] paramArrayOfQueryAnnotationNode) {
/* 11 */     this.name = paramString1;
/* 12 */     this.type = paramString2;
/* 13 */     this.queryText = paramString3;
/* 14 */     this.children = paramArrayOfQueryAnnotationNode;
/*    */   }
/*    */ }


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\QueryAnnotationNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */