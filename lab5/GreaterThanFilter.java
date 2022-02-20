/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {

    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        _colNameToIndex = input.colNameToIndex(colName);
        _ref = ref;
    }

    @Override
    protected boolean keep() {
        if (candidateNext().getValue(_colNameToIndex).compareTo(_ref) > 0) {
            return true;
        }
        return false;
    }

    public int _colNameToIndex;
    public String _ref;
}
