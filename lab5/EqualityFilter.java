/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        _input = input;
        _match = match;
        _colNameToIndex = input.colNameToIndex(colName);
    }

    @Override
    protected boolean keep() {
        if (candidateNext().getValue(_colNameToIndex).equals(_match)) {
            return true;
        }
        return false;
    }

    public Table _input;
    public String _match;
    public int _colNameToIndex;
}
