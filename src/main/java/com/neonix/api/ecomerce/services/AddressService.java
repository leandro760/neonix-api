package com.neonix.api.ecomerce.services;

import com.neonix.api.ecomerce.models.Address;
import com.neonix.api.ecomerce.models.User;
import com.neonix.api.ecomerce.repository.AddressRepository;
import com.neonix.api.ecomerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository; // Necesario para asociar una dirección a un usuario existente

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> findAddressById(Integer id) {
        return addressRepository.findById(id);
    }

    public List<Address> findAddressesByUserId(Integer userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address saveAddress(Address address) {
        if (address.getId() == null) {
            address.setCreatedAt(LocalDateTime.now());
        }
        // Asegurarse de que la referencia al usuario es gestionada por JPA
        if (address.getUser() != null && address.getUser().getId() != null) {
            Optional<User> user = userRepository.findById(address.getUser().getId());
            user.ifPresent(address::setUser);
        }
        return addressRepository.save(address);
    }

    public Address updateAddress(Integer id, Address addressDetails) {
        Optional<Address> existingAddress = addressRepository.findById(id);
        if (existingAddress.isPresent()) {
            Address address = existingAddress.get();
            address.setStreet(addressDetails.getStreet());
            address.setCity(addressDetails.getCity());
            address.setState(addressDetails.getState());
            address.setZipCode(addressDetails.getZipCode());
            address.setCountry(addressDetails.getCountry());
            address.setAddressType(addressDetails.getAddressType());
            address.setIsDefault(addressDetails.getIsDefault());
            // created_at no se actualiza, user_id no se cambia en una actualización de dirección
            return addressRepository.save(address);
        }
        return null; // O lanzar una excepción
    }

    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    public boolean addressExists(Integer id) {
        return addressRepository.existsById(id);
    }
}