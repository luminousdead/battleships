package battleship;

public enum Ship {
    AircraftCarrier("Aircraft Carrier", 5),
    Battleship("Battleship", 4),
    Submarine("Submarine ", 3),
    Cruiser("Cruiser", 3),
    Destroyer("Destroyer", 2);

    private String name;
    private int length;

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }
}