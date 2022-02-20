/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        _input = input;
        _colNameToIndex = input.colNameToIndex(colName);
        _subStr = subStr;
    }

    @Override
    protected boolean keep() {
        if (candidateNext().getValue(_colNameToIndex).contains(_subStr)) {
            return true;
        }
        return false;
    }

    public Table _input;
    public int _colNameToIndex;
    public String _subStr;
}
