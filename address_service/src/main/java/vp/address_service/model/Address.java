package vp.address_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private UUID id;
    private String address;

    public Address(String address){
        this.id = UUID.randomUUID();
        this.address = address;
    }
}
