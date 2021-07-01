package name.dmitryrazumov.bank.model;

import name.dmitryrazumov.bank.entity.Client;

import java.util.Objects;

public class ClientModel {

    private int id;

    private String name;

    public ClientModel() {

    }

    public ClientModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ClientModel toModel(Client client) {
        ClientModel clientModel = new ClientModel();
        clientModel.setId(client.getId());
        clientModel.setName(client.getName());
        return clientModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientModel that = (ClientModel) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
