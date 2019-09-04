package org.dmly.traveller.app.model.search.criteria.range;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;

public class RangeCriteria {
    private final int page;
    private final int rowCount;

    public RangeCriteria(final int page, final int rowCount) {
        if (page < 0) {
            throw new InvalidParameterException("Incorrect page index: " + page);
        }

        if (rowCount < 0) {
            throw new InvalidParameterException("Incorrect row count: " + rowCount);
        }

        this.page = page;
        this.rowCount = rowCount;
    }

    public int getPage() {
        return page;
    }

    public int getRowCount() {
        return rowCount;
    }
}
