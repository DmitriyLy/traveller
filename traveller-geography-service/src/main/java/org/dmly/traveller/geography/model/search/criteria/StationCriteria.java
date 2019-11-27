package org.dmly.traveller.geography.model.search.criteria;


import org.dmly.traveller.geography.model.entity.TransportType;

/**
 * Filtering criteria for search stations operation
 *
 */
public class StationCriteria {

    //City's name
    private String name;

    private TransportType transportType;

    //Station's address: street, zipCode, building number
    private String address;

    /**
     * Returns filtering criteria to search stations that
     * contains specified name parameter
     * @param name
     * @return
     */
    public static StationCriteria byName(final String name) {
        return new StationCriteria(name);
    }

    public static StationCriteria byTransportType(final TransportType transportType) {
        StationCriteria criteria = new StationCriteria();
        criteria.setTransportType(transportType);
        return criteria;
    }

    public StationCriteria() {
    }

    private StationCriteria(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
