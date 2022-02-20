import java.util.Iterator;

/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {

    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        _colName1 = colName1;
        _colName2 = colName2;
        _indexCol1 = input.colNameToIndex(colName1);
        _indexCol2 = input.colNameToIndex(colName2);

    }

    @Override
    protected boolean keep() {
        if (candidateNext().getValue(_indexCol1).equals(candidateNext().getValue(_indexCol2))) {
            return true;
        }
        return false;
    }

    public String _colName1;
    public String _colName2;
    public int _indexCol1;
    public int _indexCol2;

}
