package com.storage.server.unit;

import java.util.Objects;

public class Address {

    public static final String EMPTY_STRING = "";
    private final String owner;
    private final String warehouse;
    private final String rack;
    private final String shelf;

    public Address(String owner, String warehouse, String rack, String shelf) {
        this.owner = owner;
        this.warehouse = warehouse;
        this.rack = rack;
        this.shelf = shelf;
    }

    public Address(String owner, String warehouse, String rack) {
        this.owner = owner;
        this.warehouse = warehouse;
        this.rack = rack;
        this.shelf = EMPTY_STRING;
    }

    public Address(String owner, String warehouse) {
        this.owner = owner;
        this.warehouse = warehouse;
        this.rack = EMPTY_STRING;
        this.shelf = EMPTY_STRING;
    }
    public Address(String owner) {
        this.owner = owner;
        this.warehouse = EMPTY_STRING;
        this.rack = EMPTY_STRING;
        this.shelf = EMPTY_STRING;
    }

    public String getOwner() { return owner;}
    public String getWarehouse() {
        return warehouse;
    }

    public String getRack() {
        return rack;
    }

    public String getShelf() {
        return shelf;
    }

    public String getAddressAsString() {
        return getString(":");
    }

    private String getString(String delimiter) {
        StringBuilder sb = new StringBuilder();
        sb.append(owner);
        if (!warehouse.equalsIgnoreCase(EMPTY_STRING)) sb.append(delimiter + warehouse);
        if (!rack.equalsIgnoreCase(EMPTY_STRING)) sb.append(delimiter + rack);
        if (!shelf.equalsIgnoreCase(EMPTY_STRING)) sb.append(delimiter + shelf);
        return sb.toString();
    }

    public String getUnixAddress() {
        return "/"+getString("/");
    }


    public static Address parseAddress(String addressRawInput) {
        String[] parts = addressRawInput.split(":");
        if(parts.length < 1 || parts.length > 4) throw new IllegalArgumentException("invalid address provided in request");
        if(parts.length == 4) {
            return new Address(parts[0], parts[1], parts[2], parts[3]);
        }
        if(parts.length == 3) {
            return new Address(parts[0], parts[1], parts[2]);
        }
        if(parts.length == 2) {
            return new Address(parts[0], parts[1]);
        }
        return new Address(parts[0]);
    }

    public boolean isSpecificOrSameAddress(Address other) {
        String thisAddr = this.getAddressAsString();
        String otherAddr = other.getAddressAsString();
        return thisAddr.startsWith(otherAddr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(owner, address.owner) &&
                Objects.equals(warehouse, address.warehouse) &&
                Objects.equals(rack, address.rack) &&
                Objects.equals(shelf, address.shelf);
    }

    @Override
    public String toString() {
        return "Address{" +
                "owner='" + owner + '\'' +
                ", warehouse='" + warehouse + '\'' +
                ", rack='" + rack + '\'' +
                ", shelf='" + shelf + '\'' +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(owner, warehouse, rack, shelf);
    }

    public Address createSpecificAddress(String nextLevel) {
        if(this.rack == EMPTY_STRING) return new Address(owner, warehouse, nextLevel);
        if(this.shelf == EMPTY_STRING) return new Address(owner, warehouse, rack, nextLevel);
        throw new IllegalArgumentException();
    }
}
