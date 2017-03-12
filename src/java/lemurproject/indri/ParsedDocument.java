/*    */ package lemurproject.indri;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ParsedDocument { public String text;
/*    */   public String content;
/*    */   public String[] terms;
/*    */   public TermExtent[] positions;
/*    */   public Map metadata;
/*    */   
/*    */   public static class TermExtent { public int begin;
/*    */     public int end;
/*    */     
/* 14 */     public TermExtent(int paramInt1, int paramInt2) { this.begin = paramInt1;
/* 15 */       this.end = paramInt2;
/*    */     }
/*    */   }
/*    */ }


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\ParsedDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */