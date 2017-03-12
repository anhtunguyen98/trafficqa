package lemurproject.indri;

public class DocumentVector
{
  public String[] stems;
  public int[] positions;
  public Field[] fields;
  
  public static class Field
  {
    public int begin;
    public int end;
    public long number;
    public int ordinal;
    public int parentOrdinal;
    public String name;
  }
}


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\DocumentVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */