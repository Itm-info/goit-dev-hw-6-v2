package itm.entities;

public class Client {
    private long id;
    private String name;

    public long getId() { return id; }
    public String getName() { return name; }
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object client) {
        if (client == this) return true;
        return client.getClass() == this.getClass() && // we won't be checking sub/superclasses here until we have a use for any
               ((Client) client).getId() == this.id;
    }

    @Override
    public int hashCode() {
        return this == null ? 0 : ((Long) this.id).hashCode();
    }

    @Override
    public String toString() { return "Id: " + id + ", name: " + name; }
}
