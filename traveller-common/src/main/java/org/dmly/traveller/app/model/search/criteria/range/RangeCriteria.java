package org.dmly.traveller.app.model.search.criteria.range;

import org.dmly.traveller.app.infra.util.Checks;

public class RangeCriteria {
    private final int page;
    private final int rowCount;

    public RangeCriteria(final int page, final int rowCount) {
        Checks.checkParameter(page >= 0, "Incorrect page index: " + page);
        Checks.checkParameter(rowCount >= 0, "Incorrect row count: " + rowCount);

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
