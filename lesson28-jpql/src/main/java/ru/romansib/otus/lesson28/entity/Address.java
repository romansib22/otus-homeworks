package ru.romansib.otus.lesson28.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client_address")
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    String street;

    @OneToOne
    @JoinColumn(name = "client_id")
    Client client;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Phone> phones;

    public Address(String street, Client client) {
        this.street = street;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", phones=" + phones +
                '}';
    }
}
