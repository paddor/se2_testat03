package countlines;

public class LineCounts {
    public final int total;
    public final int empty;
    public final int comment;

    public LineCounts(int _total, int _empty, int _comment) {
        total = _total;
        empty = _empty;
        comment = _comment;
    }

    public int getTotalLineCount() {
        return total;
    }
    public int getEmptyLineCount() {
        return empty;
    }
    public int getCommentLineCount() {
        return comment;
    }
    public int getNetLineCount() {
        return total - empty - comment;
    }
}
// vim:et sw=4 ts=4
