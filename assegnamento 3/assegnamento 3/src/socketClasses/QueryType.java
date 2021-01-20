package socketClasses;

/**
 * Query types that can be used.
 */
public enum QueryType {

    /**
     * Add query.
     */
    add,

    /**
     * Select query.
     */
    select,

    /**
     * Select with filter query.
     */
    selectWithFilter,

    /**
     * Select count query.
     */
    count,

    /**
     * Update query.
     */
    update,
}