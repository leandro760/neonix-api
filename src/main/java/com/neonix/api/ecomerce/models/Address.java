package com.neonix.api.ecomerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable  // <-- Cambia de @Entity a @Embeddable: ¡esto es clave!
public class Address {
    @Column(name = "street")  // Columna en tabla Users
    @NotBlank
    private String street;

    @Column(name = "city")
    @NotBlank
    private String city;

    @Column(name = "country")
    @NotBlank
    private String country;

    @Column(name = "zip_code")  // Ajusta el nombre si quieres (en BD será zip_code)
    private String zipCode;

    // Constructor vacío (requerido para JPA)
    public Address() {}

    // Constructor con parámetros (útil para crear objetos)
    public Address(String street, String city, String country, String zipCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }

    // Getters y Setters (mantén los tuyos, o usa estos)
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    // toString para debug (opcional)
    @Override
    public String toString() {
        return street + ", " + city + " " + zipCode + ", " + country;
    }
}