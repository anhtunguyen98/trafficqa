package lemurproject.indri;

public class QueryRequest
{
  public static final int HTMLSnippet = 1;
  public static final int TextSnippet = 2;
  public String query;
  public String[] formulators;
  public String[] metadata;
  public int resultsRequested;
  public int startNum;
  public int options;
}


/* Location:              E:\thien\Learning\NLP\Project\IndexUI.jar!\lemurproject\indri\QueryRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */